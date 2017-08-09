package com.company.project.authz.domain.rs;

import com.company.project.authz.domain.oauth.AccessToken;
import com.company.project.authz.domain.oauth.ClientDetails;
import com.company.project.authz.domain.shared.Repository;

/**
 * Created by eayon on 2017-07-24.
 */
public interface OAuthRSRepository extends Repository {

    AccessToken findAccessTokenByTokenId(String tokenId);

    ClientDetails findClientDetailsByClientIdAndResourceIds(String clientId, String resourceIds);

}
