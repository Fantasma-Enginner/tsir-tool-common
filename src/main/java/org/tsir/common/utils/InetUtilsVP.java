package org.tsir.common.utils;

import java.lang.management.ManagementFactory;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class InetUtilsVP {

	private InetUtilsVP() {
	}

	public static int getJBossStandardUnsecurePort() throws InstanceNotFoundException, AttributeNotFoundException,
			MalformedObjectNameException, ReflectionException, MBeanException {
		Object objPort = ManagementFactory.getPlatformMBeanServer().getAttribute(
				new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http"), "port");
		return Integer.parseInt(objPort.toString());
	}

	public static int getJBossStandardUnsecurePortOffset() throws InstanceNotFoundException, AttributeNotFoundException,
			MalformedObjectNameException, ReflectionException, MBeanException {
		return getJBossStandardUnsecurePort() + getJBossStandardOffsetPorts();
	}

	public static int getJBossStandardSecurePort() throws InstanceNotFoundException, AttributeNotFoundException,
			MalformedObjectNameException, ReflectionException, MBeanException {
		Object objPort = ManagementFactory.getPlatformMBeanServer().getAttribute(
				new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=https"), "port");
		return Integer.parseInt(objPort.toString());
	}

	public static int getJBossStandardSecurePortOffset() throws InstanceNotFoundException, AttributeNotFoundException,
			MalformedObjectNameException, ReflectionException, MBeanException {
		return getJBossStandardSecurePort() + getJBossStandardOffsetPorts();
	}

	public static int getJBossStandardOffsetPorts() throws InstanceNotFoundException, AttributeNotFoundException,
			MalformedObjectNameException, ReflectionException, MBeanException {
		Object objOffset = ManagementFactory.getPlatformMBeanServer()
				.getAttribute(new ObjectName("jboss.as:socket-binding-group=standard-sockets"), "port-offset");
		return Integer.valueOf(objOffset.toString());
	}

}
