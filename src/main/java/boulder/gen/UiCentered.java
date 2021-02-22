package boulder.gen;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.simsilica.lemur.Panel;

final class UiCentered {

	private final Panel subject;
	private final Camera camera;

	public UiCentered(Panel subject, Camera camera) {
		this.subject = subject;
		this.camera = camera;
	}

	public void center() {
		Vector3f prefSize = subject.getPreferredSize();

		float left = camera.getWidth() * 0.5f - prefSize.x * 0.5f;
		float top = camera.getHeight() * 0.5f + prefSize.y * 0.5f;

		subject.setLocalTranslation(left, top, 0);

	}

}
