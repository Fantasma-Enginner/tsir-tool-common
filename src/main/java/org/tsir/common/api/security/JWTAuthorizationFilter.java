package org.tsir.common.api.security;

import static org.tsir.common.api.security.Constants.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.tsir.common.modules.ResourceConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Strings;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(HEADER_AUTHORIZACION_KEY);
		if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		if (token != null) {
			// Se procesa el token y se recupera el usuario.
			Claims claims = Jwts.parser().setSigningKey(SUPER_SECRET_KEY)
					.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, "")).getBody();
			String sub = claims.getSubject();
			String rol = claims.get("aut", String.class);
			if (sub != null) {
				return new UsernamePasswordAuthenticationToken(sub, null, loadAuthorities(rol));
			}
			return null;
		}
		return null;
	}

	private Set<GrantedAuthority> loadAuthorities(String rol) {
		if (rol != null) {
			return Strings.commaDelimitedListToSet(rol).stream().flatMap(this::unpack).map(SimpleGrantedAuthority::new)
					.collect(Collectors.toSet());
		} else {
			return Collections.emptySet();
		}
	}

	private Stream<String> unpack(String pack) {
		if (pack.startsWith("ROLE")) {
			return Stream.of(pack);
		} else {
			String domain = pack.substring(0, pack.indexOf(':'));
			String[] modules = pack.substring(pack.indexOf(':') + 1).split(";");
			return Arrays.stream(modules).flatMap(s -> getAuthorityCode(String.format("%s", domain),
					String.format("%s", s.substring(0, s.indexOf('-'))), s.substring(s.indexOf('-') + 1)));
		}
	}

	private Stream<String> getAuthorityCode(String domain, String module, String packActions) {
		String binaryModules = StringUtils.reverse(Integer.toBinaryString(Integer.valueOf(packActions, 16)));
		return IntStream.range(0, binaryModules.length()).mapToObj(i -> {
			if (binaryModules.charAt(i) == '1') {
				return formatAuthorityCode(domain, module, (int) Math.pow(2, i));
			} else {
				return "";
			}
		}).filter(s -> !s.isEmpty());
	}

	private String formatAuthorityCode(String domain, String module, int action) {
		StringBuilder builder = new StringBuilder();
		builder.append(domain).append(ResourceConstants.SEPARATOR).append(module).append(ResourceConstants.SEPARATOR)
				.append(Integer.toHexString(action).toUpperCase());
		return builder.toString();
	}

}
