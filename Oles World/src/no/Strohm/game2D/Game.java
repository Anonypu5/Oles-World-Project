package no.Strohm.game2D;

import no.Strohm.game2D.Multiplayer.Client;
import no.Strohm.game2D.Multiplayer.Server;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.state.State;
import no.Strohm.game2D.util.FPS;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * Created by Ole on 13/12/13.
 */
public class Game extends Canvas implements Runnable {

	public static final String TITLE = "Ole's World";
	public static String VERSION;
	public static boolean DEV = false;
	public static Server server;
	public static ServerSocket isRunningSockets;
	public static Client client;
	public static boolean Online = false;
	public static int SCALE = 4;
	public static Dimension windowDimension = new Dimension(0, 0);
	public static Dimension screenDimension = new Dimension(0, 0);
	public static int mapHeight = 10, mapWidth = 10;
	private static boolean running = false;

	private static final int DIM_FULLSCREEN = 0;
	private static final int DIM_SMALLEST = 1;
	private static final int DIM_ALMOST_SMALLEST = 2;
	private static final Dimension[] dimensions = {
			new Dimension(1920, 1080),
			new Dimension(800, 450),
			new Dimension(1280, 720)
	};

	private JFrame frame;
	private InputHandler input;
	private Screen screen;
	private BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

	private boolean fullscreen;

	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	public static void main(String[] args) {
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Game.class.getResourceAsStream("/audio/trumpadole.wav"));
                    clip.open(inputStream);
                    FloatControl gainControl =
                            (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(-20f);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
		String f = System.getProperty("user.home") + "\\AppData\\roaming\\.Ole-s-World";
		if (!new File(f).exists()) {
			new File(f).mkdir();
		}
		f += "\\settings.txt";
		if (new File(f).exists()) {
			System.out.println("Shit is there: " + f);
			try {
				Scanner s = new Scanner(new File(f));
				if (s.nextLine().equals("1")) {
					System.out.println("Shit got changed: " + f);
					DEV = true;
				} else {
					DEV = false;
				}
				String v = s.nextLine();
				if (v.equals("1")) {
					SCALE = 1;
				} else if (v.equals("2")) {
					SCALE = 2;
				} else if (v.equals("3")) {
					SCALE = 3;
				} else {
					SCALE = 4;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!DEV) {
			try {
				isRunningSockets = new ServerSocket(20163);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Seems like Oles World is already running", "ERROR: 2", JOptionPane.PLAIN_MESSAGE);
				System.exit(2);
			}
		}
		try {
			VERSION = DEV ? new Scanner(new File(Game.class.getResource("/info.txt").toURI())).nextLine() : new Scanner(new File(Game.class.getResource("/info.txt").toURI())).nextLine().substring(0, 4);
		} catch (Exception e) {
			e.printStackTrace();
		}

		windowDimension.setSize(dimensions[DIM_ALMOST_SMALLEST]);

		screenDimension.setSize(dimensions[DIM_ALMOST_SMALLEST].getWidth() / SCALE, dimensions[DIM_ALMOST_SMALLEST].getHeight() / SCALE);

		Game game = new Game();
		game.setPreferredSize(dimensions[DIM_ALMOST_SMALLEST]);
		game.setMinimumSize(dimensions[DIM_SMALLEST]);
		game.setMaximumSize(dimensions[DIM_FULLSCREEN]);

		game.frame = new JFrame(Game.TITLE);
		game.frame.setUndecorated(false);
		game.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.frame.setResizable(false);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		//game.setFullscreen(true);
		game.setWindowedFullscreen();

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
		screen = new Screen(screenDimension);
		img = new BufferedImage((int) screenDimension.getWidth(), (int) screenDimension.getHeight(), BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

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
		getGraphics().drawImage(img, 0, 0, (int) windowDimension.getWidth(), (int) windowDimension.getHeight(), null);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);

	}

	private void tick() {
		input.tick();
		if (input.toggleFullscreen) {
			setFullscreen(!fullscreen);
		}
		updateBounds();
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

	private void updateBounds() {
		if (frame.getWidth() != windowDimension.getWidth() || frame.getHeight() != windowDimension.getHeight()) {
			frame.setSize(windowDimension);
		}
		if(screen.w != screenDimension.getHeight() || screen.h != screenDimension.getHeight()) {
			img = new BufferedImage((int) screenDimension.getWidth(), (int) screenDimension.getHeight(), BufferedImage.TYPE_INT_RGB);
			pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
			screen = new Screen(screenDimension);
		}
	}

	public void setFullscreen(boolean fullscreen) {
		if (fullscreen) {
			device.setFullScreenWindow(frame);
			frame.setUndecorated(true);
		} else {
			device.setFullScreenWindow(null);
			frame.setUndecorated(false);
		}
		this.fullscreen = fullscreen;
	}

	public void setWindowedFullscreen() {
		fullscreen = true;
		//frame.setUndecorated(true);
		device.setFullScreenWindow(null);
		screenDimension.setSize(dimensions[DIM_FULLSCREEN]);
		frame.setSize(dimensions[DIM_FULLSCREEN]);
		frame.setLocationRelativeTo(null);
	}

}
