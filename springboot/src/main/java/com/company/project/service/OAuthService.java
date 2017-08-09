package com.company.project.service;

import com.company.project.authz.domain.oauth.AccessToken;
import com.company.project.authz.domain.oauth.ClientDetails;
import com.company.project.authz.domain.oauth.OAuthCode;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.util.Set;

/**
 * Created by eayon on 2017-07-18.
 */
public interface OAuthService {

    ClientDetails loadClientDetails(String clientId);

    OAuthCode saveAuthorizationCode(String authCode, ClientDetails clientDetails);

    String retrieveAuthCode(ClientDetails clientDetails) throws OAuthSystemException;

    AccessToken retrieveAccessToken(ClientDetails clientDetails, Set<String> scopes, boolean includeRefreshToken) throws OAuthSystemException;

    //Always return new AccessToken, exclude refreshToken
    AccessToken retrieveNewAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException;

    OAuthCode loadOauthCode(String code, ClientDetails clientDetails);

    boolean removeOauthCode(String code, ClientDetails clientDetails);

    //Always return new AccessToken,  grant_type=authorization_code
    AccessToken retrieveAuthorizationCodeAccessToken(ClientDetails clientDetails, String code) throws OAuthSystemException;

    //grant_type=password AccessToken
    AccessToken retrievePasswordAccessToken(ClientDetails clientDetails, Set<String> scopes, String username) throws OAuthSystemException;

    //grant_type=client_credentials
    AccessToken retrieveClientCredentialsAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException;

    AccessToken loadAccessTokenByRefreshToken(String refreshToken, String clientId);

    AccessToken changeAccessTokenByRefreshToken(String refreshToken, String clientId) throws OAuthSystemException;

    boolean isExistedClientId(String clientId);
}
