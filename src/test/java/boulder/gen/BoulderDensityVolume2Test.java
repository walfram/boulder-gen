package boulder.gen;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.jayfella.mesh.marchingcubes.DensityVolume;
import com.jme3.math.Vector3f;

import jme3.common.noise.FastNoiseLite;

public class BoulderDensityVolume2Test {

	final Vector3f ext = new Vector3f(10, 10, 10);
	final FastNoiseLite noise = new FastNoiseLite();

	DensityVolume volume = new BoulderDensityVolume2(ext, noise, 1f);

	@Test
	public void test_density_at_origin() {
		float density = volume.getDensity(0, 0, 0);
		assertTrue(density > 0f);
	}
	
	@Test
	public void test_density_at_point_10_10_10() {
		float density = volume.getDensity(10f, 10f, 10f);
		assertTrue(density <= 0f);
	}

}
