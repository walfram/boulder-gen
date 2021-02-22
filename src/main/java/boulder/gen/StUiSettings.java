package boulder.gen;

import java.util.Arrays;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.simsilica.lemur.Action;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.ListBox;
import com.simsilica.lemur.RollupPanel;
import com.simsilica.lemur.style.BaseStyles;

import jme3.common.noise.FastNoiseLite.NoiseType;

final class StUiSettings extends BaseAppState {

	private final Node scene = new Node("scene");

	public StUiSettings(Node guiNode) {
		guiNode.attachChild(scene);
	}

	@Override
	protected void initialize(Application app) {

		Container content = new Container();

		content.addChild(new ActionButton(new Action("noise type") {
			@SuppressWarnings("unchecked")
			@Override
			public void execute(Button source) {
				ListBox<String> list = new ListBox<>();

				Arrays.stream(NoiseType.values()).forEach(nt -> {
					list.getModel().add(nt.name());
				});

				list.addClickCommands(lb -> {
					Integer idx = lb.getSelectionModel().getSelection();
					if (idx == null)
						return;
					String noiseType = (String) lb.getModel().get(idx);
					source.setText(noiseType);
				});

				UiPopuped popuped = new UiPopuped(list);

				new UiCentered(popuped, app.getCamera()).center();

				GuiGlobals.getInstance().getPopupState().showModalPopup(popuped);
			}
		}));

		RollupPanel rollupPanel = new RollupPanel("noise settings", content, BaseStyles.GLASS);
		rollupPanel.setLocalTranslation(5, app.getCamera().getHeight() - 5, 0);

		scene.attachChild(rollupPanel);

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
