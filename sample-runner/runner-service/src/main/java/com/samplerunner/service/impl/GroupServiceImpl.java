package com.samplerunner.service.impl;

import static com.samplerunner.activate.oauth.Activate.APP_KEY;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fluig.customappkey.Keyring;
import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.api.customappkey.KeyVO;
import com.fluig.sdk.api.group.GroupVO;
import com.fluig.sdk.api.oauth.OAuthSdkVO;
import com.fluig.sdk.service.OAuthUserService;
import com.fluig.sdk.service.SecurityService;
import com.fluig.sdk.service.UserService;
import com.fluig.sdk.tenant.AdminUserVO;
import com.fluig.sdk.tenant.TenantVO;
import com.fluig.sdk.user.UserVO;
import com.samplerunner.service.GroupService;
import com.totvs.technology.foundation.common.TOTVSTechPrincipal;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

@Remote
@Stateless(name = GroupService.JNDI_NAME, mappedName = GroupService.JNDI_NAME)
public class GroupServiceImpl implements GroupService {

    private static final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Resource
    private SessionContext context;

    @EJB(lookup = SecurityService.JNDI_REMOTE_NAME)
    private SecurityService securityService;

    @EJB(lookup = UserService.JNDI_REMOTE_NAME)
    private UserService userService;

    @EJB(lookup = OAuthUserService.JNDI_REMOTE_NAME)
    private OAuthUserService oAuthUserService;

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<GroupVO> listGroups() throws Exception {
        Long tenantId = securityService.getCurrentTenantId();
        UserVO userVO = userService.getCurrent();

        // service para recuperar o objeto com consumerKey e consumerSecret
        KeyVO key = Keyring.getKeys(tenantId, APP_KEY);

        // gera os tokens para o usuário logado
        //OAuthConsumer consumer = OAuthUtil.generateKeysToUser(getJwt(), key, userVO.getLogin());
        OAuthSdkVO oAuthSdkVO = oAuthUserService.generateUserOAuthKeys(key.getConsumerKey(), userVO.getLogin());
        OAuthConsumer consumer = new DefaultOAuthConsumer(key.getConsumerKey(), key.getConsumerSecret());
        consumer.setTokenWithSecret(oAuthSdkVO.getTokenAccess(), oAuthSdkVO.getTokenSecret());

        // API que será requisitada
        URL url = new URL(key.getDomainUrl() + "/admin/api/v1/groups");

        HttpURLConnection conn = getHttpURLConnection(url, "GET");
        consumer.sign(conn);
        conn.connect();
        Reader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (int c = reader.read(); c != -1; c = reader.read()) {
            sb.append((char) c);
        }
        conn.disconnect();

        // transformando o response para a lista de retorno
        JSONArray responseJson = new JSONObject(sb.toString()).getJSONArray("items");
        List<GroupVO> result = new ArrayList<>();
        for(int i = 0; i < responseJson.length(); i++){
            JSONObject group = responseJson.getJSONObject(i);
            GroupVO vo = new GroupVO(group.getLong("id"), group.getString("code"), group.getString("description"));
            result.add(vo);
        }

        return result;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public GroupVO createGroup(GroupVO vo) throws Exception {
        Long tenantId = securityService.getCurrentTenantId();
        KeyVO key = Keyring.getKeys(tenantId, APP_KEY);

        // buscando usuáriuo administrador para executar a ação
        AdminUserVO admin = findAdmin(tenantId);

        // gerando as chaves com o login do administrador
        //OAuthConsumer consumer = OAuthUtil.generateKeysToUser(getJwt(), key, admin.getLogin());
        OAuthConsumer consumer = getOAuthConsumer(key, admin.getLogin());

        // endpoint da API para criar um grupo
        URL url = new URL(key.getDomainUrl() + "/admin/api/v1/groups");

        // body da request.  Ex.: {'code':'groupCode', 'description': 'Grupo para usuários novos'}
        JSONObject bodyRequest = new JSONObject();
        bodyRequest.put("code", vo.getCode());
        bodyRequest.put("description", vo.getDescription());

        try {
            HttpURLConnection conn = getHttpURLConnection(url, "POST");
            consumer.sign(conn);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(bodyRequest.toString());
            wr.flush();
            wr.close();
            conn.connect();
            Reader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int c = reader.read(); c != -1; c = reader.read()) {
                sb.append((char) c);
            }
            conn.disconnect();

            // transformando o response para a lista de retorno
            JSONObject jsonResponse = new JSONObject(sb.toString());
            return new GroupVO(
                    jsonResponse.getLong("id"),
                    jsonResponse.getString("code"),
                    jsonResponse.getString("description")
            );
        } catch (Exception e){
            throw new SDKException("Não foi possível criar o grupo", e);
        }
    }

    private OAuthConsumer getOAuthConsumer(KeyVO key, String login) throws Exception {
        OAuthSdkVO oAuthSdkVO = oAuthUserService.generateUserOAuthKeys(key.getConsumerKey(), login);
        OAuthConsumer consumer = new DefaultOAuthConsumer(key.getConsumerKey(), key.getConsumerSecret());
        consumer.setTokenWithSecret(oAuthSdkVO.getTokenAccess(), oAuthSdkVO.getTokenSecret());
        return consumer;
    }

    private AdminUserVO findAdmin(Long tenantId) throws Exception {
        TenantVO tenantVO = securityService.getCurrentTenantById(tenantId);
        if(tenantVO != null){
            String loginAdm = "adm_".concat(tenantVO.getCode());
            AdminUserVO adminUserVO = new AdminUserVO();
            adminUserVO.setLogin(loginAdm);;
            return adminUserVO;
        } else {
            throw new SDKException("No admin found for tenantId: " + tenantId);
        }
    }

    private HttpURLConnection getHttpURLConnection(URL url, String method) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        return conn;
    }

    private String getJwt() throws Exception {
        try {
            TOTVSTechPrincipal principal = (TOTVSTechPrincipal) context.getCallerPrincipal();
            return principal.getJwt();
        } catch (Exception e){
            throw new SDKException("Não foi possível recuperar o UserPrincipal", e);
        }
    }
}
