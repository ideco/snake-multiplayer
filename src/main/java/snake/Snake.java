package snake;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Snake {

	static int[] KEYS_P1 = { KeyEvent.VK_UP, KeyEvent.VK_DOWN,
			KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT };
	static int[] KEYS_P2 = { KeyEvent.VK_W, KeyEvent.VK_S,
		KeyEvent.VK_A, KeyEvent.VK_D};
	static int [][] KEYS = {KEYS_P1, KEYS_P2};
	
	static Direction[] DIRECTION = {Direction.RIGHT, Direction.LEFT};
	static Color[] COLORS = {Color.RED, Color.YELLOW};
	
	
	public static List<Snake> newSnakes(int nrSnakes, int width, int height) {
		if (nrSnakes > KEYS.length) {
			throw new IllegalArgumentException(String.format("Max. number of players is: %s", KEYS.length));
		}
		List<Snake> snakes = new LinkedList<Snake>();
		for (int i = 0; i <nrSnakes; i++) {
			snakes.add(new Snake(i * (width -1), i * (height -3), DIRECTION[i], width, height, KEYS[i], COLORS[i]));
		}
		return snakes;
	}

	LinkedList<Tile> segments = new LinkedList<Tile>();
	private final int width;
	private final int height;
	private int score;
	private Direction direction;
	private final DirectionKeyListener keyListener;

	public Snake(int startX, int startY, Direction direction, int width,
			int height, int[] keys, Color color) {
		this.width = width;
		this.height = height;
		this.direction = direction;
		this.keyListener = new DirectionKeyListener(keys);
		segments.add(new Tile(startX, startY, width, height, color));
	}

	public boolean move(LinkedList<Fruit> fruits) {
		Iterator<Tile> iter = segments.iterator();
		Tile segment = iter.next();
		HashSet<Tile> tiles = new HashSet<Tile>();
		Queue<Integer> positions = new LinkedList<Integer>();
		positions.add(segment.getX());
		positions.add(segment.getY());
		segment.move(direction);
		tiles.add(segment);
		while (iter.hasNext()) {
			segment = iter.next();
			positions.add(segment.getX());
			positions.add(segment.getY());
			segment.setX(positions.poll());
			segment.setY(positions.poll());
			if (tiles.contains(segment)) {
				return false;
			}
			tiles.add(segment);
		}

		int indexOfFruit = fruits.indexOf(getHead());
		if (indexOfFruit != -1) {
			Fruit fruit = fruits.get(indexOfFruit);
			fruits.remove(indexOfFruit);
			int x = positions.poll();
			int y = positions.poll();
			segments.add(new Tile(x, y, width, height, fruit.getColor()));
		}
		return true;
	}

	public Set<Tile> getTiles() {
		return new HashSet<Tile>(segments);
	}

	public int getScore() {
		return score;
	}

	public void increaseScore(int points) {
		this.score += points;
	}

	public DirectionKeyListener getKeyListener() {
		return keyListener;
	}
	
	public Tile getHead(){
		return segments.getFirst().clone();
	}

	private final class DirectionKeyListener implements KeyListener {
		private final int up;
		private final int down;
		private final int left;
		private final int right;

		public DirectionKeyListener(int[] keys) {
			super();
			this.up = keys[0];
			this.down = keys[1];
			this.left = keys[2];
			this.right = keys[3];
		}

		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == up) {
				direction = Direction.UP;
			}
			if (e.getKeyCode() == down) {
				direction = Direction.DOWN;
			}
			if (e.getKeyCode() == left) {
				direction = Direction.LEFT;
			}
			if (e.getKeyCode() == right) {
				direction = Direction.RIGHT;
			}
		}
	}

}
