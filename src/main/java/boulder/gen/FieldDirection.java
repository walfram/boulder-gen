package boulder.gen;

import com.jayfella.mesh.marchingcubes.DensityVolume;
import com.jme3.math.Vector3f;

public final class FieldDirection {

	private final DensityVolume volume;
	private final float x;
	private final float y;
	private final float z;
	private final Vector3f target;

	public FieldDirection(DensityVolume volume, float x, float y, float z, Vector3f target) {
		this.volume = volume;
		this.x = x;
		this.y = y;
		this.z = z;
		this.target = target == null ? Vector3f.NAN.clone() : target;
	}

	public Vector3f asVector3f() {
		float d = 1f;

		float nx = volume.getDensity(x + d, y, z) - volume.getDensity(x - d, y, z);
		float ny = volume.getDensity(x, y + d, z) - volume.getDensity(x, y - d, z);
		float nz = volume.getDensity(x, y, z + d) - volume.getDensity(x, y, z - d);

		target.set(-nx, -ny, -nz);
		target.normalizeLocal();

		return target;
	}

}
