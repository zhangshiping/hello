package com.company.project.authz.domain.rs;

import org.apache.oltu.oauth2.common.message.OAuthResponse;

/**
 * Created by eayon on 2017-07-24.
 */
public class OAuthRSResponse extends OAuthResponse {
    protected OAuthRSResponse(String uri, int responseStatus) {
        super(uri, responseStatus);
    }

    public static class OAuthRSResponseBuilder extends OAuthResponse.OAuthResponseBuilder {

        public OAuthRSResponseBuilder(int responseCode) {
            super(responseCode);
        }
    }
}
