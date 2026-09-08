package com.samplecomponent.activate.oauth;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.fluig.customappkey.Keyring;
import com.fluig.sdk.api.component.activation.ActivationEvent;
import com.fluig.sdk.api.component.activation.ActivationListener;

/**
 * Classe de ativacao para provisionar OAuth do componente.
 *
 * Funcionalidades:
 * - Provisiona OAuth Provider/App via Keyring no momento da ativacao
 *
 */
@Remote
@Stateless(mappedName = "activator/samplecomponent", name = "activator/samplecomponent")
public class Activate implements ActivationListener {

    /**
     * Chave do app para recuperar tokens OAuth.
     * Formato: aaaa-bbbb-cccc-dddd
     */
    private static final String APP_KEY = "1234-5678-9876-5432";

    @Override
    public String getArtifactFileName() throws Exception {
        return "sample-component-service.jar";
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
