package boulder.gen;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import com.simsilica.mathd.Vec3i;

import jme3.common.material.MtlUnshaded;

final class StCells extends BaseAppState {

	private final Node scene = new Node("scene");

	public StCells(Node rootNode) {
		rootNode.attachChild(scene);
	}

	@Override
	protected void initialize(Application app) {
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

	public void cellVisibility(boolean visible) {
		scene.detachAllChildren();

		if (!visible)
			return;

		SurfaceSettings surfaceSettings = getState(StUiSettings.class).surfaceSettings();

		int cellsx = surfaceSettings.cellsx;
		int cellsy = surfaceSettings.cellsy;
		int cellsz = surfaceSettings.cellsz;

		float ext = surfaceSettings.cellSize * 0.5f;
		Mesh wbox = new WireBox(ext, ext, ext);

		Material m = new MtlUnshaded(getApplication().getAssetManager(), ColorRGBA.Yellow);
		
		for (int cx = -cellsx; cx <= cellsx; cx++) {
			for (int cy = -cellsy; cy <= cellsy; cy++) {
				for (int cz = -cellsz; cz <= cellsz; cz++) {
					Vec3i cell = new Vec3i(cx, cy, cz);

					Vector3f translation = cell.toVector3f().mult(surfaceSettings.cellSize).add(ext, ext, ext);

					Geometry g = new Geometry(cell.toString(), wbox);
					g.setMaterial(m);
					g.setLocalTranslation(translation);

					scene.attachChild(g);
				}
			}
		}
	}

}
