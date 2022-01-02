package org.tsir.common.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.tsir.common.modules.Domain;
import org.tsir.common.modules.Module;
import org.tsir.common.modules.Operation;
import org.tsir.common.modules.Registrable;
import org.tsir.common.services.resources.dto.ResourceDTO;
import org.tsir.common.services.resources.dto.ResourceType;
import org.tsir.common.services.resources.dto.StateActive;

public class RegisterService extends AbstractService {

	public static final String HOME_CONTEXT = "vial-plus";

	@Value("${vialplus.platform.home.host:}")
	private String homeHost;

	private String location;

	public RegisterService() {
		setApiPath("api/v1/");
	}

	public String getLocation() {
		return location;
	}

	/**
	 * Register the module resources (operations that publish) and the path front
	 * location if apply.
	 * 
	 * @param module    The Registrable object that contains all the operations
	 *                  available on the module.
	 * @param pathFront The relative path where the front should be located.
	 * @return
	 */
	public List<String> registerModule(Registrable module, String pathFront) {
		return this.registerModule(module, pathFront, this.homeHost);
	}
	
	/**
	 * Register the module resources (operations that publish) and the path front
	 * location if apply.
	 * 
	 * @param module    The Registrable object that contains all the operations
	 *                  available on the module.
	 * @param pathFront The relative path where the front should be located.
	 * @return
	 */
	public List<String> registerModule(Registrable module, String pathFront, String host) {
		this.location = ObjectUtils.isEmpty(pathFront) ? null : buildLocation(host, pathFront);
		List<String> results = new ArrayList<>();
		getResources(module).forEach(r -> {
			logger.info("Registering {}", r.getLabel());
			results.add(r.getType() + " " + r.getLabel() + " : " + (register(r) ? "OK" : "Falló"));
		});
		return results;
	}

	private boolean register(ResourceDTO r) {
		try {
			String uri = getUriComponents("register").toUriString();
			logger.info("Register {} with name {} on service {}", r.getType(), r.getLabel(), uri);
			getClient().baseUrl(uri).build().post().contentType(MediaType.APPLICATION_JSON).bodyValue(r).retrieve()
					.bodyToMono(Void.class).block();
			return true;
		} catch (Exception e) {
			logger.warn("Creation resource warning {}", e.getMessage());
			return false;
		}
	}

	private Collection<ResourceDTO> getResources(Registrable module) {
		try {
			HashMap<Long, ResourceDTO> resources = new HashMap<>();
			module.getAuthorities().forEach(o -> {
				Domain domain = o.getModule().getDomain();
				if (!resources.containsKey(domain.getIdentifier())) {
					resources.put(domain.getIdentifier(), buildResourceDTO(domain));
				}
				Module mod = o.getModule();
				if (!resources.containsKey(mod.getIdentifier())) {
					mod.setLocation(getLocation());
					resources.put(mod.getIdentifier(), buildResourceDTO(mod));
				}
				resources.put(o.getIdentifier(), buildResourceDTO(o));
			});
			List<ResourceDTO> list = new ArrayList<>(resources.values());
			Collections.sort(list, (a, b) -> a.getId().compareTo(b.getId()));
			return list;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			logger.error("Extraction resources warning from module");
		}
		return Collections.emptyList();
	}

	private ResourceDTO buildResourceDTO(Operation o) {
		ResourceDTO resource = new ResourceDTO();
		resource.setId(o.getIdentifier());
		resource.setLabel(o.getName());
		resource.setCode(Integer.parseInt(o.getCode(), 16));
		resource.setActive(StateActive.ACTIVO);
		resource.setType(ResourceType.OPERACION);
		resource.setParent(o.getModule().getIdentifier());
		return resource;
	}

	private ResourceDTO buildResourceDTO(Domain o) {
		ResourceDTO resource = new ResourceDTO();
		resource.setId(o.getIdentifier());
		resource.setLabel(o.getName());
		resource.setCode(Integer.parseInt(o.getCode(), 16));
		resource.setActive(StateActive.ACTIVO);
		resource.setType(ResourceType.DOMINIO);
		resource.setIcon(o.getIcon());
		return resource;
	}

	private ResourceDTO buildResourceDTO(Module o) {
		ResourceDTO resource = new ResourceDTO();
		resource.setId(o.getIdentifier());
		resource.setLabel(o.getName());
		resource.setCode(Integer.parseInt(o.getCode(), 16));
		resource.setActive(StateActive.ACTIVO);
		resource.setType(ResourceType.MODULO);
		resource.setPath(o.getUrl());
		resource.setLocation(o.getLocation());
		resource.setIcon(o.getIcon());
		resource.setParent(o.getDomain().getIdentifier());
		return resource;
	}

	private static String buildLocation(String host, String path) {
		Objects.requireNonNull(host, "La URL de localización de Host es requerido");
		Objects.requireNonNull(path, "El path de localización es requerido");
		StringBuilder builder = new StringBuilder();
		builder.append(host);
		if (!host.endsWith("/")) {
			builder.append('/');
		}
		builder.append(HOME_CONTEXT);
		if (!path.startsWith("/")) {
			builder.append('/');
		}
		builder.append(path);
		return builder.toString();
	}

}
