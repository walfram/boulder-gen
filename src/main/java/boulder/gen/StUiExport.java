package boulder.gen;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;

final class StUiExport extends BaseAppState {

	private final Node scene = new Node("scene");

	public StUiExport(Node guiNode) {
		guiNode.attachChild(scene);
	}

	@Override
	protected void initialize(Application app) {
		Container container = new Container();

		container.addChild(new Label("export as"), 0);
		
		container.addChild(new ActionButton(new AcCall("json", () -> getState(StIo.class).exportJson())), 1).setMaxWidth(100f);
		container.addChild(new ActionButton(new AcCall("j3o", () -> getState(StIo.class).exportJ3o())), 2).setMaxWidth(100f);

		scene.attachChild(container);

		new UiCenteredTop(container, app.getCamera()).center();
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

}
