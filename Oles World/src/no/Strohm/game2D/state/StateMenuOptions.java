package no.Strohm.game2D.state;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.util.FPS;

/**
 * Created by Ole on 22/12/13.
 */
public class StateMenuOptions extends StateMenu {

	public StateMenuOptions(InputHandler input) {
		super(2, optionsId, input);
	}

	protected void press() {
		switch (selected) {
			case 0:
				FPS.renderFps = !FPS.renderFps;
				break;
			case 1:
				setState(lastState);
				break;
		}
	}

	public void render(Screen screen) {
		screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
		screen.renderText("renderFps: " + FPS.renderFps, (Game.WIDTH - ("renderFps: " + FPS.renderFps).length() * 8) / 2, 50, getColor(0), false);
		screen.renderText("Back", screen.w - 50, screen.h - 20, getColor(1), false);
	}
}
