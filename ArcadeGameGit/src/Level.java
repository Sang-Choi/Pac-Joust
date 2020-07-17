import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * 
 * @author chois3, julianda, and jinm
 * 
 *         The level class takes in the different text files and scans for
 *         different letters, and forms tiles objects based off this
 *         information. It holds an ArrayList of letters, and generates floors,
 *         monsters, player, or nothing depending on what letter is stored. We
 *         have different text files that are used to build each level.
 *
 */
public class Level {

	private int levelNum;
	private ArrayList<LevelTile> tiles;
	private ArrayList<Enemy> monsters;
	private GameComponent component;
	private int tileSize = 50;
	private int playerX;
	private int playerY;

	public Level(int levelNum, GameComponent component) {
		this.component = component;
		this.levelNum = levelNum;
		this.tiles = new ArrayList<LevelTile>();
		this.monsters = new ArrayList<Enemy>();
		readFile();
	}

	/**
	 * Returns the list of floor tiles in the level.
	 * 
	 * @return
	 */
	public ArrayList<LevelTile> getTiles() {
		return this.tiles;
	}

	/**
	 * Reads a file and adds shapes according to the text in the file. In our text
	 * files, we have set 'F' to be a 'floor' LevelTile and 'A' to be 'air', which
	 * is nothing. 'P' is player position, 'M' is a Monster1, 'N' is a Monster2, 'O'
	 * is a Monster3, 'Q' is a Monster4, and 'B' is the boss.
	 */
	public void readFile() {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("level" + this.levelNum + ".txt"));
		} catch (FileNotFoundException e) {
			System.out.println("level" + this.levelNum + ".txt not found");
			return;
		}
		int row = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == 'F') {
					this.tiles.add(new LevelTile(i * tileSize, row * tileSize, tileSize, tileSize, this.component));
				} else if (line.charAt(i) == 'P') {
					this.playerX = i * tileSize;
					this.playerY = row * tileSize;
				} else if (line.charAt(i) == 'M') {
					monsters.add(new Monster1(i * tileSize, row * tileSize - 30, this.component,
							this.component.getPlayer()));
				} else if (line.charAt(i) == 'N') {
					monsters.add(new Monster2(i * tileSize, row * tileSize - 30, this.component,
							this.component.getPlayer()));
				} else if (line.charAt(i) == 'B') {
					monsters.add(
							new Boss(i * tileSize, row * tileSize - 30, this.component, this.component.getPlayer()));
				} else if (line.charAt(i) == 'O') {
					monsters.add(new Monster3(i * tileSize, row * tileSize - 30, this.component,
							this.component.getPlayer()));
				} else if (line.charAt(i) == 'Q') {
					monsters.add(new Monster4(i * tileSize, row * tileSize - 30, this.component,
							this.component.getPlayer()));
				}
			}
			row++;
		}
		scanner.close();
	}

	/**
	 * Draws this level's background and platform tiles.
	 * 
	 * @param g2
	 */
	public void drawOn(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, this.component.getWidth(), this.component.getHeight());
		for (LevelTile tile : tiles) {
			tile.drawOn(g2);
		}
	}

	/**
	 * Returns the position that the player should start in.
	 * 
	 * @return
	 */
	public int playerX() {
		return this.playerX;
	}

	/**
	 * Returns the position that the player should start in.
	 * 
	 * @return
	 */
	public int playerY() {
		return this.playerY;
	}

	/**
	 * Returns the list of monsters per the level.
	 * 
	 * @return
	 */
	public ArrayList<Enemy> getMonsters() {
		return this.monsters;
	}
}
