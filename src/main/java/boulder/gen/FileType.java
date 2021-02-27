package boulder.gen;

enum FileType {

	JSON("json files", "json"), J3O("jme j3i files", "j3o");

	private final String desc;
	private final String ext;

	FileType(String desc, String ext) {
		this.desc = desc;
		this.ext = ext;
	}

	String description() {
		return desc;
	}

	String extension() {
		return ext;
	}

}
