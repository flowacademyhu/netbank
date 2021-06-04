package hu.flowacademy.netbank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final int JWT_EXPR_DELAY = 1000 * 60 * 30; // 30 minutes
    public static final String JWT_TOKEN_HEADER = "jwt-token";
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String ADDITIONAL_CLAIM_ROLES = "roles";
    private final String hmacKey;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                String hmacKey) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/login");
        this.hmacKey = hmacKey;
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);
        log.info("Login started with username: {}", loginRequest.getUsername());

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword(),
                        List.of()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        UserDetails principal = (UserDetails) authResult.getPrincipal();
        log.info("Successfully login with {}", principal.getUsername());

        String jwtToken = Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(hmacKey.getBytes()), SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPR_DELAY))
                .claim(ADDITIONAL_CLAIM_ROLES, principal.getAuthorities())
                .compact();

        response.setHeader(JWT_TOKEN_HEADER, jwtToken);
        response.setCharacterEncoding(DEFAULT_CHARSET);
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);

        response.getWriter().print(
                new ObjectMapper()
                        .writeValueAsString(
                                new LoginResponse(jwtToken)
                        )
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private String token;
    }
}
