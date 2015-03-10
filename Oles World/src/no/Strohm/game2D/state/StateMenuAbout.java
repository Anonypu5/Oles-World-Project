package no.Strohm.game2D.state;

import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.graphics.Screen;

/**
 * Created by Ole on 22/12/13.
 */
public class StateMenuAbout extends StateMenu {

	private String[] lines = {
			"This game was made by:",
			"Ole Marius Strøhm",
			"Elias Fyksen"
	};

	public StateMenuAbout(InputHandler input) {
		super(1, aboutId, input);
	}

	protected void press() {
		pressedTimer = 10;
		setState(lastState);
	}

	public void render(Screen screen) {
		screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
		screen.renderText("Back", screen.w - 50, screen.h - 20, getColor(0), false);

		screen.renderText("This game was made by:", (screen.w - "This game was made by:".length() * 8) / 2, (screen.h >> 2) - 6, getColor(-1), false);
		screen.renderText("Ole Marius Strøhm", (screen.w - "Ole Marius Strøhm".length() * 8) / 2, (screen.h >> 2) + 6, getColor(-1), false);
	}
}
