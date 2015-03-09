import javax.swing.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class StateDev extends StateMenu{

    private final String userDir = System.getProperty("user.home");
    private String dir = "";
    private boolean first = true;

    public StateDev(InputHandler input) {
        super(0, devId, input);
    }

    protected void press() {
    }

    public void render(Screen screen) {
        if(first){
            String text = "";
            dir = userDir+"\\AppData\\roaming";
            if(new File(dir).exists()){
                System.out.println("roaming exists");
                dir+="\\.Ole-s-World";
                if(!new File(dir).exists()){
                    System.out.println("Ole-s-World does not exists");
                    new File(dir).mkdir();
                }
                dir+="\\developerBuild";
                if(!new File(dir).exists()){
                    System.out.println("Ole-s-World does not exists");
                    new File(dir).mkdir();
                }
            }else{
                System.out.println("roaming does not exist");
                JOptionPane.showMessageDialog(null, "Please delete this launcher and download it again.\nIf the same thing happens again, please contact support\nat http://grabit.no and tell them you got the error message 1","ERROR: 1", JOptionPane.ERROR_MESSAGE);
            }
            if(!new File(dir).exists()){
                new File(dir).mkdir();
            }
            if(!new File(dir+"\\info.txt").exists() || !new File(dir+"\\Ole-s-World.jar").exists())
                text = "Downloading Build...";
            else
                text = "Checking For Update";
            screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
            screen.renderText(Launcher.TITLE, (screen.w - Launcher.TITLE.length() * 8) / 2, 50, 0xFF0000, false);
            screen.renderText(text, (screen.w - (text.length()) * 8) / 2, screen.h / 2 - 15 + (0 * 10), col1, false);
            screen.renderText("Please Don't Exit", (screen.w - "Please Don't Exit".length() * 8) / 2, screen.h / 2 - 15 + (2 * 10), col1, false);
            first = false;
            return;
        }
        if(!new File(dir+"\\info.txt").exists() || !new File(dir+"\\Ole-s-World.jar").exists()){
            downloadDev();
            try {
                if(!StateSettings.devBuild) {
                    Launcher.isRunningSockets.close();
                }
                Runtime.getRuntime().exec("cmd /c start " + dir + "\\Ole-s-World.jar");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }else{
            URL url = null;
            try {
                url = new URL("https://github.com/InzaneNova/Ole-s-World/raw/master/info.txt");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            File file2 = new File(dir+"\\info.txt");
            Scanner scan1 = null;
            Scanner scan2 = null;
            try {
                scan1 = new Scanner(url.openStream());
                scan2 = new Scanner(file2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(!scan1.nextLine().equals(scan2.nextLine())){
                setState(updateQId);
            }else{
                try {
                    if(!StateSettings.devBuild) {
                        Launcher.isRunningSockets.close();
                    }
                    Runtime.getRuntime().exec("cmd /c start "+dir+"\\Ole-s-World.jar");
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void downloadDev(){

        URL url;
        URLConnection con;
        DataInputStream dis;
        FileOutputStream fos;
        byte[] fileData;
        try {
            url = new URL("https://github.com/InzaneNova/Ole-s-World/raw/master/out/artifacts/_dev_Ole_s_World/.dev_Ole_s%20World.jar"); //File Location goes here
            con = url.openConnection(); // open the url connection.
            dis = new DataInputStream(con.getInputStream());
            fileData = new byte[con.getContentLength()];
            for (int q = 0; q < fileData.length; q++) {
                fileData[q] = dis.readByte();
            }
            dis.close(); // close the data input stream
            fos = new FileOutputStream(new File(dir+"\\Ole-s-World1.jar")); //FILE Save Location goes here
            fos.write(fileData);  // write out the file we want to save.
            fos.close(); // close the output stream writer
            if(new File(dir+"\\Ole-s-World.jar").exists()){
                new File(dir+"\\Ole-s-World.jar").delete();
            }
            new File(dir+"\\Ole-s-World1.jar").renameTo(new File(dir+"\\Ole-s-World.jar"));
            String filePath = dir+"\\Ole-s-World.jar"; //where your jar is located.
        }
        catch(Exception m) {
            System.out.println(m);
        }try {
            url = new URL("https://github.com/InzaneNova/Ole-s-World/raw/master/info.txt"); //File Location goes here
            con = url.openConnection(); // open the url connection.
            dis = new DataInputStream(con.getInputStream());
            fileData = new byte[con.getContentLength()];
            for (int q = 0; q < fileData.length; q++) {
                fileData[q] = dis.readByte();
            }
            dis.close(); // close the data input stream
            fos = new FileOutputStream(new File(dir+"\\info.txt")); //FILE Save Location goes here
            fos.write(fileData);  // write out the file we want to save.
            fos.close(); // close the output stream writer
        }
        catch(Exception m) {
            System.out.println(m);
        }
    }
}
