package com.company.project.authz.oauth.validator;

import com.company.project.authz.OAuthTokenxRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by eayon on 2017-07-18.
 */
public abstract class AbstractOAuthTokenValidator extends AbstractClientDetailsValidator {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractOAuthTokenValidator.class);


    protected OAuthTokenxRequest tokenRequest;

    protected AbstractOAuthTokenValidator(OAuthTokenxRequest tokenRequest) {
        super(tokenRequest);
        this.tokenRequest = tokenRequest;
    }


    protected String grantType() {
        return tokenRequest.getGrantType();
    }


    protected OAuthResponse invalidGrantTypeResponse(String grantType) throws OAuthSystemException {
        return OAuthResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setError(OAuthError.TokenResponse.INVALID_GRANT)
                .setErrorDescription("Invalid grant_type '" + grantType + "'")
                .buildJSONMessage();
    }


    //true is invalided
    protected boolean invalidUsernamePassword() {
        final String username = tokenRequest.getUsername();
        final String password = tokenRequest.getPassword();

        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            SecurityUtils.getSubject().login(token);
        } catch (Exception e) {
            LOG.debug("Login failed by username: " + username, e);
            return true;
        }
        return false;
    }

    protected OAuthResponse invalidUsernamePasswordResponse() throws OAuthSystemException {
        return OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_GRANT)
                .setErrorDescription("Bad credentials")
                .buildJSONMessage();
    }
}
