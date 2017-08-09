package com.company.project.authz.domain.oauth;


import com.company.project.authz.domain.shared.Repository;

import java.util.List;

/**
 * 15-6-13
 *
 */
public interface OAuthRepository extends Repository {

    ClientDetails findClientDetails(String clientId);

    int saveClientDetails(ClientDetails clientDetails);

    int saveOauthCode(OAuthCode oauthCode);

    OAuthCode findOauthCode(String code, String clientId);

    OAuthCode findOauthCodeByUsernameClientId(String username, String clientId);

    int deleteOauthCode(OAuthCode oauthCode);

    AccessToken findAccessToken(String clientId, String username, String authenticationId);

    int deleteAccessToken(AccessToken accessToken);

    int saveAccessToken(AccessToken accessToken);

    AccessToken findAccessTokenByRefreshToken(String refreshToken, String clientId);

    List<ClientDetails> findClientDetailsListByClientId(String clientId);
}
