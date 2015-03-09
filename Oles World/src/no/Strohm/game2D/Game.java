package no.Strohm.game2D;

import no.Strohm.game2D.Multiplayer.Client;
import no.Strohm.game2D.Multiplayer.Server;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.state.State;
import no.Strohm.game2D.util.FPS;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * Created by Ole on 13/12/13.
 */
public class Game extends Canvas implements Runnable {

	public static final String TITLE = "Ole's World", VERSION = "a1.01.9";
	public static boolean DEV = false;
	public static Server server;
    public static ServerSocket isRunningSockets;
	public static Client client;
	public static boolean Online = false;
	public static int SCALE = 4;
	public static int WIDTH = 1280 / SCALE;
	public static int HEIGHT = (WIDTH / 16) * 10;
	public static int mapHeight = 10, mapWidth = 10;
	private static boolean running = false;
	private Dimension d = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
	private BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private JFrame frame;
	private InputHandler input;
	private Screen screen;
	private int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

	public static void main(String[] args) {
        String f = System.getProperty("user.home") + "\\AppData\\roaming\\.Ole-s-World";
        if(!new File(f).exists()){
            new File(f).mkdir();
        }
        f += "\\settings.txt";
        if(new File(f).exists()){
            System.out.println("Shit is there: "+f);
            try {
                Scanner s = new Scanner(new File(f));
                if(s.nextLine().equals("1")){
                    System.out.println("Shit got changed: "+f);
                    DEV = true;
                }else{
                    DEV = false;
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
        }

        if(!DEV){
            try {
                isRunningSockets = new ServerSocket(20163);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Seems like Oles World is already running","ERROR: 2",JOptionPane.PLAIN_MESSAGE);
                System.exit(2);
            }
        }

		Game game = new Game();
		game.setPreferredSize(game.d);
		game.setMinimumSize(game.d);
		game.setMaximumSize(game.d);

		game.frame = new JFrame(Game.TITLE);
		game.frame.setUndecorated(false);
		game.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.frame.setResizable(false);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		try {
			game.frame.setIconImage(ImageIO.read(Game.class.getResourceAsStream("/textures/icon2.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		game.start();
	}

	public static void stop() {
		running = false;
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
				FPS.ticks = ticks;
				FPS.frames = frames;
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
