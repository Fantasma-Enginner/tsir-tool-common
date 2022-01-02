package org.tsir.common.modules;

import static org.tsir.common.modules.ResourceConstants.*;

public enum Domain {

	PLATAFORM(PLATAFORM_DOMAIN, "Plataforma", "fas fa-window-restore"),

	SETTINGS(SETTINGS_DOMAIN, "Parametrización", "fas fa-cogs"),

	MANAGEMENT(MANAGEMENT_DOMAIN, "Administración", "fas fa-th-list"),

	OPERATION(OPERATION_DOMAIN, "Operación", "fas fa-users"),

	TRANSACTION(TRANSACTION_DOMAIN, "Transacciones", "fas fa-car"),

	CONCILIATION(CONCILIATION_DOMAIN, "Conciliación", "fas fa-compress-arrows-alt"),
	
	MONEY(MONEY_DOMAIN, "Dineros", "fas fa-file-invoice-dollar"),

	COMMUNICATION(COMMUNICATION_DOMAIN, "Comunicaciones", "fas fa-exchange-alt"),
	
	STATISTICALS(STATISTICS_DOMAIN, "Estadísticos", "fas fa-chart-bar"),
	
	IPREV(IPREV_DOMAIN, "REV", "fas fa-rss");

	private final String code;
	private final String name;
	private String icon;

	Domain(String code, String name, String icon) {
		this.code = code;
		this.name = name;
		this.icon = icon;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getIcon() {
		return icon;
	}

	protected void setIcon(String icon) {
		this.icon = icon;
	}

	public long getIdentifier() {
		return Long.parseLong(getCode(), 16);
	}

	public static Domain getByCode(String code) {
		for (Domain domain : values()) {
			if (domain.code.equals(code)) {
				return domain;
			}
		}
		return null;
	}

}
