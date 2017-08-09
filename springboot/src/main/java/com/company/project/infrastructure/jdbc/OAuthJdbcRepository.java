package com.company.project.infrastructure.jdbc;


import com.company.project.authz.domain.oauth.AccessToken;
import com.company.project.authz.domain.oauth.ClientDetails;
import com.company.project.authz.domain.oauth.OAuthCode;
import com.company.project.authz.domain.oauth.OAuthRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 15-6-13
 * <p/>
 * OAUTH 的JDBC 操作实现
 *
 */
@Repository("oAuthJdbcRepository")
public class OAuthJdbcRepository extends AbstractJdbcRepository implements OAuthRepository {


    private static ClientDetailsRowMapper clientDetailsRowMapper = new ClientDetailsRowMapper();
    private static OAuthCodeRowMapper oauthCodeRowMapper = new OAuthCodeRowMapper();

    private static AccessTokenRowMapper accessTokenRowMapper = new AccessTokenRowMapper();


    @Override
    public ClientDetails findClientDetails(String clientId) {
        final String sql = " select * from oauth_client_details where archived = 0 and client_id = ? ";
        final List<ClientDetails> list = jdbcTemplate.query(sql, clientDetailsRowMapper, clientId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int saveClientDetails(final ClientDetails clientDetails) {
        final String sql = " insert into oauth_client_details(client_id,client_secret,client_name, client_uri,client_icon_uri,resource_ids, scope,grant_types, " +
                "redirect_uri,roles,access_token_validity,refresh_token_validity,description,archived,trusted) values (?,?,?, ?,?,?,?,?, ?,?, ?,? ,?,?,?)";

        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, clientDetails.getClientId());
                ps.setString(2, clientDetails.getClientSecret());
                ps.setString(3, clientDetails.getName());

                ps.setString(4, clientDetails.getClientUri());
                ps.setString(5, clientDetails.getIconUri());
                ps.setString(6, clientDetails.resourceIds());

                ps.setString(7, clientDetails.scope());
                ps.setString(8, clientDetails.grantTypes());
                ps.setString(9, clientDetails.getRedirectUri());

                ps.setString(10, clientDetails.roles());
                ps.setInt(11, clientDetails.accessTokenValidity() == null ? -1 : clientDetails.accessTokenValidity());
                ps.setInt(12, clientDetails.refreshTokenValidity() == null ? -1 : clientDetails.refreshTokenValidity());

                ps.setString(13, clientDetails.getDescription());
                ps.setBoolean(14, clientDetails.archived());
                ps.setBoolean(15, clientDetails.trusted());
            }
        });
    }

    @Override
    public int saveOauthCode(final OAuthCode oauthCode) {
        final String sql = " insert into oauth_code(code,username,client_id) values (?,?,?)";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, oauthCode.code());
                ps.setString(2, oauthCode.username());
                ps.setString(3, oauthCode.clientId());
            }
        });
    }

    @Override
    public OAuthCode findOauthCode(String code, String clientId) {
        final String sql = " select * from oauth_code where code = ? and client_id = ? ";
        final List<OAuthCode> list = jdbcTemplate.query(sql, oauthCodeRowMapper, code, clientId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public OAuthCode findOauthCodeByUsernameClientId(String username, String clientId) {
        final String sql = " select * from oauth_code where username = ? and client_id = ? ";
        final List<OAuthCode> list = jdbcTemplate.query(sql, oauthCodeRowMapper, username, clientId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int deleteOauthCode(final OAuthCode oauthCode) {
        final String sql = " delete from oauth_code where code = ? and client_id = ? and username = ?";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, oauthCode.code());
                ps.setString(2, oauthCode.clientId());
                ps.setString(3, oauthCode.username());
            }
        });
    }

    @Override
    public AccessToken findAccessToken(String clientId, String username, String authenticationId) {
        final String sql = " select * from oauth_access_token where client_id = ? and username = ? and authentication_id = ?";
        final List<AccessToken> list = jdbcTemplate.query(sql, accessTokenRowMapper, clientId, username, authenticationId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int deleteAccessToken(final AccessToken accessToken) {
        final String sql = " delete from oauth_access_token where client_id = ? and username = ? and authentication_id = ?";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, accessToken.clientId());
                ps.setString(2, accessToken.username());
                ps.setString(3, accessToken.authenticationId());
            }
        });
    }

    @Override
    public int saveAccessToken(final AccessToken accessToken) {
        final String sql = "insert into oauth_access_token(token_id,token_expired_seconds,authentication_id," +
                "username,client_id,token_type,refresh_token_expired_seconds,refresh_token) values (?,?,?,?,?,?,?,?) ";

        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, accessToken.tokenId());
                ps.setInt(2, accessToken.tokenExpiredSeconds());
                ps.setString(3, accessToken.authenticationId());

                ps.setString(4, accessToken.username());
                ps.setString(5, accessToken.clientId());
                ps.setString(6, accessToken.tokenType());

                ps.setInt(7, accessToken.refreshTokenExpiredSeconds());
                ps.setString(8, accessToken.refreshToken());
            }
        });
    }

    @Override
    public AccessToken findAccessTokenByRefreshToken(String refreshToken, String clientId) {
        final String sql = " select * from oauth_access_token where refresh_token = ? and client_id = ? ";
        final List<AccessToken> list = jdbcTemplate.query(sql, accessTokenRowMapper, refreshToken, clientId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<ClientDetails> findClientDetailsListByClientId(String clientId) {
        String sql = " select * from oauth_client_details where archived = 0 ";
        if (StringUtils.isNotEmpty(clientId)) {
            sql += " and client_id = ? order by create_time desc ";
            return jdbcTemplate.query(sql, clientDetailsRowMapper, clientId);
        }
        sql += " order by create_time desc  ";
        return jdbcTemplate.query(sql, clientDetailsRowMapper);
    }


}
