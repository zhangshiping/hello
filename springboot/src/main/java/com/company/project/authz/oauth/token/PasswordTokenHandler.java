package com.company.project.authz.oauth.token;

import com.company.project.authz.OAuthTokenxRequest;
import com.company.project.authz.domain.oauth.AccessToken;
import com.company.project.authz.oauth.validator.AbstractClientDetailsValidator;
import com.company.project.authz.oauth.validator.PasswordClientDetailValidator;
import com.company.project.web.WebUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by eayon on 2017-07-18.
 */
public class PasswordTokenHandler extends AbstractOAuthTokenHandler {

    private static final Logger LOG = LoggerFactory.getLogger(PasswordTokenHandler.class);
    @Override
    public boolean support(OAuthTokenxRequest tokenRequest) throws OAuthProblemException {
        final String grantType = tokenRequest.getGrantType();
        return GrantType.PASSWORD.toString().equalsIgnoreCase(grantType);
    }

    @Override
    protected AbstractClientDetailsValidator getValidator() {
        return new PasswordClientDetailValidator(tokenRequest);
    }

    @Override
    protected void handlerAfterValidation() throws OAuthProblemException, OAuthSystemException {
        AccessToken accessToken = oAuthService.retrievePasswordAccessToken(clientDetails(),
                tokenRequest.getScopes(), tokenRequest.getUsername());
        final OAuthResponse tokenResponse = createTokenResponse(accessToken, false);

        LOG.debug("'password' response: {}", tokenResponse);
        WebUtils.writeOAuthJsonResponse(response, tokenResponse);
    }
}
