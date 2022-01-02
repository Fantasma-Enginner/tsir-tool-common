package org.tsir.common.services;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;
import org.tsir.common.modules.Module;
import org.tsir.common.services.resources.dto.ResourceDTO;
import org.tsir.common.utils.ObjectUtilsVP;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResourcesService extends AbstractService {

	@Autowired
	private ObjectMapper mapper;

	private static final ParameterizedTypeReference<List<ResourceDTO>> TYPE_LIST_RESOURCEDTO = new ParameterizedTypeReference<List<ResourceDTO>>() {
	};

	public ResourcesService() {
		setSecure(false);
		setApiPath("api/v1/");
	}

	public void setModuleInformation(Module module) {
		URI uriRequest = getUriComponents("/resources/".concat(String.valueOf(module.getIdentifier()))).build().toUri();
		ResourceDTO dto = getClient().baseUrl(uriRequest.toString()).build().get().headers(this::addHeaders)
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(ResourceDTO.class)
				.block(Duration.ofSeconds(5));
		URI uriLocation = ObjectUtilsVP.getURLBase(dto.getLocation());
		module.setLocation(uriLocation.toString());
	}

	public List<ResourceDTO> findResources(Map<String, String> filter) throws JsonProcessingException {
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(getUriComponents(""));
		factory.setEncodingMode(EncodingMode.TEMPLATE_AND_VALUES);
		return getClient().uriBuilderFactory(factory).build().get()
				.uri(b -> b.path("/resources").queryParam("filter", "{f}").build(getValue(filter)))
				.headers(this::addHeaders).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(TYPE_LIST_RESOURCEDTO).block(Duration.ofSeconds(3));
	}

	private void addHeaders(HttpHeaders httpHeaders) {
		if (ObjectUtils.isNotEmpty(headers)) {
			getHeaders().forEach(httpHeaders::add);
			headers.clear();
		}

	}

	private String getValue(Map<String, String> filter) {
		try {
			return mapper.writeValueAsString(filter);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

}
