package com.itm.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itm.api.security.dto.LoginResponseDTO;
import com.itm.api.user.model.dto.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static com.itm.api.base.Constants.*;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AuthenticationFilter() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Value("${authentication.api.base.url}")
    private String authenticationApiBaseUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info("{} {}", request.getMethod(), request.getRequestURI());
        String auth = request.getHeader("Authorization");
        if (auth == null) {
            auth = request.getHeader("X-Authorization");
        }
        LoginDTO loginDTO = decodeAuthorizationHeader(auth);
        if (loginDTO.getUsername() == null) {
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
        }
        UserAuth user;
        if (LOGIN_ENDPOINT.equals(request.getRequestURI())) {
            user = authWithCredentials(loginDTO);
        } else if (LOGOUT_ENDPOINT.equals(request.getRequestURI())) {
            user = authWithActiveSession(loginDTO);
            if (user != null) {
                logoutUser(loginDTO);
            }
        } else {
            user = authWithActiveSession(loginDTO);
        }
        SecurityContextHolder.getContext().setAuthentication(user);
        filterChain.doFilter(request, response);
    }

    private UserAuth authWithCredentials(LoginDTO auth) {
        UserAuth userAuth = null;
        try {
            String loginResponseDTO = restTemplate.postForEntity(authenticationApiBaseUrl + LOGIN_URI, auth, String.class).getBody();
            LoginResponseDTO loginResponse = null;
            try {
                loginResponse = objectMapper.readValue(loginResponseDTO, LoginResponseDTO.class);
            } catch (Exception ignored) {
            }
            if (loginResponse != null && loginResponse.getLoginSession() != 0) {
                userAuth = new UserAuth(auth.getUsername(), null, List.of());
                return userAuth;
            }
        } catch (Exception e) {
            LOGGER.info("Credentials auth failed", e);
        }
        return userAuth;
    }

    private void logoutUser(LoginDTO loginDTO) {
        try {
            restTemplate.postForEntity(authenticationApiBaseUrl + LOGOUT_URI, loginDTO, String.class);
        } catch (Exception e) {
            LOGGER.info("Logout failed", e);
        }
    }

    private UserAuth authWithActiveSession(LoginDTO auth) {
        UserAuth userAuth = null;
        try {
            String loginResponseDTO = restTemplate.postForEntity(authenticationApiBaseUrl + ACTIVE_SESSION_URI, auth, String.class).getBody();
            LoginResponseDTO loginResponse = null;
            try {
                loginResponse = objectMapper.readValue(loginResponseDTO, LoginResponseDTO.class);
            } catch (Exception ignored) {
            }
            if (loginResponse != null && loginResponse.getLoginSession() != 0) {
                userAuth = new UserAuth(auth.getUsername(), null, List.of());
                return userAuth;
            }
        } catch (Exception e) {
            LOGGER.info("Active session auth failed", e);
        }
        return userAuth;
    }

    private LoginDTO decodeAuthorizationHeader(String auth) {
        LoginDTO loginDTO = new LoginDTO();
        if (auth == null) {
            return loginDTO;
        }
        auth = auth.replaceAll("Basic ", "");
        String[] parts = new String(Base64.getDecoder().decode(auth.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8).split(":");
        if (parts.length >= 1) {
            loginDTO.setUsername(parts[0]);
        }
        if (parts.length >= 2) {
            loginDTO.setPassword(parts[1]);
        }
        return loginDTO;
    }
}
