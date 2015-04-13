import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Elias on 13.04.2015.
 */
public class Main {

    public static JFrame jFrame;
    private static JTextArea mainTextArea;
    private static JTextField jTextField;
    public static final String path = System.getProperty("user.home")+"\\.Ole-s-World";

    public static void main(String[] args){
        jFrame = new JFrame("Oles World Server");
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());
        mainTextArea = new JTextArea(20,60);
        mainTextArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(mainTextArea);
        jFrame.add(jScrollPane, BorderLayout.NORTH);
        jTextField = new JTextField(60);
        jFrame.add(jTextField, BorderLayout.EAST);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        println("Preparing to run server");
        println("Checking if directory " + path + " exists");
        File directory = new File(path);
        if(directory.exists()){
            println("Successfully located directory ");
        }else{
            println("Were not able to located directory "+path+", preceding to create one");
            directory.mkdir();
        }
    }

    public static void println(String m){
        String t = mainTextArea.getText();
        t += m + "\n";
        mainTextArea.setText(t);
    }
}
