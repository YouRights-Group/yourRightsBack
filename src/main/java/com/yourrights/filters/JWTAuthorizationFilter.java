package com.yourrights.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yourrights.repository.beans.UserEntity;
import com.yourrights.services.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTAuthorizationFilter implements Filter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "mySecretKey";

    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
//	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	try {
	    if (existsJWTToken((HttpServletRequest) request, (HttpServletResponse) response)) {
		Claims claims = validateToken((HttpServletRequest) request);
		if (claims.get("authorities") != null) {
		    setUpSpringAuthentication(claims, (HttpServletRequest) request);
		} else {
		    SecurityContextHolder.clearContext();
		}
	    }
	    chain.doFilter(request, response);
	} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
	    ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
	    return;
	}
    }

//    @Override
//    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//	    throws ServletException, IOException {
//	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
//	
//    }

    private Claims validateToken(HttpServletRequest request) {
	String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
	return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    /**
     * Metodo para autenticarnos dentro del flujo de Spring
     * 
     * @param claims
     * @param request
     */
    private void setUpSpringAuthentication(Claims claims, HttpServletRequest request) {
	@SuppressWarnings("unchecked")
	List<String> authorities = (List<String>) claims.get("authorities");

	UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
		authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

	String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
	UserEntity userEntity = userService.getUser(jwtToken);
	Map<String, Object> info = new HashMap<String, Object>();
	info.put("user", userEntity);
	((AbstractAuthenticationToken) auth).setDetails(info);
	SecurityContextHolder.getContext().setAuthentication(auth);
	SecurityContextHolder.getContext().setAuthentication(auth);

    }

    private boolean existsJWTToken(HttpServletRequest request, HttpServletResponse res) {
	String authenticationHeader = request.getHeader(HEADER);
	if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
	    return false;

	return true;
    }

}