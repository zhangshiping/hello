package com.company.project.web;

import com.company.project.authz.OAuthTokenxRequest;
import com.company.project.authz.oauth.token.OAuthTokenHandleDispatcher;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by eayon on 2017-07-19.
 */
@Controller
@RequestMapping("oauth/")
public class OAuthTokenController {
    @RequestMapping("token")
    public void authorize(HttpServletRequest request, HttpServletResponse response) throws OAuthSystemException {
        try {
            OAuthTokenxRequest tokenRequest = new OAuthTokenxRequest(request);

            OAuthTokenHandleDispatcher tokenHandleDispatcher = new OAuthTokenHandleDispatcher(tokenRequest, response);
            tokenHandleDispatcher.dispatch();

        } catch (OAuthProblemException e) {
            //exception
            OAuthResponse oAuthResponse = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_FOUND)
                    .location(e.getRedirectUri())
                    .error(e)
                    .buildJSONMessage();
            WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
        }

    }
}
