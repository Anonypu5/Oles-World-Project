import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by Elias on 3/9/2015.
 */
public class main {
    public static void main(String[] args){
        
        String path = System.getProperty("user.home")+"\\AppData\\Roaming\\.Ole-s-World";
        if(!new File(path).exists()){
            new File(path).mkdir();
        }if(new File(path+"\\settings.txt").exists()){
            try {
                Scanner s = new Scanner(new File(path+"\\settings.txt"));
                if(s.nextLine().equals("0")){
                    try {
                        ServerSocket ss = new ServerSocket(20163);
                        ss.close();
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Seems like Oles World is already running", "ERROR: 2", JOptionPane.PLAIN_MESSAGE);
                        System.exit(2);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(!new File(path+"\\info.txt").exists()){
            try {
                URL url;
                URLConnection con;
                DataInputStream dis;
                FileOutputStream fos;
                byte[] fileData;
                url = new URL("https://github.com/Anonypu5/Oles-World-Project/raw/master/Oles%20World%20Launcher/out/artifacts/Oles_World_Launcher_jar/Oles%20World%20Launcher.jar"); //File Location goes here
                con = url.openConnection(); // open the url connection.
                dis = new DataInputStream(con.getInputStream());
                fileData = new byte[con.getContentLength()];
                for (int q = 0; q < fileData.length; q++) {
                    fileData[q] = dis.readByte();
                }
                dis.close(); // close the data input stream
                fos = new FileOutputStream(new File(path+"\\Oles-World-Launcher.jar")); //FILE Save Location goes here
                fos.write(fileData);  // write out the file we want to save.
                fos.close(); // close the output stream writer
                
                url = new URL("https://github.com/Anonypu5/Oles-World-Project/raw/master/Oles%20World%20Launcher/info"); //File Location goes here
                con = url.openConnection(); // open the url connection.
                dis = new DataInputStream(con.getInputStream());
                fileData = new byte[con.getContentLength()];
                for (int q = 0; q < fileData.length; q++) {
                    fileData[q] = dis.readByte();
                }
                dis.close(); // close the data input stream
                fos = new FileOutputStream(new File(path+"\\info.txt")); //FILE Save Location goes here
                fos.write(fileData);  // write out the file we want to save.
                fos.close(); // close the output stream writer
                try {
                    Runtime.getRuntime().exec("cmd /c start "+path+"\\Oles-World-Launcher.jar");
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }catch(Exception e){e.printStackTrace();
            JOptionPane.showMessageDialog(null,"You need internet the\nfirst time you open Oles World","ERROR: 3",JOptionPane.ERROR_MESSAGE);System.exit(3);}
        }else{
            URL url = null;
            try {
                url = new URL("https://github.com/Anonypu5/Oles-World-Project/raw/master/Oles%20World%20Launcher/info");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            File file2 = new File(path+"\\info.txt");
            Scanner scan1 = null;
            Scanner scan2 = null;
            try {
                scan1 = new Scanner(url.openStream());
                scan2 = new Scanner(file2);
            } catch (Exception e) {
                try {
                    Runtime.getRuntime().exec("cmd /c start "+System.getProperty("user.home")+"\\AppData\\Roaming\\.Ole-s-World"+"\\Oles-World-Launcher.jar");
                    System.exit(3);
                } catch (Exception e2) {
                    e.printStackTrace();
                }
                e.printStackTrace();
            }
            if(!scan1.nextLine().equals(scan2.nextLine())){
                int x = JOptionPane.showOptionDialog(null,"A update for the launcher was found \n do you want to update?","Update?",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,new String[]{"Yes","No"},0);
                if(x == -1 || x == 1){
                    try {
                        Runtime.getRuntime().exec("cmd /c start "+path+"\\Oles-World-Launcher.jar");
                        System.exit(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(x == 0){
                    try {
                        URLConnection con;
                        DataInputStream dis;
                        FileOutputStream fos;
                        byte[] fileData;
                        url = new URL("https://github.com/Anonypu5/Oles-World-Project/raw/master/Oles%20World%20Launcher/out/artifacts/Oles_World_Launcher_jar/Oles%20World%20Launcher.jar"); //File Location goes here
                        con = url.openConnection(); // open the url connection.
                        dis = new DataInputStream(con.getInputStream());
                        fileData = new byte[con.getContentLength()];
                        for (int q = 0; q < fileData.length; q++) {
                            fileData[q] = dis.readByte();
                        }
                        dis.close(); // close the data input stream
                        fos = new FileOutputStream(new File(path+"\\Oles-World-Launcher.jar")); //FILE Save Location goes here
                        fos.write(fileData);  // write out the file we want to save.
                        fos.close(); // close the output stream writer

                        url = new URL("https://github.com/Anonypu5/Oles-World-Project/raw/master/Oles%20World%20Launcher/info"); //File Location goes here
                        con = url.openConnection(); // open the url connection.
                        dis = new DataInputStream(con.getInputStream());
                        fileData = new byte[con.getContentLength()];
                        for (int q = 0; q < fileData.length; q++) {
                            fileData[q] = dis.readByte();
                        }
                        dis.close(); // close the data input stream
                        fos = new FileOutputStream(new File(path+"\\info.txt")); //FILE Save Location goes here
                        fos.write(fileData);  // write out the file we want to save.
                        fos.close(); // close the output stream writer
                        try {
                            Runtime.getRuntime().exec("cmd /c start "+path+"\\Oles-World-Launcher.jar");
                            System.exit(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }catch(Exception e){e.printStackTrace();}
                }
            }else{
                try {
                    Runtime.getRuntime().exec("cmd /c start "+path+"\\Oles-World-Launcher.jar");
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
