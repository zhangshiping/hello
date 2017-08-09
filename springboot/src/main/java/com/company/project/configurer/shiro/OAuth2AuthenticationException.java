package com.company.project.configurer.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Created by eayon on 2017-07-24.
 */
public class OAuth2AuthenticationException extends AuthenticationException {

    public OAuth2AuthenticationException() {
    }

    public OAuth2AuthenticationException(String message) {
        super(message);
    }

    public OAuth2AuthenticationException(Throwable cause) {
        super(cause);
    }

    public OAuth2AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

