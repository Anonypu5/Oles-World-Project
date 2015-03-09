package no.Strohm.game2D.state;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.Multiplayer.Client;
import no.Strohm.game2D.Multiplayer.Server;
import no.Strohm.game2D.graphics.Screen;

import java.awt.event.KeyEvent;
import java.net.Inet4Address;

/**
 * Created by Ole on 28/11/2014.
 */
public class StateMenuStartServer extends StateMenu {

	private String maxPlayers = "",gameTag = "";

	private static String[] options = new String[]{
			"<max players>",
			"<game tag>",
			"Start Server",
			"Back",
	};
	private static String title = "Start Server";

	public StateMenuStartServer(InputHandler input) {
		super(options.length, startServerId, input);
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
					Game.server = new Server(1999, Integer.parseInt(maxPlayers));
					Game.server.start();
					new Client(Inet4Address.getLocalHost().getHostAddress(), 1999, gameTag);
					setState(gameId);
				} catch (Exception e) {
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
				for (int x = 0; x < 10; x++) {
					if (input.clicked[checks[x]]) {
						maxPlayers += values[x];
					}
				}

				if (input.clicked[KeyEvent.VK_PERIOD]) {
					maxPlayers += ".";
				}

				if (input.clicked[KeyEvent.VK_BACK_SPACE] && maxPlayers.length() > 0) {
					maxPlayers = maxPlayers.substring(0, maxPlayers.length() - 1);
				}

				if (!maxPlayers.equals("")) {
					options[0] = maxPlayers;
				} else {
					options[0] = "<max players>";
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
