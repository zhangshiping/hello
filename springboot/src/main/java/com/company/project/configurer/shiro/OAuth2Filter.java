package com.company.project.configurer.shiro;

import com.company.project.authz.domain.oauth.AccessToken;
import com.company.project.authz.domain.rs.OAuthRSResponse;
import com.company.project.service.OAuthRSService;
import com.company.project.web.WebUtils;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by eayon on 2017-07-24.
 */
public class OAuth2Filter extends AuthenticatingFilter implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2Filter.class);


    private String resourceId;
    private OAuthRSService rsService;


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        final String accessToken = getAccessToken(httpRequest);
        final AccessToken token = rsService.loadAccessTokenByTokenId(accessToken);

        String username = null;
        if (token != null) {
            LOGGER.debug("Set username and clientId from AccessToken: {}", token);
            username = token.username();
            httpRequest.setAttribute(OAuth.OAUTH_CLIENT_ID, token.clientId());
        } else {
            LOGGER.debug("Not found AccessToken by access_token: {}", accessToken);
        }

        return new OAuth2Token(accessToken, resourceId)
                .setUserId(username);
    }

    private String getAccessToken(HttpServletRequest httpRequest) {
        final String authorization = httpRequest.getHeader("Authorization");
        if (authorization != null) {
            //bearer ab1ade69-d122-4844-ab23-7b109ad977f0
            return authorization.substring(6).trim();
        }
        return httpRequest.getParameter(OAuth.OAUTH_ACCESS_TOKEN);
    }


    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {

        final OAuthResponse oAuthResponse;
        try {
            oAuthResponse = OAuthRSResponse.errorResponse(401)
                    .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                    .setErrorDescription(ae.getMessage())
                    .buildJSONMessage();

            WebUtils.writeOAuthJsonResponse((HttpServletResponse) response, oAuthResponse);

        } catch (OAuthSystemException e) {
            LOGGER.error("Build JSON message error", e);
            throw new IllegalStateException(e);
        }

        return false;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setRsService(OAuthRSService rsService) {
        this.rsService = rsService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(resourceId, "resourceId is null");
        Assert.notNull(rsService, "rsService is null");
    }

}
