
package com.bijlipay.config.security;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class SecurityFilter extends OncePerRequestFilter {

//    @Autowired
//    private UserDetailsService userDetailsService;
//
//
//    @Autowired
//    private JwtUtil util;
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//
//        Gson gson = new Gson();
//        Map<String, Object> requestData = new HashMap<>();
//        requestData.put("method", request.getMethod());
//        requestData.put("requestURI", request.getRequestURI());
//        requestData.put("headers", getHeadersInfo(request));
//        requestData.put("parameters", request.getParameterMap());
//
//        String jsonRequest = gson.toJson(requestData);
//        System.out.println("Json Request: " + jsonRequest);
//        String token = null;
//        if (request.getCookies() != null) {
//            System.out.println("cookies entered");
//            for (Cookie cookie : request.getCookies()) {
//                String value=AESUtil.decrypt(cookie.getValue());
//                if ("jwt".equals(cookie.getName())) {
//                    System.out.println("cookies" + cookie);
//                    token = value;
//                    break;
//                }
//            }
//        } else {
//            System.out.println("jwtHeader" + request.getHeader("Authorization"));
//            token = request.getHeader("Authorization");
//
//        }
//
//        if (token != null) {
//            //do validation
//            String username = util.getUsername(token);
//            if (username != null &&
//                    SecurityContextHolder.getContext()
//                            .getAuthentication() == null) {
//                UserDetails user = userDetailsService.loadUserByUsername(username);
//                //validate token
//                boolean isValid = util.validateToken(token, username);
//                if (isValid) {
//                    UsernamePasswordAuthenticationToken authtoken
//                            = new UsernamePasswordAuthenticationToken(username, user.getPassword(),
//                            user.getAuthorities());
//                    authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    //final object stored
//                    SecurityContextHolder.getContext().setAuthentication(authtoken);
//                }
//            }
//        }
//        chain.doFilter(request, response);
//
//    }
//    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
//        Map<String, String> headersMap = new HashMap<>();
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            headersMap.put(headerName, request.getHeader(headerName));
//        }
//        return headersMap;
//    }


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil util;

    // 1. SKIP FILTER FOR PUBLIC ENDPOINTS
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.contains("/implementation/device/verify-mobile-otp") ||
                path.contains("/implementation/device/send-mobile-otp") ||
                path.contains("/actuator/health") ||
                path.contains("/swagger-ui") ||
                path.contains("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        Gson gson = new Gson();
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("method", request.getMethod());
        requestData.put("requestURI", request.getRequestURI());
        requestData.put("headers", getHeadersInfo(request));
        requestData.put("parameters", request.getParameterMap());

        String jsonRequest = gson.toJson(requestData);
        System.out.println("Json Request: " + jsonRequest);
        String token = null;

        if (request.getCookies() != null) {
            System.out.println("cookies entered");
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    try {
                        token = AESUtil.decrypt(cookie.getValue());
                    } catch (Exception e) {
                        System.out.println("Cookie decryption failed: " + e.getMessage());
                    }
                    break;
                }
            }
        } else {
            String authHeader = request.getHeader("Authorization");
            System.out.println("jwtHeader: " + authHeader);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            } else {
                token = authHeader;
            }
        }

        // 2. SAFE JWT VALIDATION WITH TRY-CATCH
        if (token != null && !token.trim().isEmpty() && !"null".equalsIgnoreCase(token)) {
            try {
                String username = util.getUsername(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails user = userDetailsService.loadUserByUsername(username);
                    boolean isValid = util.validateToken(token, username);
                    if (isValid) {
                        UsernamePasswordAuthenticationToken authtoken =
                                new UsernamePasswordAuthenticationToken(username, user.getPassword(), user.getAuthorities());
                        authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authtoken);
                    }
                }
            } catch (Exception e) {
                // Log exception without blocking public flow
                System.out.println("JWT Token processing failed: " + e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }

    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> headersMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headersMap.put(headerName, request.getHeader(headerName));
        }
        return headersMap;
    }

}



