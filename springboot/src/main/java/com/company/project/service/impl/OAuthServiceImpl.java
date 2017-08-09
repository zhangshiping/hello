package com.company.project.service.impl;

import com.company.project.authz.domain.oauth.*;
import com.company.project.service.OAuthService;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by eayon on 2017-07-18.
 */
@Service("oauthService")
public class OAuthServiceImpl implements OAuthService {
    private static final Logger LOG = LoggerFactory.getLogger(OAuthServiceImpl.class);

    @Autowired
    private OAuthRepository oauthRepository;
    @Autowired
    private AuthenticationIdGenerator authenticationIdGenerator;
    @Autowired
    private OAuthIssuer oAuthIssuer;
    @Override
    public ClientDetails loadClientDetails(String clientId) {
        LOG.debug("Load ClientDetails by clientId: {}", clientId);
        return oauthRepository.findClientDetails(clientId);
    }

    @Override
    public OAuthCode saveAuthorizationCode(String authCode, ClientDetails clientDetails) {
        return null;
    }

    @Override
    public String retrieveAuthCode(ClientDetails clientDetails) throws OAuthSystemException {
        return null;
    }

    @Override
    public AccessToken retrieveAccessToken(ClientDetails clientDetails, Set<String> scopes, boolean includeRefreshToken) throws OAuthSystemException {
        return null;
    }

    @Override
    public AccessToken retrieveNewAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException {
        return null;
    }

    @Override
    public OAuthCode loadOauthCode(String code, ClientDetails clientDetails) {
        return null;
    }

    @Override
    public boolean removeOauthCode(String code, ClientDetails clientDetails) {
        return false;
    }

    @Override
    public AccessToken retrieveAuthorizationCodeAccessToken(ClientDetails clientDetails, String code) throws OAuthSystemException {
        return null;
    }

    @Override
    public AccessToken retrievePasswordAccessToken(ClientDetails clientDetails, Set<String> scopes, String username) throws OAuthSystemException {
        String scope = OAuthUtils.encodeScopes(scopes);
        final String clientId = clientDetails.getClientId();

        final String authenticationId = authenticationIdGenerator.generate(clientId, username, scope);
        AccessToken accessToken = oauthRepository.findAccessToken(clientId, username, authenticationId);

        boolean needCreate = false;
        if (accessToken == null) {
            needCreate = true;
            LOG.debug("Not found AccessToken from repository, will create a new one, client_id: {}", clientId);
        } else if (accessToken.tokenExpired()) {
            LOG.debug("Delete expired AccessToken: {} and create a new one, client_id: {}", accessToken, clientId);
            oauthRepository.deleteAccessToken(accessToken);
            needCreate = true;
        } else {
            LOG.debug("Use existed AccessToken: {}, client_id: {}", accessToken, clientId);
        }

        if (needCreate) {
            accessToken = createAndSaveAccessToken(clientDetails, clientDetails.supportRefreshToken(), username, authenticationId);
            LOG.debug("Create a new AccessToken: {}", accessToken);
        }

        return accessToken;
    }

    @Override
    public AccessToken retrieveClientCredentialsAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException {
        return null;
    }


    @Override
    public AccessToken loadAccessTokenByRefreshToken(String refreshToken, String clientId) {
        LOG.debug("Load ClientDetails by refreshToken: {} and clientId: {}", refreshToken, clientId);
        return oauthRepository.findAccessTokenByRefreshToken(refreshToken, clientId);
    }

    /*
    * Get AccessToken
    * Generate a new AccessToken from existed(exclude token,refresh_token)
    * Update access_token,refresh_token, expired.
    * Save and remove old
    * */
    @Override
    public AccessToken changeAccessTokenByRefreshToken(String refreshToken, String clientId) throws OAuthSystemException {
        final AccessToken oldToken = loadAccessTokenByRefreshToken(refreshToken, clientId);

        AccessToken newAccessToken = oldToken.cloneMe();
        LOG.debug("Create new AccessToken: {} from old AccessToken: {}", newAccessToken, oldToken);

        ClientDetails details = oauthRepository.findClientDetails(clientId);
        newAccessToken.updateByClientDetails(details);

        final String authId = authenticationIdGenerator.generate(clientId, oldToken.username(), null);
        newAccessToken.authenticationId(authId)
                .tokenId(oAuthIssuer.accessToken())
                .refreshToken(oAuthIssuer.refreshToken());

        oauthRepository.deleteAccessToken(oldToken);
        LOG.debug("Delete old AccessToken: {}", oldToken);

        oauthRepository.saveAccessToken(newAccessToken);
        LOG.debug("Save new AccessToken: {}", newAccessToken);

        return newAccessToken;
    }

    @Override
    public boolean isExistedClientId(String clientId) {
        return false;
    }


    private AccessToken createAndSaveAccessToken(ClientDetails clientDetails, boolean includeRefreshToken, String username, String authenticationId) throws OAuthSystemException {
        AccessToken accessToken = new AccessToken()
                .clientId(clientDetails.getClientId())
                .username(username)
                .tokenId(oAuthIssuer.accessToken())
                .authenticationId(authenticationId)
                .updateByClientDetails(clientDetails);

        if (includeRefreshToken) {
            accessToken.refreshToken(oAuthIssuer.refreshToken());
        }

        this.oauthRepository.saveAccessToken(accessToken);
        LOG.debug("Save AccessToken: {}", accessToken);
        return accessToken;
    }
}
