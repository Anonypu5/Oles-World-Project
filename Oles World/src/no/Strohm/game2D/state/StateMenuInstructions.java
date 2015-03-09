package no.Strohm.game2D.state;

import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.graphics.Screen;

/**
 * Created by Ole on 22/12/13.
 */
public class StateMenuInstructions extends StateMenu {

    private static String[] instructions = {
            "Arrow keys to move,",
            "and navigate the menus.",
            "X to open inventory.",
            "C to attack or choose active item.",
    };

    public StateMenuInstructions(InputHandler input) {
        super(1, instructionsId, input);
    }

    protected void press() {
        setState(lastState);
    }

    public void render(Screen screen) {
        screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
        screen.renderText("Back", screen.w - 50, screen.h - 20, getColor(0), false);

        for(int i = 0; i < instructions.length; i++) {
            screen.renderText(instructions[i], 10, 50 + i * 10, getColor(-1), false);
        }
    }
}
