package com.samplerunner.activate.oauth;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.fluig.customappkey.Keyring;
import com.fluig.sdk.api.component.activation.ActivationEvent;
import com.fluig.sdk.api.component.activation.ActivationListener;

/**
 * 
 * Classe de ativação para provisionar a criação de um OAuth Provider e um OAuth App
 *  
 * Essa funcionalidade está disponível apenas a partir da release 1.6.5 do fluig
 * 
 */
@Remote
@Stateless(mappedName = "activator/runner", name = "activator/runner")
public class Activate implements ActivationListener {
	
    /**
     * ATENÇÃO: Essa é a chave para sua aplicação recuperar os tokens para as requisições
     * 
     * Troque esta chave, atenção ao formato aaaa-bbbb-cccc-dddd.
     */
    public static final String APP_KEY = "eUr6-Vkhc-WEYQ-4KA0";

    @Override
    public String getArtifactFileName() throws Exception {
        return "runner-service.jar";
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void install(ActivationEvent event) throws Exception {
    }

    @Override
    public void disable(ActivationEvent evt) throws Exception {
    }

    @Override
    public void enable(ActivationEvent evt) throws Exception {
        Keyring.provision(APP_KEY);
    }

}
