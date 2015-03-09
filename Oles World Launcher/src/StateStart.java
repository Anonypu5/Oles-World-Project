import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class StateStart extends StateMenu{

    static final String[] butts = {"Start Game","Settings","Exit"};

    public StateStart(InputHandler input) {
        super(butts.length, startId, input);
    }

    protected void press() {
        switch(selected){
            case 0:
                if(StateSettings.devBuild) {
                    setState(devId);
                }else {
                    setState(defId);
                }break;
            case 1:
                setState(settingsId);
                break;
            case 2:
                System.exit(9000+1);
        }
    }

    public void render(Screen screen) {
        screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
        screen.renderText(Launcher.TITLE, (screen.w - Launcher.TITLE.length() * 8) / 2, 50, 0xFF0000, false);
        for (int i = 0; i < butts.length; i++) {
            screen.renderText(butts[i], (screen.w - butts[i].length() * 8) / 2, screen.h / 2 - 15 + (i * 10), getColor(i), false);
        }
    }
}
