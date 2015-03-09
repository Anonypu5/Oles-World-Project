package no.Strohm.game2D.state;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.graphics.Screen;

/**
 * Created by Ole on 22/12/13.
 */
public class StateMenuExit extends StateMenu {

	private static final String[] options = new String[] {
			"Yes",
			"No"
	};

    public StateMenuExit(InputHandler input) {
        super(options.length, exitId, input);
    }

    protected void press() {
        switch (selected) {
            case 0:
                Game.stop();
                break;
            case 1:
                setState(lastState);
                break;
        }
    }

    public void render(Screen screen) {
        screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
        screen.renderText("Are you sure you want to quit?", (screen.w - "Are you sure you want to quit?".length() * 8) / 2, 50, col1 + col2, false);
		for (int i = 0; i < options.length; i++) {
			screen.renderText(options[i], (screen.w - options[i].length() * 8) / 2, screen.h / 2 - 8 + (i * 16), getColor(i), false);
		}
    }
}
