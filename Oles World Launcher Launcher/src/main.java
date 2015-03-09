import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Elias on 3/9/2015.
 */
public class main {
    public static void main(String[] args){
        String path = System.getProperty("user.home")+"\\AppData\\Roaming\\.Ole-s-World";
        if(!new File(path).exists()){
            new File(path).mkdir();
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
            }catch(Exception e){e.printStackTrace();}
        }
    }
}
