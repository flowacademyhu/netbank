package hu.flowacademy.netbank.config;

import hu.flowacademy.netbank.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Transactional
public class AuthorizationFilter extends BasicAuthenticationFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final UserRepository userRepository;
    private final String hmacKey;

    public AuthorizationFilter(AuthenticationManager authenticationManager,
                               UserRepository userRepository,
                               String hmacKey) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.hmacKey = hmacKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String authValue = request.getHeader(AUTHORIZATION);
        if (StringUtils.isEmpty(authValue)) {
            log.info("No Authorization header for {} path", request.getRequestURI());

            chain.doFilter(request, response);
            return;
        }

        authValue = authValue.replaceAll(BEARER_PREFIX, StringUtils.EMPTY);

        if (StringUtils.isEmpty(authValue)) {
            log.info("Missing Bearer token for path {}", request.getRequestURI());

            chain.doFilter(request, response);
            return;
        }

        Claims body = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(hmacKey.getBytes()))
                .build()
                .parseClaimsJws(authValue)
                .getBody();


        log.info("Jwt's content is: {}", body);
        String username = body.getSubject();

        userRepository.findFirstByEmail(username)
                .ifPresent(user ->
                        SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
                ));

        chain.doFilter(request, response);
    }
}
