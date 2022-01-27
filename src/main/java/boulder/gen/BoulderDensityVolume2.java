package boulder.gen;

import com.jayfella.mesh.marchingcubes.DensityVolume;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

import jme3.common.noise.FastNoiseLite;

final class BoulderDensityVolume2 implements DensityVolume {

	private final Vector3f ellipseExt;

	private final float extx;
	private final float exty;
	private final float extz;

	private final FastNoiseLite noise;
	private final float strength;

	public BoulderDensityVolume2(Vector3f ext, FastNoiseLite noise, float noiseStrength) {
		this.ellipseExt = ext;

		this.extx = ext.x * ext.x;
		this.exty = ext.y * ext.y;
		this.extz = ext.z * ext.z;

		this.noise = noise;
		this.strength = noiseStrength;
	}

	private float densityAt(Vector3f p) {
		float xx = FastMath.sqr(p.x) / extx;
		float yy = FastMath.sqr(p.y) / exty;
		float zz = FastMath.sqr(p.z) / extz;

		float e = FastMath.sqrt(xx + yy + zz) - 1;
		e *= -1f;

		e += exclude(p);
		// e += strength * noise.GetNoise(p.x, p.y, p.z);

		// e = FastMath.pow(FastMath.pow(p.x, 6) + FastMath.pow(p.y, 6) + FastMath.pow(p.z, 6), 1.0f / 6.0f) - 1.0f;
		e = FastMath.pow(1.0f - FastMath.sqrt(p.x * p.x + p.y * p.y), 2f) + p.z * p.z - 0.25f;
		e *= -1f * strength;

		return e;
	}

	private float exclude(Vector3f p) {
		float xx = FastMath.sqr(p.x) / 4f;
		float yy = FastMath.sqr(p.y) / 4f;
		float zz = FastMath.sqr(p.z) / 196f;

		return -1f * (FastMath.sqrt(xx + yy + zz) - 1f);
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
