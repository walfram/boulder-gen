package boulder.gen;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.simsilica.lemur.Action;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.DefaultCheckboxModel;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Insets3f;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.RollupPanel;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.props.PropertyPanel;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;

import jme3.common.noise.FastNoiseLite.FractalType;
import jme3.common.noise.FastNoiseLite.NoiseType;
import jme3.common.ui.UiCentered;
import jme3.common.noise.NoiseSettings;

final class StUiSettings extends BaseAppState {

	private final Node scene = new Node("scene");

	private final NoiseSettings noiseSettings = new NoiseSettings();
	private final SurfaceSettings surfaceSettings = new SurfaceSettings();

	private final Insets3f insets = new Insets3f(5, 2, 5, 2);

	public StUiSettings(Node guiNode) {
		guiNode.attachChild(scene);
	}

	@Override
	protected void initialize(Application app) {
		noiseSettings.mNoiseType = NoiseType.OpenSimplex2;
		noiseSettings.mFractalType = FractalType.FBm;
		noiseSettings.mOctaves = 4;

		Container content = new Container();

		// content.addChild(new ActionButton(new CallMethodAction("change noise settings", this, "showNoiseSettings"))).setInsets(
		// insets);

		content.addChild(new ActionButton(new AcCall("change noise settings", () -> showNoiseSettings()))).setInsets(insets);

		{
			PropertyPanel props = new PropertyPanel(BaseStyles.GLASS);

			props.addIntField("Cell size", surfaceSettings, "cellSize", 4, 64, 1);

			props.addIntField("cells x", surfaceSettings, "cellsx", 1, 10, 1);
			props.addIntField("cells y", surfaceSettings, "cellsy", 1, 10, 1);
			props.addIntField("cells z", surfaceSettings, "cellsz", 1, 10, 1);

			props.addFloatField("ellipse x", surfaceSettings, "ellipsex", 1, 64, 0.5f);
			props.addFloatField("ellipse y", surfaceSettings, "ellipsey", 1, 64, 0.5f);
			props.addFloatField("ellipse z", surfaceSettings, "ellipsez", 1, 64, 0.5f);

			props.addFloatField("noise strength", surfaceSettings, "noiseStrength", 0, 4, 0.125f);

			content.addChild(props).setInsets(insets);
		}

		content.addChild(new ActionButton(new AcCall("refresh surface", () -> getState(StSurface.class).refreshSurface())))
				.setInsets(insets);

		{
			Checkbox chkCells = content.addChild(new Checkbox("show cells", new DefaultCheckboxModel(false)));
			chkCells.setInsets(insets);

			VersionedReference<Boolean> refCells = chkCells.getModel().createReference();
			chkCells.addControl(new CtCallback(refCells, () -> getState(StCells.class).cellVisibility(refCells.get())));

			Checkbox chkWireframe = content.addChild(new Checkbox("wireframe", new DefaultCheckboxModel(false)));
			chkWireframe.setInsets(insets);

			VersionedReference<Boolean> refWire = chkWireframe.getModel().createReference();
			chkWireframe.addControl(new CtCallback(refWire, () -> getState(StSurface.class).useWireframe(refWire.get())));
		}

		RollupPanel rollupPanel = new RollupPanel("boulder surface settings", content, BaseStyles.GLASS);
		rollupPanel.getTitleElement().setMaxWidth(250f);
		rollupPanel.setLocalTranslation(5, app.getCamera().getHeight() - 5, 0);

		scene.attachChild(rollupPanel);

		getState(StSurface.class).refreshSurface();
	}

	private void showNoiseSettings() {
		PropertyPanel props = new PropertyPanel(BaseStyles.GLASS);

		props.addEnumField("Noise type", noiseSettings, "mNoiseType");
		props.addEnumField("Rotation", noiseSettings, "mRotationType3D");
		props.addEnumField("Fractal type", noiseSettings, "mFractalType");

		props.addFloatField("Frequency", noiseSettings, "mFrequency", 0, 1, 0.001f);
		props.addIntField("Octaves", noiseSettings, "mOctaves", 0, 10, 1);
		props.addFloatField("Lacunarity", noiseSettings, "mLacunarity", 0, 4, 0.001f);

		props.addFloatField("Gain", noiseSettings, "mGain", 0, 2, 0.001f);
		props.addFloatField("Wgt.Str.", noiseSettings, "mWeightedStrength", 0, 4, 0.001f);
		props.addFloatField("PP.Str.", noiseSettings, "mPingPongStength", 0, 4, 0.001f);

		props.addEnumField("Cell.Dist.Func", noiseSettings, "mCellularDistanceFunction");
		props.addEnumField("Cell.Ret.Type", noiseSettings, "mCellularReturnType");
		props.addFloatField("Cell.Jitt.Modr", noiseSettings, "mCellularJitterModifier", 0, 2, 0.001f);

		props.addEnumField("Dmn.Warp.Type", noiseSettings, "mDomainWarpType");
		props.addFloatField("Dmn.Warp.Amp", noiseSettings, "mDomainWarpAmp", 0, 2, 0.001f);

		Container container = new Container();
		Label label = container.addChild(new Label("noise settings", new ElementId("window.title.label")));
		label.setMaxWidth(640f);

		container.addChild(props);

		container.addChild(new ActionButton(new Action("ok") {
			@Override
			public void execute(Button source) {
				GuiGlobals.getInstance().getPopupState().closePopup(container);
			}
		}));

		new UiCentered(container, getApplication().getCamera()).center();

		GuiGlobals.getInstance().getPopupState().showModalPopup(container, new ColorRGBA(0.2f, 0.2f, 0.2f, 0.5f));
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

	public SurfaceSettings surfaceSettings() {
		return surfaceSettings;
	}

	public NoiseSettings noiseSettings() {
		return noiseSettings;
	}

}
