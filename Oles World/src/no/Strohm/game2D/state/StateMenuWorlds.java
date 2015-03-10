package no.Strohm.game2D.state;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.graphics.Screen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ole on 15/12/13.
 */
public class StateMenuWorlds extends StateMenu {

	private static String[] options = null;

	public StateMenuWorlds(InputHandler input) {
		super(0, worldsId, input);
	}

	protected void press() {

	}

	public void render(Screen screen) {
		screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
		screen.renderText("Select World", (screen.w - "Select World".length() * 8) / 2, 50, 0xFF0000, false);
		for (int i = 0; i < options.length; i++) {
			screen.renderText(options[i], (screen.w - options[i].length() * 8) / 2, screen.h / 2 - 15 + (i * 10), getColor(i), false);
		}
	}

    public void start(){
        String path = Game.path+"\\saves";
        if(!new File(path).exists()){
            new File(path).mkdir();
        }
        List<String> l = new ArrayList<String>();
        for(File x:new File(path).listFiles()){
            l.add(x.getName());
        }
        l.add("[new world]");
        options = l.toArray(new String[l.size()]);
        selectionCount = options.length;
    }
}
