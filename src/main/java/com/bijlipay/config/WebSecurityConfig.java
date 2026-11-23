package com.bijlipay.config;

import com.bijlipay.config.security.JwtAuthenticationEntryPoint;
//import com.bijlipay.config.security.SecurityFilter;
import com.bijlipay.config.security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.util.Collections;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   /* @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf()
                .disable().authorizeRequests().antMatchers("/**").permitAll();

        return http.build();
    }*/

    /*@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");
//        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("Content-Disposition");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/


    @Autowired
    private SecurityFilter filter;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http
//                .cors()
//                .and()
//                .headers()
//                .contentSecurityPolicy("default-src 'self'; script-src 'self'; style-src 'self'; img-src 'self';")
//                .and()
//                .httpStrictTransportSecurity() // <-- Add HSTS
//                .includeSubDomains(true)
//                .maxAgeInSeconds(31536000) // 1 year
//                .and()
//                .and()
//                .csrf().disable().authorizeRequests().antMatchers("/tpt-request/**", "/kapture/**", "/tpt-onboard/**")
//                .authenticated().and().httpBasic().authenticationEntryPoint(unauthorizedHandler).and().authorizeRequests()
//                .antMatchers("/implementation/device/submit-to-mars-from-so-app1/**","/implementation/device/agg-lead-update/**","/actuator/health","/implementation/device/validate-token/**",
//                        "/implementation/device/activateSqr/**","/implementation/device/sqr-lead-mars-update/**","/implementation/device/submit-to-mars-from-so-app1/**"
//                        ,"/implementation/device/call-back-addOn","/implementation/device/qikberry/**","/implementation/device/refresh-implementation","/implementation/device/aggregator-mars-onboard-POS/**"
//                        ,"/implementation/device/retryAddOn/**","/implementation/device/retryAddOnQr/**","/implementation/device/tms-addOn-retrigger-Q161/**","/implementation/device/send-mobile-otp/**","/implementation/device/verify-mobile-otp/**","/implementation/device/mail","/swagger-ui/**","/swagger-ui.html/**","/v3/api-docs/**").permitAll()
//                .anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//        http.headers().frameOptions().disable(); // TO fix document preview issue.

        http
                .cors()
                .and()
                .csrf().disable()

                // Security Headers Configuration
                .headers()
                .contentSecurityPolicy("default-src 'self'; script-src 'self'; style-src 'self'; img-src 'self';")
                .and()
                .httpStrictTransportSecurity()
                .includeSubDomains(true)
                .maxAgeInSeconds(31536000)
                .and()
                .frameOptions().disable() // Fix document preview issue
                .and()

                // Request Authorizations - SINGLE CLEAN BLOCK
                .authorizeRequests()
                // 1. Authenticated Endpoints
                .antMatchers("/tpt-request/**", "/kapture/**", "/tpt-onboard/**").authenticated()

                // 2. Public Endpoints (Permit All)
                .antMatchers(
                        "/implementation/device/send-mobile-otp/**",
                        "/implementation/device/verify-mobile-otp/**", // <-- Public Access Fixed Here
                        "/implementation/device/submit-to-mars-from-so-app1/**",
                        "/implementation/device/agg-lead-update/**",
                        "/actuator/health",
                        "/implementation/device/validate-token/**",
                        "/implementation/device/activateSqr/**",
                        "/implementation/device/sqr-lead-mars-update/**",
                        "/implementation/device/call-back-addOn",
                        "/implementation/device/qikberry/**",
                        "/implementation/device/refresh-implementation",
                        "/implementation/device/aggregator-mars-onboard-POS/**",
                        "/implementation/device/retryAddOn/**",
                        "/implementation/device/retryAddOnQr/**",
                        "/implementation/device/tms-addOn-retrigger-Q161/**",
                        "/implementation/device/mail",
                        "/swagger-ui/**",
                        "/swagger-ui.html/**",
                        "/v3/api-docs/**"
                ).permitAll()

                // 3. All other requests require Authentication
                .anyRequest().authenticated()
                .and()

                // Exception Handling & Session Management
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // JWT Filter
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

    }
}
