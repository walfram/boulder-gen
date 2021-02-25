package boulder.gen;

import com.jayfella.mesh.marchingcubes.DensityVolume;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

import jme3.common.noise.FastNoiseLite;

final class BoulderDensityVolume implements DensityVolume {

	private final Vector3f ext;
	private final FastNoiseLite noise;

	public BoulderDensityVolume(Vector3f ext, FastNoiseLite noise) {
		this.ext = ext;
		this.noise = noise;
	}

	private float densityAt(Vector3f p) {
		float x = p.x * p.x / (ext.x * ext.x);
		float y = p.y * p.y / (ext.y * ext.y);
		float z = p.z * p.z / (ext.z * ext.z);

		float e = FastMath.sqrt(x + y + z) - 1;

		// TODO: move "2" to settings and constructor
		e += 2 * noise.GetNoise(p.x, p.y, p.z);
		// e *= noise.GetNoise(p.x, p.y, p.z);

		return -1 * e;
	}

	@Override
	public float getDensity(int x, int y, int z) {
		return densityAt(new Vector3f(x, y, z));
	}

	@Override
	public float getDensity(float x, float y, float z) {
		return densityAt(new Vector3f(x, y, z));
	}

	@Override
	public Vector3f getFieldDirection(float x, float y, float z, Vector3f target) {
		return new FieldDirection(this, x, y, z, target).asVector3f();
	}

}
