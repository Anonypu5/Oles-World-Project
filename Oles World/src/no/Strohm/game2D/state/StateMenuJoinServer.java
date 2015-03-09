package no.Strohm.game2D.state;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.Multiplayer.Client;
import no.Strohm.game2D.Multiplayer.Server;
import no.Strohm.game2D.graphics.Screen;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Created by Ole on 28/11/2014.
 */
public class StateMenuJoinServer extends StateMenu {

	private String host = "",gameTag = "";

	private static String[] options = new String[]{
			"<host>",
			"<game tag>",
			"Join Server",
			"Back",
	};
	private static String title = "Join Server";

	public StateMenuJoinServer(InputHandler input) {
		super(options.length, joinServerId, input);
	}

	@Override
	protected void press() {
		switch (selected) {
			case 0:
				break;
			case 1:
                break;
			case 2:
				try {
					new Client((host.equals("")?"localhost":host), 1999, gameTag);
					setState(gameId);
				} catch (Exception e) {
					System.out.println("Check your internet connection");
				}
				break;
			case 3:
                Game.Online = true;
				setState(multiplayerId);
		}
	}

	@Override
	public void render(Screen screen) {
		screen.renderArea(0x353535, 0, Game.WIDTH, 0, Game.HEIGHT, false);
		screen.renderText(title, (screen.w - title.length() * 8) / 2, 60, col2, false);
		for (int i = 0; i < options.length; i++) {
			screen.renderText(options[i], (screen.w - options[i].length() * 8) / 2, screen.h / 2 - 15 + (i * 10), getColor(i), false);
		}
	}

	public void inputTick(InputHandler input){
		int[] checks = {
				KeyEvent.VK_0,
				KeyEvent.VK_1,
				KeyEvent.VK_2,
				KeyEvent.VK_3,
				KeyEvent.VK_4,
				KeyEvent.VK_5,
				KeyEvent.VK_6,
				KeyEvent.VK_7,
				KeyEvent.VK_8,
				KeyEvent.VK_9,
				KeyEvent.VK_Q,
				KeyEvent.VK_W,
				KeyEvent.VK_E,
				KeyEvent.VK_R,
				KeyEvent.VK_T,
				KeyEvent.VK_Y,
				KeyEvent.VK_U,
				KeyEvent.VK_I,
				KeyEvent.VK_O,
				KeyEvent.VK_P,
				KeyEvent.VK_A,
				KeyEvent.VK_S,
				KeyEvent.VK_D,
				KeyEvent.VK_F,
				KeyEvent.VK_G,
				KeyEvent.VK_H,
				KeyEvent.VK_J,
				KeyEvent.VK_K,
				KeyEvent.VK_L,
				KeyEvent.VK_Z,
				KeyEvent.VK_X,
				KeyEvent.VK_C,
				KeyEvent.VK_V,
				KeyEvent.VK_B,
				KeyEvent.VK_N,
				KeyEvent.VK_M,
		};
		String[] values = {
				"0",
				"1",
				"2",
				"3",
				"4",
				"5",
				"6",
				"7",
				"8",
				"9",
				"q",
				"w",
				"e",
				"r",
				"t",
				"y",
				"u",
				"i",
				"o",
				"p",
				"a",
				"s",
				"d",
				"f",
				"g",
				"h",
				"j",
				"k",
				"l",
				"z",
				"x",
				"c",
				"v",
				"b",
				"n",
				"m"
		};
		switch (selected) {

			case 0:
				for (int x = 0; x < values.length; x++) {
					if (input.clicked[checks[x]]) {
						host += values[x];
					}
				}

				if (input.clicked[KeyEvent.VK_PERIOD]) {
					host += ".";
				}

				if (input.clicked[KeyEvent.VK_BACK_SPACE] && host.length() > 0) {
					host = host.substring(0, host.length() - 1);
				}

				if (!host.equals("")) {
					options[0] = host;
				} else {
					options[0] = "<host>";
				}
				break;

			case 1:
				for (int x = 0; x < checks.length; x++) {
					if (input.clicked[checks[x]]) {
						gameTag += values[x];
					}
				}

				if (input.clicked[KeyEvent.VK_PERIOD]) {
					gameTag += ".";
				}

				if (input.clicked[KeyEvent.VK_BACK_SPACE] && gameTag.length() > 0) {
					gameTag = gameTag.substring(0, gameTag.length() - 1);
				}

				if (!gameTag.equals("")) {
					options[1] = gameTag;
				} else {
					options[1] = "<Game Tag>";
				}
				break;
		}
	}
}
