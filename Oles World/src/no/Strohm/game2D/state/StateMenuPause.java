package no.Strohm.game2D.state;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.graphics.Screen;

import java.io.IOException;

/**
 * Created by Ole on 22/12/13.
 */
public class StateMenuPause extends StateMenu {

	private static final String[] options = new String[]{
			"Resume",
			"Options",
			"Instructions",
			"Main Menu"
	};
	private final String title = "Paused";

	public StateMenuPause(InputHandler input) {
		super(options.length, pauseId, input);
	}

	protected void press() {
		switch (selected) {
			case 0:
				setState(gameId);
				break;
			case 1:
				setState(optionsId);
				break;
			case 2:
				setState(instructionsId);
				break;
			case 3:
                if(Game.client.run){
                    Game.client.run = false;
                    try {
                        Game.client.socket.close();
                    } catch (IOException e) {}
                }
				setState(startId);
				break;
		}
	}

	public void render(Screen screen) {
		screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
		screen.renderText(title, (screen.w - title.length() * 8) / 2, 60, col2, false);
		for (int i = 0; i < options.length; i++) {
			screen.renderText(options[i], (screen.w - options[i].length() * 8) / 2, screen.h / 2 - 15 + (i * 10), getColor(i), false);
		}
	}
}
