package com.general.template.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.ant;

@Configuration
public class SwaggerConfig {

    @Value("${app.client.id}")
    private String clientId;
    @Value("${app.client.secret}")
    private String clientSecret;

    @Value("${host.full.dns.auth.link}")
    private String authLink;

    @Bean
    public Docket swaggerConfiguration() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build()
                .apiInfo(metadata()).securitySchemes(Collections.singletonList(securitySchema())).securityContexts(Collections.singletonList(securityContext())).pathMapping("/");
    }

    private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = new ArrayList();
        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
        authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
        authorizationScopeList.add(new AuthorizationScope("write", "access all"));

        List<GrantType> grantTypes = new ArrayList();
        GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(authLink+"/oauth/token");

        grantTypes.add(creGrant);

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(Predicates.and(ant("/**"), Predicates.not(ant("/all/**"))))
                .build();
    }

    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");

        return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
    }

    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(clientId, clientSecret, "", "", "", ApiKeyVehicle.HEADER, "", " ");
    }

//    @Bean
//    public SecurityConfiguration security() {
//        return SecurityConfigurationBuilder.builder()
//                .clientId("omc-hrms-rest-client")
//                .clientSecret("omc-hrms-rest-secret")
//                .scopeSeparator(" ")
//                .useBasicAuthenticationWithAccessCodeGrant(true)
//                .build();
//    }
//
//    private SecurityScheme securityScheme() {
//        GrantType grantType = new AuthorizationCodeGrantBuilder()
//                .tokenEndpoint(new TokenEndpoint(baseUrl + "/token", "oauthtoken"))
//                .tokenRequestEndpoint(
//                        new TokenRequestEndpoint(baseUrl + "/authorize", "omc-hrms-rest-client", "omc-hrms-rest-secret"))
//                .build();
//
//        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
//                .grantTypes(Arrays.asList(grantType))
//                .scopes(Arrays.asList(scopes()))
//                .build();
//        return oauth;
//    }
//
//    private AuthorizationScope[] scopes() {
//        AuthorizationScope[] scopes = {
//                new AuthorizationScope("read", "for read operations"),
//                new AuthorizationScope("write", "for write operations"),
//                new AuthorizationScope("foo", "Access foo API") };
//        return scopes;
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(
//                        Arrays.asList(new SecurityReference("spring_oauth", scopes())))
//                .forPaths(PathSelectors.regex("/foos.*"))
//                .build();
//    }

//    @Bean
//    public Docket swaggerAdminConfiguration() {
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("admin")
//                .select()
//                .paths(PathSelectors.ant("/admin/*"))
//                .apis(RequestHandlerSelectors.basePackage("com.sweetitech.hrm.controller.admin"))
//                .build()
//                .apiInfo(metadata());
//    }



//    @Bean
//    public Docket swaggerAllConfiguration() {
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("all")
//                .select()
//                .paths(PathSelectors.ant("/all/*"))
//                .apis(RequestHandlerSelectors.basePackage("com.sweetitech.hrm"))
//                .build()
//                .apiInfo(metadata());
//    }
//
//    @Bean
//    public Docket swaggerOAuthConfiguration() {
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("oauth")
//                .select()
//                .paths(PathSelectors.ant("/oauth/*"))
//                .apis(RequestHandlerSelectors.basePackage("com.sweetitech.hrm"))
//                .build()
//                .apiInfo(metadata());
//    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title( "OMC HRMS" )
                .description( "Api documentation for omc hrms." )
                .version( "1.0.0" )
                .contact(new Contact("Tasnim Ankon", "www.sweetitech.co.uk", "tasnim.ankon@gmail.com"))
                .build();
    }
}
