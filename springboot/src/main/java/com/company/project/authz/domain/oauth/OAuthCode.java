package com.company.project.authz.domain.oauth;

import com.company.project.authz.domain.AbstractDomain;

/**
 *
 *  用于在  grant_type = authorization_code 流程中记录生成的 code信息
 *
 * Created by eayon on 2017-07-18.
 */

public class OAuthCode extends AbstractDomain {

    private static final long serialVersionUID = 7861853986708936572L;
    private String code;

    private String username;

    private String clientId;

    public OAuthCode() {
    }


    public String code() {
        return code;
    }

    public OAuthCode code(String code) {
        this.code = code;
        return this;
    }

    public String username() {
        return username;
    }

    public OAuthCode username(String username) {
        this.username = username;
        return this;
    }

    public String clientId() {
        return clientId;
    }

    public OAuthCode clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

}
