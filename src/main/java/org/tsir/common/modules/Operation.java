package org.tsir.common.modules;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.tsir.common.modules.exceptions.MalformedResourceException;

public class Operation {

	private final Module module;
	private final String code;
	private final String name;
	private final String authority;

	private Operation(Module module, String code, String name, String authority) {
		this.module = module;
		this.code = code;
		this.name = name;
		this.authority = authority;
	}

	public Module getModule() {
		return module;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getAuthority() {
		return authority;
	}

	public long getIdentifier() {
		String formatAuthority = String.format("%2s%4s%8s", module.getDomain().getCode(), module.getCode(), getCode());
		formatAuthority = formatAuthority.replace(' ', '0');
		return Long.parseLong(formatAuthority, 16);
	}

	public static Operation getFromAuthority(String name, String authority) {
		Objects.requireNonNull(authority, "El código de autoridad no puede ser nulo");
		String[] array = authority.split(ResourceConstants.SEPARATOR);
		if (array.length != 3) {
			throw new MalformedResourceException(name);
		}
		Domain domain = Domain.getByCode(array[0]);
		Module module = Module.getByCode(domain, array[1]);
		String codeResource = array[2];
		return new Operation(module, codeResource, name, authority);
	}

	public static Long[] getIdentifiers(long resourceId) {
		String strId = Long.toHexString(resourceId);
		strId = StringUtils.leftPad(strId, 14, '0');
		return new Long[] { Long.parseLong(strId.substring(0, 2), 16),
				Long.parseLong(strId.substring(2, 6), 16), Long.parseLong(strId.substring(6), 16) };
	}

	@Override
	public String toString() {
		return "Operation [module=" + module + ", code=" + code + ", name=" + name + ", authority=" + authority
				+ ", getIdentifier()=" + getIdentifier() + "]";
	}

}
