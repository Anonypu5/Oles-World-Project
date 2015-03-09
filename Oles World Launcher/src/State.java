import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ole on 14/12/13.
 */
public abstract class State {

    public static final int startId = 0;
    public static final int settingsId = 1;
    public static final int defId = 2;
    public static final int updateQId = 3;
    public static final int updatingId = 4;
    public static final int devId = 5;

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
        new StateStart(input);
        new StateSettings(input);
        new StateDef(input);
        new StateUpdateQ(input);
        new StateUpdating(input);
        new StateDev(input);
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
