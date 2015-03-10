package no.Strohm.game2D.state;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.util.FPS;

/**
 * Created by Ole on 22/12/13.
 */
public class StateMenuSettings extends StateMenu {

	private static String[] options = {
			"show fps: disabled",
			"back"
	};

	public StateMenuSettings(InputHandler input) {
		super(options.length, settingsId, input);
	}

	protected void press() {
		switch (selected) {
			case 0:
				FPS.renderFps = !FPS.renderFps;
				if(FPS.renderFps) {
					options[0] = "show fps: enabled";
				} else {
					options[0] = "show fps: disabled";
				}
				break;
			case 1:
				setState(lastState);
				break;
		}
	}

	public void tick() {
	}

	public void render(Screen screen) {
		screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
		screen.renderText("Settings", (screen.w - Game.TITLE.length() * 8) / 2, 50, 0xFF0000, false);

		for (int i = 0; i < options.length; i++) {
			screen.renderText(options[i], (screen.w - options[i].length() * 8) / 2, screen.h / 2 - 15 + (i * 10), getColor(i), false);
		}
	}
}
