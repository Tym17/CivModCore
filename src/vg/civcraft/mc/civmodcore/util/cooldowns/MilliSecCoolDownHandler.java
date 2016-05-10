package vg.civcraft.mc.civmodcore.util.cooldowns;

import java.util.HashMap;
import java.util.Map;

/**
 * Cooldown implementation that keeps track of objects in milliseconds. The
 * value given in the constructor is assumed to be in milliseconds and time
 * stamps are stored as unix timestamp
 * 
 * @author Maxopoly
 *
 * @param <E>
 *            Object that cooldowns are assigned to
 */
public class MilliSecCoolDownHandler<E> implements ICoolDownHandler<E> {
	private Map<E, Long> cds;
	private long cooldown;

	public MilliSecCoolDownHandler(long cooldown) {
		this.cooldown = cooldown;
		cds = new HashMap<E, Long>();
	}

	public void putOnCoolDown(E e) {
		cds.put(e, System.currentTimeMillis());
	}

	public boolean onCoolDown(E e) {
		Long lastUsed = cds.get(e);
		if (lastUsed == null
				|| (System.currentTimeMillis() - lastUsed) > cooldown) {
			return false;
		}
		return true;
	}

	public long getRemainingCoolDown(E e) {
		Long lastUsed = cds.get(e);
		if (lastUsed == null) {
			return 0L;
		}
		long leftOver = System.currentTimeMillis() - lastUsed;
		if (leftOver < cooldown) {
			return cooldown - leftOver;
		}
		return 0;
	}

	public long getTotalCoolDown() {
		return cooldown;
	}
}
