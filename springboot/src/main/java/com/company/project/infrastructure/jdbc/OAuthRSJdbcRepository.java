package com.company.project.infrastructure.jdbc;

import com.company.project.authz.domain.oauth.AccessToken;
import com.company.project.authz.domain.oauth.ClientDetails;
import com.company.project.authz.domain.rs.OAuthRSRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by eayon on 2017-07-24.
 */
@Repository("oauthRSJdbcRepository")
public class OAuthRSJdbcRepository extends AbstractJdbcRepository implements OAuthRSRepository {

    private static ClientDetailsRowMapper clientDetailsRowMapper = new ClientDetailsRowMapper();
    private static AccessTokenRowMapper accessTokenRowMapper = new AccessTokenRowMapper();


    @Override
    public AccessToken findAccessTokenByTokenId(String tokenId) {
        final String sql = " select * from oauth_access_token where token_id = ?";
        final List<AccessToken> list = jdbcTemplate.query(sql, accessTokenRowMapper, tokenId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public ClientDetails findClientDetailsByClientIdAndResourceIds(String clientId, String resourceIds) {
        final String sql = " select * from oauth_client_details where archived = 0 and client_id = ? and resource_ids = ? ";
        final List<ClientDetails> list = jdbcTemplate.query(sql, clientDetailsRowMapper, clientId, resourceIds);
        return list.isEmpty() ? null : list.get(0);
    }
}
