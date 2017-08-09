package com.company.project.authz.oauth.token;

import com.company.project.authz.OAuthTokenxRequest;
import com.company.project.authz.oauth.OAuthHandler;
import com.company.project.authz.oauth.validator.AbstractClientDetailsValidator;
import com.company.project.web.WebUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by eayon on 2017-07-14.
 */
public abstract class AbstractOAuthTokenHandler extends OAuthHandler implements OAuthTokenHandler {
    private static Logger LOG = LoggerFactory.getLogger(AbstractOAuthTokenHandler.class);
    protected OAuthTokenxRequest tokenRequest;
    protected HttpServletResponse response;

    @Override
    public void handle(OAuthTokenxRequest tokenRequest, HttpServletResponse response) throws OAuthProblemException, OAuthSystemException {
        this.tokenRequest = tokenRequest;
        this.response = response;

        if (validateFailed()){
            return;
        }

        handlerAfterValidation();
    }

    @Override
    protected String clientId() {
        return tokenRequest.getClientId();
    }

    protected boolean validateFailed() throws OAuthSystemException{
        AbstractClientDetailsValidator validator = getValidator();
        LOG.debug("Use [{}] validate client: {}", validator, tokenRequest.getClientId());

        final OAuthResponse oAuthResponse = validator.validate();

        return checkAndResponseValidateFailed(oAuthResponse);
    }

    protected boolean checkAndResponseValidateFailed(OAuthResponse oAuthResponse) {
        if (oAuthResponse != null) {
            LOG.debug("Validate OAuthAuthzRequest(client_id={}) failed", tokenRequest.getClientId());
            WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
            return true;
        }
        return false;
    }

    protected abstract AbstractClientDetailsValidator getValidator();

    protected abstract void handlerAfterValidation() throws OAuthProblemException,OAuthSystemException;
}
