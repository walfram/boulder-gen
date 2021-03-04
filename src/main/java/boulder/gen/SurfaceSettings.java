package boulder.gen;

import com.fasterxml.jackson.databind.node.ObjectNode;

public final class SurfaceSettings {

	public int cellSize = 24;

	public int cellsx = 1;
	public int cellsy = 1;
	public int cellsz = 1;

	public float ellipsex = 10.0f;
	public float ellipsey = 10.0f;
	public float ellipsez = 10.0f;

	public float noiseStrength = 2f;

	public void toJson(ObjectNode node) {
		node.put("cellSize", cellSize);

		node.put("cellsx", cellsx);
		node.put("cellsy", cellsy);
		node.put("cellsz", cellsz);

		node.put("ellipsex", ellipsex);
		node.put("ellipsey", ellipsey);
		node.put("ellipsez", ellipsez);

		node.put("noiseStrength", noiseStrength);
	}

}
