package org.tsir.common.modules;

import static org.tsir.common.modules.Domain.*;
import static org.tsir.common.modules.ResourceConstants.*;

public enum Module {

	/*
	 * PLATFORM DOMAIN MODULES
	 */
	MODULES(PLATAFORM, RESOURCES_MODULE, "Recursos", "/resources-app/modules", "fas fa-object-group"),
	
	HOME(PLATAFORM, HOME_MODULE, "Home", null, "fas fa-home"),


	/*
	 * SETTINGS DOMAIN MODULES
	 */
	USERS(SETTINGS, USERS_MODULE, "Usuarios", "/users-app/users", "fas fa-user-edit"),

	PROFILES(SETTINGS, PROFILES_MODULE, "Perfiles", "/users-app/profiles", "fas fa-users"),

	TOLLS(SETTINGS, TOLLS_MODULE, "Estaciones", "/tolls-app/tolls", "fas fa-landmark"),


	/*
	 * MANAGEMENT DOMAIN MODULES
	 */
	PARAMETERS(MANAGEMENT, PARAMETERS_MODULE, "Parámetros", "/parameters-app", "fas fa-hand-holding-usd"),

	WHITELIST(MANAGEMENT, WHITELIST_MODULE, "Listas Blancas", "/whitelist-app", "fa fa-list-alt"),
	

	/*
	 * OPERATION DOMAIN MODULES
	 */
	ASSITANCE(OPERATION, ASSISTANCE_MODULE, "Asistencia", "/assistance-app", "fas fa-book"),


	/*
	 * TRANSACTIONS DOMAIN MODULES
	 */
	EVIDENCE(TRANSACTION, EVIDENCE_MODULE, "Evidencias de transacción", "/evidence-app", "fas fa-photo-video"),


	/*
	 * COMMUNICATIONS DOMAIN MODULES
	 */
	INTEGRATION(COMMUNICATION, INTEGRATION_MODULE, "Integración", null, "fas fa-bezier-curve"),
	
	NODES(COMMUNICATION, NODES_MODULE, "Nodos", "/vps-nodes-app/nodes", "fab fa-codepen"),


	/*
	 * CONCILIATION DOMAIN MODULES
	 */
	RECONCILE(CONCILIATION, RECONCILE_MODULE, "Conciliación", "/conciliation-app", "fas fa-handshake"),
	RULES(CONCILIATION, RULES_MODULE, "Reglas de conciliación", "/ruleconciliation-app", "fas fa-calculator"),
	MASIVE(CONCILIATION, CONCILIACION_MASIVE_MODULE, "Conciliación Masiva", "/masive-app", "far fa-handshake");
	
	
	private final String code;
	private final Domain domain;
	private final String name;
	private String url;
	private String location;
	private String icon;

	Module(Domain domain, String code, String name, String url, String icon) {
		this.domain = domain;
		this.code = code;
		this.name = name;
		this.url = url;
		this.icon = icon;
	}

	public Domain getDomain() {
		return domain;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getIcon() {
		return icon;
	}

	protected void setIcon(String icon) {
		this.icon = icon;
	}
	
	public void unplubishFront() {
		this.url = "";
	}

	public long getIdentifier() {
		String formattedCode = String.format("%2s%4s", domain.getCode(), getCode());
		formattedCode = formattedCode.replace(' ', '0');
		return Long.parseLong(formattedCode, 16);
	}

	public static Module getByCode(Domain domain, String code) {
		for (Module module : values()) {
			if (module.domain.equals(domain) && module.code.equals(code)) {
				return module;
			}
		}
		return null;
	}

}
