package com.samplecomponent;

import com.fluig.customappkey.Keyring;
import com.fluig.sdk.api.component.activation.ActivationEvent;
import com.fluig.sdk.api.component.activation.ActivationListener;
import com.samplecomponent.rest.RestConstant;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Remote
@Stateless(mappedName = "activator/samplecomponent", name = "activator/samplecomponent")
public class Activate implements ActivationListener {

    public String getArtifactFileName() throws Exception {
        return "sample-component-web.war";
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void install(ActivationEvent event) throws Exception {
    }

    public void disable(ActivationEvent evt) throws Exception {
    }

    public void enable(ActivationEvent evt) throws Exception {
        Keyring.provision(RestConstant.APP_KEY);
    }

}
