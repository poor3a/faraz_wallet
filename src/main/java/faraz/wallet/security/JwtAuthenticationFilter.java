package faraz.wallet.security;

import faraz.wallet.entity.Token;
import faraz.wallet.repository.TokenRepository;
import faraz.wallet.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, TokenRepository tokenRepository, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();
        return path.equals("/swagger-ui")
                || path.startsWith("/swagger-ui/")
                || path.startsWith("/swagger-ui.html")
                || path.startsWith("/v2/api-docs/**")
                || path.startsWith("/v3/api-docs/**")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/webjars")
                || path.startsWith("/auth")
                || path.startsWith("/h2-console");
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        Long userId;
        try {
            userId = jwtTokenProvider.getUserId(jwt);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        Token storedToken = tokenRepository.findByTokenAndRevokedFalseAndExpiredFalse(jwt).orElse(null);
        if (storedToken == null || storedToken.isRevoked() || storedToken.isExpired()) {
            filterChain.doFilter(request, response);
            return;
        }

        var user = userRepository.findById(userId).orElse(null);
        if (user == null || !user.isEnabled()) {
            filterChain.doFilter(request, response);
            return;
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
