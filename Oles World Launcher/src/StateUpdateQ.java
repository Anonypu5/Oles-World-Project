
public class StateUpdateQ extends StateMenu{

    static final String[] butts = {"yes","no"};

    public StateUpdateQ(InputHandler input) {
        super(butts.length, updateQId, input);
    }

    protected void press() {
        switch(selected){
            case 0:
                setState(updatingId);
                break;
            case 1:
                try{
                    if(!StateSettings.devBuild) {
                        if(!StateSettings.devBuild) {
                            Launcher.isRunningSockets.close();
                        }
                        Runtime.getRuntime().exec("cmd /c start " + System.getProperty("user.home") + "\\AppData\\roaming\\.Ole-s-World\\defaultBuild\\Ole-s-World.jar");
                    }else{
                        if(!StateSettings.devBuild) {
                            Launcher.isRunningSockets.close();
                        }
                        Runtime.getRuntime().exec("cmd /c start " + System.getProperty("user.home") + "\\AppData\\roaming\\.Ole-s-World\\developerBuild\\Ole-s-World.jar");
                    }
                    System.exit(0);
                }catch(Exception e){
                    System.out.println(e);
                }
        }
    }

    public void render(Screen screen) {
        screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
        screen.renderText(Launcher.TITLE, (screen.w - Launcher.TITLE.length() * 8) / 2, 50, 0xFF0000, false);
        screen.renderText("A Update was found", (screen.w - "A Update was found".length() * 8) / 2, 70, col1, false);
        screen.renderText("do you want to update?", (screen.w - "do you want to update?".length() * 8) / 2, 80, col1, false);
        for (int i = 0; i < butts.length; i++) {
            screen.renderText(butts[i], (screen.w - butts[i].length() * 8) / 2, screen.h / 2 + (i * 10), getColor(i), false);
        }
    }
}
