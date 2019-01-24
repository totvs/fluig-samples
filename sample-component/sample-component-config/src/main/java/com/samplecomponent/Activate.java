package com.samplecomponent;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.fluig.customappkey.Keyring;
import com.fluig.sdk.api.component.activation.ActivationEvent;
import com.fluig.sdk.api.component.activation.ActivationListener;
import com.samplecomponent.util.RestConstant;

/**
 * 
 * Classe de ativação para provisionar a criação de um OAuth Provider e um OAuth App
 *  
 * Essa funcionalidade está disponível apenas a partir da release 1.6.5 do fluig
 * 
 */
@Remote
@Stateless(mappedName = "activator/samplecomponent", name = "activator/samplecomponent")
public class Activate implements ActivationListener {

    @Override
    public String getArtifactFileName() throws Exception {
        return "sample-component-config.war";
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
        Keyring.provision(RestConstant.APP_KEY);
    }

}
