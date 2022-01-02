package org.tsir.common.services;

import org.springframework.http.MediaType;
import org.tsir.common.services.communications.dto.TaskDTO;

public class CommunicationsService extends AbstractService {

	public CommunicationsService() {
		setApiPath("api/v1/");
	}

	public long createTask(String name, long registerPK) {
		return createTask(name, String.valueOf(registerPK));
	}

	public long createTask(String name, String pkRegister) {
		try {
			TaskDTO body = new TaskDTO().name(name).registerPK(pkRegister);
			Long l = getClient().baseUrl(getUriComponents("task").build().toUriString()).build().post()
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(body)
					.retrieve().bodyToMono(Long.class).block();
			logger.info("Tarea puesta correctamente [{} {}] con identificador {}", name, pkRegister, l);
			return l;
		} catch (Exception e) {
			logger.warn("Ha fallado la puesta de la tarea de comunicaciones {} = {} ", name, pkRegister, e);
		}
		return -1;
	}
}
