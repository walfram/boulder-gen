package boulder.gen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;

final class StSurface extends BaseAppState {

	private static final Logger logger = LoggerFactory.getLogger(StSurface.class);
	
	private final Node scene = new Node("scene");

	public StSurface(Node rootNode) {
		rootNode.attachChild(scene);
	}

	@Override
	protected void initialize(Application app) {
	}
	
	protected void refreshSurface() {
		logger.debug("refresh called");
	}

	@Override
	protected void cleanup(Application app) {
	}

	@Override
	protected void onEnable() {
	}

	@Override
	protected void onDisable() {
	}

	public void useWireframe(boolean wireframe) {

	}

}
