package com.amalitech.org.apigateway.filter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.amalitech.org.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                // header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    jwtUtil.validateToken(authHeader);

                    String userName = jwtUtil.extractUsername(authHeader);
                    // Add user details to the request headers
                    // exchange.getRequest().mutate()
                    //        .header("X-UserId", userName)
                    //        .header("X-UserRole", roles.toString())
                    //        .build();

                } catch (ExpiredJwtException ex) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired", ex);
                } catch (UnsupportedJwtException | MalformedJwtException | SignatureException ex) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token", ex);
                } catch (UsernameNotFoundException ex) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username not found", ex);
                } catch (BadCredentialsException ex) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password", ex);
                } catch (JwtException ex) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT authentication failed", ex);
                } catch (Exception ex) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access to application", ex);
                }
            }

            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
