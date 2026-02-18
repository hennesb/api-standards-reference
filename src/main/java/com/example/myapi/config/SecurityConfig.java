package com.example.myapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String GENERATED_PACKAGE = "com.example.myapi.contract.definition";

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for local API testing
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll() // Allow all traffic
//                )
//                .formLogin(form -> form.disable()) // Turn off the login page
//                .httpBasic(basic -> basic.disable()); // Turn off the pop-up prompt
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers( "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        // Internal endpoints protected by Client Credentials scope
                        .requestMatchers("/customer/**").hasAuthority("SCOPE_customer:read")
                        .requestMatchers("/staff/**").hasAuthority("SCOPE_customer:manage")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // For development, you can point this to your JWK Set URI
        return NimbusJwtDecoder.withJwkSetUri("https://auth.example.com/oauth2/jwks").build();
    }

    @Bean
    public GroupedOpenApi internalApi() {
        return GroupedOpenApi.builder()
                .group("internal")
                .pathsToMatch("/staff/**")
                .build();
    }
    @Bean
    public GroupedOpenApi externalApi() {
        return GroupedOpenApi.builder()
                .group("external")
                .pathsToMatch("/customer/**")
                .build();
    }


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("M2MClientCredentials")) // Root level button
                .components(new Components()
                        .addSecuritySchemes("M2MClientCredentials",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .flows(new OAuthFlows()
                                                .clientCredentials(new OAuthFlow()
                                                        .tokenUrl("https://auth.example.com/oauth2/token")
                                                        .scopes(new Scopes()
                                                                .addString("customer:read", "Read customer data")
                                                                .addString("customer:create", "Create a customer record")
                                                                .addString("customer:update", "Update a customer record")
                                                                .addString("customer:manage", "Admin functions on a customer record")
                                                                .addString("pet:manage", "Admin functions on a pet record")
                                                        )
                                                )
                                        )
                        )
                );
    }

}