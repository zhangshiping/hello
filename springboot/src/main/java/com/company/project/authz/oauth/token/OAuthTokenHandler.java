package com.company.project.authz.oauth.token;

import com.company.project.authz.OAuthTokenxRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by eayon on 2017-07-14.
 */
public interface OAuthTokenHandler {

    boolean support(OAuthTokenxRequest tokenRequest) throws OAuthProblemException;

    void handle(OAuthTokenxRequest tokenRequest, HttpServletResponse response) throws OAuthProblemException, OAuthSystemException;
}