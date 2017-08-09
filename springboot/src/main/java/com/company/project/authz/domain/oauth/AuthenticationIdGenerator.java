package com.company.project.authz.domain.oauth;

/**
 * Created by eayon on 2017-07-20.
 */
public interface AuthenticationIdGenerator {
    public String generate(String clientId,String username,String scope);
}
