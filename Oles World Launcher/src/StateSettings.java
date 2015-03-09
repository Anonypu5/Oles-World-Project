import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class StateSettings extends StateMenu{

    public static boolean devBuild = false;
    public static boolean forceUp = false;
    static String[] butts = {"Developers Build: ","Force Update: disabled","","Back"};
    String[] scale = {"scale: 1","scale: 2","scale: 3","scale: 4"};

    public StateSettings(InputHandler input) {
        super(butts.length, settingsId, input);
    }
    public void start(){
        if(!devBuild)
            butts[0] = butts[0].substring(0,18) + "DISABLED";
        else
            butts[0] = butts[0].substring(0,18) + "ENABLED";

        butts[2] = scale[Launcher.TEMPSCALE-1];
    }
    protected void press() {
        switch(selected){
            case 0:
                if(devBuild) {
                    butts[0] = butts[0].substring(0, 18) + "DISABLED";
                    try {
                        editSettings(0,"0");
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        editSettings(0,"1");
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                    butts[0] = butts[0].substring(0, 18) + "ENABLED";
                }
                devBuild =! devBuild;
                break;
            case 1:
                if(devBuild) {
                    butts[1] = butts[1].substring(0, 14) + "DISABLED";
                }else {
                    butts[1] = butts[1].substring(0, 14) + "ENABLED";
                }
                forceUp =! forceUp;
                break;
            case 2:
                    Launcher.TEMPSCALE = Launcher.TEMPSCALE+1>4 ? 1 : Launcher.TEMPSCALE+1;
                    butts[2] = scale[Launcher.TEMPSCALE-1];
                    editSettings(1,""+Launcher.TEMPSCALE);
                break;
            case 3:
                setState(lastState);
        }
    }

    public void render(Screen screen) {
        screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
        screen.renderText(Launcher.TITLE, (screen.w - Launcher.TITLE.length() * 8) / 2, 50, 0xFF0000, false);
        for (int i = 0; i < butts.length; i++) {
            screen.renderText(butts[i], (screen.w - butts[i].length() * 8) / 2, screen.h / 2 - 15 + (i * 10), getColor(i), false);
        }
    }

    public static void editSettings(int line, String text){
        String path = System.getProperty("user.home")+"\\AppData\\Roaming\\.Ole-s-World";
        if(!new File(path).exists()){
            new File(path).mkdir();
        }
        path += "\\settings.txt";
        if(!new File(path).exists()){

        }
        try {
            Scanner s = new Scanner(new File(path));
            List<String> list = new ArrayList<String>();
            while(s.hasNext()){
                list.add(s.nextLine());
            }
            Formatter f = new Formatter(path);
            for(int i = 0; i < list.size(); i++){
                if(i == line){
                    f.format(text+System.getProperty("line.separator"));
                }else{
                    f.format(list.get(i)+System.getProperty("line.separator"));
                }

            }
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
