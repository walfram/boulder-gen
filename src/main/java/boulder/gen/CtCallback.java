package boulder.gen;

import com.simsilica.lemur.core.VersionedReference;

import jme3utilities.SimpleControl;

final class CtCallback extends SimpleControl {

	private final VersionedReference<?> ref;
	private final Runnable callback;

	public CtCallback(VersionedReference<?> ref, Runnable callback) {
		this.ref = ref;
		this.callback = callback;
	}

	@Override
	protected void controlUpdate(float updateInterval) {
		super.controlUpdate(updateInterval);

		if (ref.update())
			callback.run();
	}

}
