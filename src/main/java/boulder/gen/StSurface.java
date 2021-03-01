package boulder.gen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayfella.mesh.MarchingCubesMeshGenerator;
import com.jayfella.mesh.marchingcubes.ArrayDensityVolume;
import com.jayfella.mesh.marchingcubes.DensityVolume;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitorAdapter;
import com.simsilica.mathd.Vec3i;

import jme3.common.material.MtlShowNormals;
import jme3.common.noise.FastNoiseLite;
import jme3.common.noise.NoiseSettings;

final class StSurface extends BaseAppState {

	private static final Logger logger = LoggerFactory.getLogger(StSurface.class);

	private final Node scene = new Node("scene");

	private final FastNoiseLite noise = new FastNoiseLite();

	public StSurface(Node rootNode) {
		rootNode.attachChild(scene);
	}

	@Override
	protected void initialize(Application app) {
	}

	protected void refreshSurface() {
		logger.debug("refresh called");

		scene.detachAllChildren();

		NoiseSettings noiseSettings = getState(StUiSettings.class).noiseSettings();
		SurfaceSettings surfaceSettings = getState(StUiSettings.class).surfaceSettings();

		noiseSettings.setup(noise);

		Vector3f ellipseExt = new Vector3f(surfaceSettings.ellipsex, surfaceSettings.ellipsey, surfaceSettings.ellipsez);
		DensityVolume source = new BoulderDensityVolume(ellipseExt, noise, surfaceSettings.noiseStrength);

		float scale = 1f;

		Material material = new MtlShowNormals(getApplication().getAssetManager());

		int cellSize = surfaceSettings.cellSize;

		MarchingCubesMeshGenerator generator = new MarchingCubesMeshGenerator(cellSize, cellSize, cellSize);
		int[] volSize = generator.getRequiredVolumeSize();
		Vec3i vsize = new Vec3i(volSize[0], volSize[1], volSize[2]);

		for (int cellx = -surfaceSettings.cellsx; cellx <= surfaceSettings.cellsx; cellx++) {
			for (int celly = -surfaceSettings.cellsy; celly <= surfaceSettings.cellsy; celly++) {
				for (int cellz = -surfaceSettings.cellsz; cellz <= surfaceSettings.cellsz; cellz++) {

					Vec3i cell = new Vec3i(cellx, celly, cellz);
					Vec3i w = cell.mult(cellSize);

					ArrayDensityVolume volume = ArrayDensityVolume.extractVolume(
							source,
							w.x,
							w.y,
							w.z,
							vsize.x,
							vsize.y,
							vsize.z);

					Mesh mesh = generator.buildMesh(volume);

					if (mesh == null) {
						continue;
					}

					Geometry chunk = new Geometry(cell.toString(), mesh);
					chunk.setMaterial(material);
					chunk.scale(scale);

					Vector3f t = w.add(1, 1, 1).toVector3f().mult(scale);
					chunk.setLocalTranslation(t);

					scene.attachChild(chunk);
				}
			}
		}

		logger.debug("refreshed, scene bound = {}", scene.getWorldBound());
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
		scene.depthFirstTraversal(new SceneGraphVisitorAdapter() {
			@Override
			public void visit(Geometry geom) {
				geom.getMaterial().getAdditionalRenderState().setWireframe(wireframe);
			}
		});
	}

}
