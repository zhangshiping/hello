package com.company.project.configurer.shiro;

import com.company.project.authz.domain.oauth.AuthenticationIdGenerator;
import com.company.project.authz.domain.oauth.DefaultAuthenticationIdGenerator;
import com.company.project.service.OAuthRSService;
import com.company.project.service.impl.OAuthRSServiceImpl;
import com.company.project.web.content.ShiroController;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.*;


/**
 * Created by eayon on 2017-07-17.
 */
@Configuration
public class ShiroConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(ShiroController.class);

    @Bean
    public SimpleCredentialsMatcher resourceCredentialsMatcher(){
        return new SimpleCredentialsMatcher();
    }

    @Bean
    public HashedCredentialsMatcher authzCredentialsMatcher(){
        return new HashedCredentialsMatcher("MD5");
    }

    @Bean
    public OAuth2CredentialsMatcher credentialsMatcher(){
        OAuth2CredentialsMatcher oAuth2CredentialsMatcher = new OAuth2CredentialsMatcher();
        oAuth2CredentialsMatcher.setResourcesCredentialsMatcher(resourceCredentialsMatcher());
        oAuth2CredentialsMatcher.setAuthzCredentialsMatcher(authzCredentialsMatcher());
        return oAuth2CredentialsMatcher;

    }

    @Bean
    public OAuthRSService oAuthRSService(){
        return new OAuthRSServiceImpl();
    }

    @Bean
    public Collection<Realm> jdbcRealms(DataSource dataSource, OAuth2CredentialsMatcher credentialsMatcher){
       Collection<Realm> jdbcRealms = new ArrayList<>();
        OAuth2JdbcRealm oAuth2JdbcRealm = new OAuth2JdbcRealm();
        oAuth2JdbcRealm.setName("oAuth2jdbcRealm");
        oAuth2JdbcRealm.setDataSource(dataSource);
        oAuth2JdbcRealm.setCredentialsMatcher(credentialsMatcher);
        oAuth2JdbcRealm.setPermissionsLookupEnabled(true);
        oAuth2JdbcRealm.setRsService(oAuthRSService());

        CustomJdbcRealm customJdbcRealm = new CustomJdbcRealm();
        customJdbcRealm.setName("jdbcRealm");
        customJdbcRealm.setDataSource(dataSource);
        customJdbcRealm.setCredentialsMatcher(credentialsMatcher);
        customJdbcRealm.setPermissionsLookupEnabled(true);

        jdbcRealms.add(oAuth2JdbcRealm);
        jdbcRealms.add(customJdbcRealm);
        return  jdbcRealms;
    }

    @Bean
    public EhCacheManager getEhCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        //ehCacheManager.setCacheManagerConfigFile("classpath:aa.xml");
        return  ehCacheManager;

    }

    @Bean
    public OAuth2SubjectFactory subjectFactory() {
        return  new OAuth2SubjectFactory();
    }
    @Bean
    public DefaultWebSecurityManager securityManager(Collection<Realm> jdbcRealms){

        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        //defaultSecurityManager.setRealm(jdbcRealm);
        defaultSecurityManager.setRealms(jdbcRealms);
        defaultSecurityManager.setSubjectFactory(subjectFactory());
        return  defaultSecurityManager;
    }


    @Bean
    public OAuthIssuerImpl oAuthIssuer(){
        return new OAuthIssuerImpl(new MD5Generator());
    }


    @Bean
    public AuthenticationIdGenerator authenticationIdGenerator(){
        return new DefaultAuthenticationIdGenerator();
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");


        /**
         /favicon.ico = anon
         /resources/** = anon
         /login = anon
         /unauthorized = anon
         # OAuth anon
         /oauth/** = anon
         /users/** = anon
         /client_details* = anon
         /client_details/** = anon
         /logout = logout
         # admin role
         /admin/** = authc, roles["Admin"]
         #user permissions
         /user/list = authc, perms["user:list"]
         /user/create = authc, perms["user:create"]
         # everything else requires authentication:
         /** = authc
         * **/

        HashMap<String, Filter> map = new HashMap<>();
        map.put("oauth",oAuth2Filter());
        shiroFilterFactoryBean.setFilters(map);


        HashMap filterMap = new LinkedHashMap();
        filterMap.put("/favicon.ico","anon");
        filterMap.put("/swagger-ui.html","anon");
//        filterMap.put("/user/**","oauth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);


        return shiroFilterFactoryBean;
    }


    @Bean
    public OAuth2Filter oAuth2Filter(){
        OAuth2Filter oAuth2Filter = new OAuth2Filter();
        oAuth2Filter.setResourceId("os-resource");
        oAuth2Filter.setRsService(oAuthRSService());
        return oAuth2Filter;
    }
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
//        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
//        filterRegistration.setEnabled(true);
//        filterRegistration.addUrlPatterns("/*");
//      //  filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
//        return filterRegistration;
//    }

}
