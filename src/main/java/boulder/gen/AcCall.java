package boulder.gen;

import com.simsilica.lemur.Action;
import com.simsilica.lemur.Button;

final class AcCall extends Action {

	private final Runnable callback;

	public AcCall(String name, Runnable callback) {
		super(name);
		this.callback = callback;
	}

	@Override
	public void execute(Button source) {
		callback.run();
	}

}
