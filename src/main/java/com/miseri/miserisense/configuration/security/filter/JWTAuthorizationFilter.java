package com.miseri.miserisense.configuration.security.filter;

import com.miseri.miserisense.utilities.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null) {
            String token = bearerToken.replace("Bearer ", "");
            UsernamePasswordAuthenticationToken userNamePAT = TokenUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(userNamePAT);
        }

        filterChain.doFilter(request, response);

    }
}
