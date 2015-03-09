

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

/**
 * Created by Ole on 08/03/2015.
 */
public class Launcher extends Canvas implements Runnable {

	public static final String TITLE = "Ole's World";
	public static int SCALE = 4;
    public static int TEMPSCALE = SCALE;
    public static ServerSocket isRunningSockets;
	public static int WIDTH = 1280 / SCALE;
	public static int HEIGHT = (WIDTH / 16) * 10;
	private static boolean running = false;
	private Dimension d = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
	private BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private JFrame frame;
	private InputHandler input;
	private Screen screen;
	private int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

	public static void main(String[] args) {

        String path = System.getProperty("user.home") + "\\AppData\\roaming\\.Ole-s-World";
        if(!new File(path).exists()){
            new File(path).mkdir();
        }
        path += "\\settings.txt";
        if(new File(path).exists()){
            System.out.println("Shit is there: "+path);
            try {
                Scanner s = new Scanner(new File(path));
                if(s.nextLine().equals("1")){
                    System.out.println("Shit got changed: "+path);
                    StateSettings.devBuild = true;
                }else{
                    StateSettings.devBuild = false;
                }
                String v = s.nextLine();
                if(v.equals("1")){
                    SCALE = 1;
                }else if(v.equals("2")){
                    SCALE = 2;
                }else if(v.equals("3")){
                    SCALE = 3;
                }else{
                    SCALE = 4;
                }
            }catch(Exception e){System.out.println(e);}
        }else{
            System.out.println("Shit is not there: " + path);
            try {
                Formatter file = new Formatter(path);
                file.format("0" + System.getProperty("line.separator"));
                file.format("4");
                file.close();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        TEMPSCALE = SCALE;

        if(!StateSettings.devBuild){
            try {
                isRunningSockets = new ServerSocket(20163);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Seems like Oles World is already running","ERROR: 2",JOptionPane.PLAIN_MESSAGE);
                System.exit(2);
            }
        }

        Launcher launcher = new Launcher();
		launcher.setPreferredSize(launcher.d);
		launcher.setMinimumSize(launcher.d);
		launcher.setMaximumSize(launcher.d);

		launcher.frame = new JFrame(Launcher.TITLE);
		launcher.frame.setUndecorated(false);
		launcher.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		launcher.frame.setResizable(false);
		launcher.frame.add(launcher);
		launcher.frame.pack();
		launcher.frame.setLocationRelativeTo(null);
		launcher.frame.setVisible(true);

		try {
			launcher.frame.setIconImage(ImageIO.read(Launcher.class.getResourceAsStream("/launcherIcon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		launcher.start();
	}

	public synchronized void start() {
		running = true;
		input = new InputHandler();
		screen = new Screen(WIDTH, HEIGHT);

		State.init(input);

		addKeyListener(input);
		new Thread(this).start();
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double ns = 1000000000.0 / 60.0;
		double unprocessed = 0;
		int ticks = 0;
		int frames = 0;

		requestFocus();
		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / ns;
			lastTime = now;
			while (unprocessed >= 1) {
				tick();
				ticks++;
				unprocessed--;
			}
			{
				render();
				frames++;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				ticks = 0;
				frames = 0;
			}
		}

		screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
		screen.renderText("Exiting Game!", 50, (HEIGHT - 8) / 2, 0, false);
		screen.copy(pixels);
		getGraphics().drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);

	}

	private void tick() {
		input.tick();
		State.getCurState().tick();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		State.getCurState().render(screen);

		screen.copy(pixels);

		Graphics g;
		g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.dispose();
		bs.show();
	}

}
