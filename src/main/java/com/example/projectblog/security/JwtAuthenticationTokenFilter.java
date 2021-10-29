package com.example.projectblog.security;

import com.example.projectblog.service.account.IAccountService;
import com.example.projectblog.service.jwt.JwtService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

  private final static String TOKEN_HEADER = "authorization";
  @Autowired
  private JwtService jwtService;
  @Autowired
  private IAccountService accountService;

  @Autowired
  @Override
  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String authToken = httpRequest.getHeader(TOKEN_HEADER);
    if (jwtService.validateTokenLogin(authToken)) {
      String email = jwtService.getEmailFromToken(authToken);
      com.example.projectblog.model.Account account = accountService.loadUserByEmail(email);
      if (email != null) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        UserDetails userDetail = new User(email, account.getPassword(), enabled, accountNonExpired,
            credentialsNonExpired, accountNonLocked, account.getAuthorities());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
            null, userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    chain.doFilter(request, response);
  }
}
