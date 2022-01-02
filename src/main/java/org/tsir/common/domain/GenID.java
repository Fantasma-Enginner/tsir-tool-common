package org.tsir.common.domain;

import java.util.HashMap;

public class GenID {

	private static final short MIN_DISC = 0;
	private static final short MAX_DISC = 9;
	private static HashMap<Class<?>, GenID> map;

	private final long tollBase;
	private final long laneBase;
	private long lastTime;
	private short discriminator;

	private Object lock = new Object();

	public GenID() {
		tollBase =  10_000_000_000_000_000L;
		laneBase =  100_000_000_000_000L;
		lastTime = 0;
		discriminator = MIN_DISC;
	}

	private long nextID() {
		synchronized (lock) {
			long now = System.currentTimeMillis() * 10;
			if (lastTime == now) {
				setDiscriminator(true);
				now = System.currentTimeMillis() * 10;
			} else {
				setDiscriminator(false);
			}
			lastTime = now;
			return tollBase + laneBase + lastTime + discriminator;
		}
	}

	private void setDiscriminator(boolean wait) {
		discriminator++;
		if (discriminator > MAX_DISC) {
			if (wait) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			discriminator = 0;
		}
	}

	public static long generateID(Class<?> src) {
		if (map == null) {
			map = new HashMap<>();
		}
		GenID gen = map.get(src);
		if (gen == null) {
			gen = new GenID();
			map.put(src, gen);
		}
		return gen.nextID();
	}

}
