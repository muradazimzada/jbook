package edu.jhu.bookservice.security;

import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Bypass pre-flight requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        // Process only requests to the book-related endpoints
        if (!request.getRequestURI().contains("book")) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println(authHeader);

        // Check for Authorization header and if it's a Bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the header
        String token = authHeader.substring(7);

        try {
            // Call userDetailsService to validate the token and get the user details
            UserDetails user = userDetailsService.loadUserByUsername(token);

            if (user != null) {
                // Create and set the authentication token in the SecurityContext
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                // If user is null, throw unauthorized exception
                response.setStatus(401);
                response.getWriter().write("User not found");
                response.getWriter().flush();
                return;
            }

        } catch (FeignException.Unauthorized e) {
            // Handle Feign Unauthorized Exception (401)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + e.getMessage());
            response.getWriter().flush();
            return; // Stop further filter processing
        } catch (Exception e) {
            // Handle other exceptions
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
            response.getWriter().flush();
            return; // Stop further filter processing
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
