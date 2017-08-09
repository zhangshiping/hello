package com.company.project.authz.oauth;

import com.company.project.authz.domain.oauth.AccessToken;
import com.company.project.authz.domain.oauth.ClientDetails;
import com.company.project.authz.domain.shared.BeanProvider;
import com.company.project.service.OAuthService;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;


/**
 * Created by eayon on 2017-07-14.
 */
public abstract class OAuthHandler {


    private static final Logger LOG = LoggerFactory.getLogger(OAuthHandler.class);

    private ClientDetails clientDetails;
    protected transient OAuthService oAuthService = BeanProvider.getBean(OAuthService.class);

    protected ClientDetails clientDetails(){
        if(clientDetails == null){
            final String clientId = clientId();
            clientDetails = oAuthService.loadClientDetails(clientId);
            LOG.debug("Load ClientDetails: {} by clientId: {}", clientDetails, clientId);

            LOG.debug("Load ClientDetails: {} by clientId: {}", clientDetails, clientId);
        }
        return clientDetails;
    }

    protected OAuthResponse createTokenResponse(AccessToken accessToken,boolean queryOrJson) throws OAuthSystemException{
        final ClientDetails tempClientDetails = clientDetails();
        final OAuthASResponse.OAuthTokenResponseBuilder builder = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
                .location(tempClientDetails.getClientUri())
                .setAccessToken(accessToken.tokenId())
                .setExpiresIn(String.valueOf(accessToken.currentTokenExpiredSeconds()))
                .setTokenType(accessToken.tokenType());
        final String refreshToken = accessToken.refreshToken();

        if (refreshToken != null){
            builder.setRefreshToken(refreshToken);
        }

        return  queryOrJson ? builder.buildQueryMessage() : builder.buildJSONMessage();
    }


    protected abstract String clientId();
}
