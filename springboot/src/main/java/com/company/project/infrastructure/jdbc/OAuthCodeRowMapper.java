package com.company.project.infrastructure.jdbc;

import com.company.project.authz.domain.oauth.OAuthCode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by eayon on 2017-07-19.
 */
public class OAuthCodeRowMapper implements RowMapper<OAuthCode> {


    public OAuthCodeRowMapper(){

    }

    @Override
    public OAuthCode mapRow(ResultSet resultSet, int i) throws SQLException {
        final OAuthCode oAuthCode = new OAuthCode()
                .clientId(resultSet.getString("client_id"))
                .username(resultSet.getString("username"))
                .code(resultSet.getString("code"));

        oAuthCode.createTime(resultSet.getTimestamp("create_time"));

        return  oAuthCode;
    }
}
