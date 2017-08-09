package com.company.project.authz.oauth.validator;

import com.company.project.authz.OAuthTokenxRequest;
import com.company.project.authz.domain.oauth.ClientDetails;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by eayon on 2017-07-18.
 */
public class PasswordClientDetailValidator extends AbstractOAuthTokenValidator {

    private static final Logger LOG = LoggerFactory.getLogger(PasswordClientDetailValidator.class);

    public PasswordClientDetailValidator(OAuthTokenxRequest oauthRequest) {
        super(oauthRequest);
    }

    @Override
    protected OAuthResponse validateSelf(ClientDetails clientDetails) throws OAuthSystemException {


        final String grantType = grantType();
        //validate grant_type
        if (invalidateGranType(clientDetails,grantType)){
            return invalidGrantTypeResponse(grantType);
        }
        //validate client_secret
        if (invalidateClientSecret(clientDetails)) {
            return invalidClientSecretResponse();
        }
        //validate scope
        if (invalidateScope(clientDetails)){
            return invalidScopeResponse();
        }
        //validate username,password
        if(invalidUsernamePassword()){
            return invalidUsernamePasswordResponse();
        }
        return null;
    }

    private boolean invalidateScope(ClientDetails clientDetails) {
        final Set<String> scopes = oauthRequest.getScopes();
        return scopes.isEmpty() || excludeScopes(scopes, clientDetails);
    }

    private boolean invalidateClientSecret(ClientDetails clientDetails) {
        final String clientSecret = oauthRequest.getClientSecret();
        if (clientSecret == null || !clientSecret.equals(clientDetails.getClientSecret())) {
            LOG.debug("Invalid client_secret '{}', client_id = '{}'", clientSecret, clientDetails.getClientId());
            return true;
        }
        return false;
    }

    private boolean invalidateGranType(ClientDetails clientDetails, String grantType) {
        if (!clientDetails.grantTypes().contains(grantType)) {
            LOG.debug("Invalid grant_type '{}', client_id = '{}'", grantType, clientDetails.getClientId());
            return true;
        }
        return false;
    }
}
