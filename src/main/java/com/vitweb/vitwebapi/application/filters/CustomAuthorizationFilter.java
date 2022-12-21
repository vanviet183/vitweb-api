package com.vitweb.vitwebapi.application.filters;

import com.vitweb.vitwebapi.application.services.MyUserDetailsService;
import com.vitweb.vitwebapi.application.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final MyUserDetailsService userDetailsService;

  public CustomAuthorizationFilter(JwtUtil jwtUtil, MyUserDetailsService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    // kiem tra xem co phai thong tin dang nhap khong
    if(request.getServletPath().equals("/api/v1/auth/login") || request.getServletPath().equals("/api/v1/auth/refresh-token")) {
      filterChain.doFilter(request, response);
    } else {
      try {
        String token = getTokenFromRequest(request);
        if (token != null && jwtUtil.validateJwtToken(token)) {
          String uuid = jwtUtil.getUUIDFromJwtToken(token);
          UserDetails userDetails = userDetailsService.loadUserByUsername(uuid);
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities()
          );
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
      } catch (Exception ex) {
        response.setHeader("error", ex.getMessage());
        response.sendError(HttpStatus.FORBIDDEN.value());
      }
    }
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");

    if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }
    return null;
  }
}
