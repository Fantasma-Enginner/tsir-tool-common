/**
 * 
 */
package org.tsir.common.utils;

import java.net.URI;

import javax.net.ssl.SSLException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.tsir.common.api.exceptions.DataTypeCriteriaException;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

/**
 * @author cristhian.murillo
 *
 */
public class ObjectUtilsVP {

	private ObjectUtilsVP() {
	}

	public static <T extends Number> T getNumber(String value, Class<T> t) {
		if (t != null && value != null) {
			value = value.trim();
			try {
				if (t.isAssignableFrom(Integer.class)) {
					return t.cast(Integer.parseInt(value));
				}
				if (t.isAssignableFrom(Long.class)) {
					return t.cast(Long.parseLong(value));
				}
				if (t.isAssignableFrom(Short.class)) {
					return t.cast(Short.parseShort(value));
				}
				if (t.isAssignableFrom(Double.class)) {
					return t.cast(Double.parseDouble(value));
				}
				if (t.isAssignableFrom(Float.class)) {
					return t.cast(Float.parseFloat(value));
				}

			} catch (NumberFormatException e) {
				throw new TypeMismatchException(value, t);
			}
		}
		throw new TypeMismatchException(value, t);
	}

	public static <T extends Number> T getNumberCriteria(String resourceName, String value, String criteria,
			Class<T> t) {
		try {
			return getNumber(value, t);
		} catch (TypeMismatchException e) {
			throw new DataTypeCriteriaException(resourceName, criteria, "Numérico");
		}
	}

	public static URI getURLBase(UriComponents components) {
		if (components.getPathSegments().size() > 1) {
			UriComponentsBuilder aux = UriComponentsBuilder.newInstance();
			aux.scheme(components.getScheme());
			aux.host(components.getHost());
			aux.port(components.getPort());
			aux.pathSegment(components.getPathSegments().get(0));
			return aux.build().toUri();
		} else {
			return components.toUri();
		}
	}

	public static URI getURLBase(String httpUrl) {
		return getURLBase(UriComponentsBuilder.fromHttpUrl(httpUrl).build());
	}

	public static URI getURLBase(HttpRequest request) {
		return getURLBase(UriComponentsBuilder.fromHttpRequest(request).build());
	}

	public static WebClient.Builder getClient() {
		WebClient.Builder client;
		try {
			SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)
					.build();
			HttpClient httpClient = HttpClient.create()
					.secure(sslProviderBuilder -> sslProviderBuilder.sslContext(sslContext));
			client = WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
		} catch (SSLException ex) {
			client = WebClient.builder();
		}
		return client;
	}

}
