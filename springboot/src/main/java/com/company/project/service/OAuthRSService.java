package com.company.project.service;

import com.company.project.authz.domain.oauth.AccessToken;
import com.company.project.authz.domain.oauth.ClientDetails;

/**
 * Created by eayon on 2017-07-24.
 */
public interface OAuthRSService {
    AccessToken loadAccessTokenByTokenId(String tokenId);
    ClientDetails loadClientDetails(String clientId, String resourceIds);
}
