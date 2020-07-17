import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author chois3, julianda, and jinm This class allows for constant updates to
 *         the frame, and is the action listener for the timer.
 *
 */
public class GameAdvanceListener implements ActionListener {

	private GameComponent gameComponent;

	public GameAdvanceListener(GameComponent comp) {
		this.gameComponent = comp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		advanceOneTick();
	}

	/*
	 * Advances the game and passes a timer tick for game component to use for
	 * spawning bullets.
	 */
	public void advanceOneTick() {
		this.gameComponent.updateState();
	}
}
