package org.tsir.common.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

public abstract class AbstractService {

	protected Logger logger = LoggerFactory.getLogger(AbstractService.class);

	private String customURL = null;

	private boolean secure;

	private String host;

	private Integer port;

	private String contextPath;

	private String apiPath;

	protected Map<String, String> headers;

	@Autowired
	private WebClient.Builder client;

	public String getCustomURI() {
		return customURL;
	}

	public void setCustomURI(String customURI) {
		this.customURL = customURI;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getApiPath() {
		return apiPath;
	}

	protected void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}

	protected Map<String, String> getHeaders() {
		headers = ObjectUtils.getIfNull(headers, HashMap::new);
		return headers;
	}

	public void addHeader(String key, String value) {
		getHeaders().put(key, value);
	}

	public void setClient(WebClient.Builder client) {
		this.client = client;
	}
	
	public WebClient.Builder getClient() {
		return client;
	}

	private String getProtocol() {
		return secure ? "https://" : "http://";
	}

	private String getBasePath() {
		StringBuilder builder = new StringBuilder();
		builder.append(getProtocol());
		builder.append(getHost());
		if (Objects.nonNull(port)) {
			builder.append(":").append(port);
		}
		builder.append("/");
		return builder.toString();
	}

	protected UriComponentsBuilder getUriComponents(String resource) {
		UriComponentsBuilder uriBuilder;
		if (ObjectUtils.isNotEmpty(getCustomURI())) {
			uriBuilder = UriComponentsBuilder.fromHttpUrl(customURL).path(resource);
		} else {
			Objects.requireNonNull(host, "Se requiere el host del servicio");
			uriBuilder = UriComponentsBuilder.fromHttpUrl(getBasePath());
			if (ObjectUtils.isNotEmpty(getContextPath())) {
				uriBuilder.path(getContextPath());
			}
			if (ObjectUtils.isNotEmpty(getApiPath())) {
				uriBuilder.path(getApiPath());
			}
		}
		if (ObjectUtils.isNotEmpty(resource)) {
			uriBuilder.path(resource);
		}
		return uriBuilder;
	}

}
