package boulder.gen;

import java.io.File;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

import jme3.common.noise.NoiseSettings;

final class StIo extends BaseAppState {

	private static final Logger logger = LoggerFactory.getLogger(StIo.class);

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

	protected Optional<File> fileChooser(FileType type) {
		File userDir = new File(System.getProperty("user.dir"));
		JFileChooser jfc = new JFileChooser(userDir);
		jfc.setDialogTitle("Export as " + type.extension());
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(type.description(), type.extension());
		jfc.addChoosableFileFilter(filter);
		int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			logger.debug("saving as = {}", jfc.getSelectedFile());
			return Optional.of(jfc.getSelectedFile());
		}

		return Optional.empty();
	}

	public void exportJson() {
		fileChooser(FileType.JSON).ifPresent((File f) -> {
			NoiseSettings noiseSettings = getState(StUiSettings.class).noiseSettings();
			SurfaceSettings surfaceSettings = getState(StUiSettings.class).surfaceSettings();

			ObjectMapper mapper = new ObjectMapper();

			ObjectNode noiseJson = mapper.createObjectNode();
			
			ObjectNode root = mapper.createObjectNode();
		});
	}

	public void exportJ3o() {
		fileChooser(FileType.J3O).ifPresent((File f) -> {

		});
	}

}
