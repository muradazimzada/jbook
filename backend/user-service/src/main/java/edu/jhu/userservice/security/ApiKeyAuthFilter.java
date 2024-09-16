package edu.jhu.userservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Collections;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    @Value("${book-service.api.key}")
    private String apiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Apply filter only for specific endpoints
        if (
                request.getRequestURI().endsWith("bookservice")) {


            // Retrieve API key from request header
            String apiKeyFromRequest = request.getHeader("X-API-KEY");

            // Validate API key
            if (apiKeyFromRequest != null && apiKeyFromRequest.equals(apiKey)) {
                // Set authentication in security context
                Authentication auth = new UsernamePasswordAuthenticationToken("api-user", null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                // If API key is invalid, return 403 and stop processing
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Invalid API Key");
                return;
            }
        }
        // Proceed with the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
