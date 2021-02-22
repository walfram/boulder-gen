package boulder.gen;

import com.simsilica.lemur.Action;
import com.simsilica.lemur.ActionButton;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Panel;

final class UiPopuped extends Container {

	public UiPopuped(Panel content) {
		super();

		addChild(new ActionButton(new Action("x") {
			@Override
			public void execute(Button source) {
				GuiGlobals.getInstance().getPopupState().closePopup(UiPopuped.this);
			}
		}));
		
		addChild(content);
	}

}
