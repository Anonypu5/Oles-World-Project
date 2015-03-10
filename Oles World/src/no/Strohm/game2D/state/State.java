package no.Strohm.game2D.state;

import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.graphics.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ole on 14/12/13.
 */
public abstract class State {

	public static final int startId = 0;
	public static final int gameId = 1;
	public static final int pauseId = 2;
	public static final int instructionsId = 3;
	public static final int aboutId = 4;
	public static final int settingsId = 5;
	public static final int exitId = 6;
	public static final int multiplayerId = 7;
	public static final int joinServerId = 8;
	public static final int startServerId = 9;

	public static int lastState = 0;
	public static List<State> states = new ArrayList<State>();
	private static int curState = 0;
	public int statePos;
	protected SpriteSheet sheet = SpriteSheet.effects;
	protected InputHandler input;
	private int id;

	public State(int id, InputHandler input) {
		this.id = id;
		this.input = input;
		addState(this);
	}

	public static void init(InputHandler input) {
		new StateMenuMain(input);
		new StateGame(input);
		new StateMenuPause(input);
		new StateMenuInstructions(input);
		new StateMenuAbout(input);
		new StateMenuSettings(input);
		new StateMenuExit(input);
		new StateMenuMultiplayer(input);
		new StateMenuStartServer(input);
		new StateMenuJoinServer(input);
	}

	public static void addState(State state) {
		state.statePos = states.size();
		states.add(state);
	}

	public static void setState(int id) {
		for (int i = 0; i < getStatesLength(); i++) {
			State state = states.get(i);
			if (state.id == id) {
				lastState = curState;
                states.get(i).start();
				curState = i;
				return;
			}
		}
		System.out.println("Couldn't locate State with ID: " + id);
	}

	public static State getCurState() {
		return states.get(curState);
	}

	public static int getCurStateID() {
		return curState;
	}

	public static State getState(int index) {
		return states.get(index);
	}

	public static int getStatesLength() {
		return states.size();
	}

    public void start(){}

	public abstract void tick();

	public abstract void render(Screen screen);

}
