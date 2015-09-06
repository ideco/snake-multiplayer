package snake;

import java.awt.Container;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class Game extends JFrame {

	private static final int WIDTH = 80;
	private static final int HEIGHT = 40;
	private static final int TILE_SIZE = 20;
	private static final int NR_FRUITS = 2;
	private Board board;

	public Game() {
		setSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
		setResizable(false);
	}

	public void start() {
		Container contentPane = getContentPane();
		JLayeredPane layeredPane = new JLayeredPane();
		board = new Board(WIDTH, HEIGHT, TILE_SIZE);
		layeredPane.add(board, 0);
		contentPane.add(layeredPane);
		setVisible(true);
		board.createBufferStrategy(4);
		run();
	}

	private void run() {
		HashSet<Tile> tiles = new HashSet<Tile>();
		List<Snake> snakes = Snake.newSnakes(2, WIDTH, HEIGHT);
		LinkedList<Fruit> fruits = new LinkedList<Fruit>();

		for (Snake snake : snakes) {
			addKeyListener(snake.getKeyListener());
			board.addKeyListener(snake.getKeyListener());
			addTiles(tiles, snake);
		}

		fruits.add(Fruit.getNew(100, tiles, WIDTH, HEIGHT));
		while (!snakes.isEmpty()) {
			tiles = new HashSet<Tile>();
			moveSnakes(snakes, fruits);
			removeCollidedSnakes(snakes);
			addSnakeTiles(tiles, snakes);
			addFruitTiles(tiles, fruits);
			board.repaint(tiles);
			sleep(100);
		}
	}

	private void sleep(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void addFruitTiles(HashSet<Tile> tiles, LinkedList<Fruit> fruits) {
		int eatenFruits = NR_FRUITS - fruits.size();
		tiles.addAll(fruits);
		for (int i = 0; i < eatenFruits; i++) {
			Fruit fruit = Fruit.getNew(100, tiles, WIDTH, HEIGHT);
			fruits.add(fruit);
			tiles.add(fruit);
		}
	}

	private void addSnakeTiles(HashSet<Tile> tiles, List<Snake> snakes) {
		for (Snake snake : snakes) {
			tiles.addAll(snake.getTiles());
		}
	}

	private void removeCollidedSnakes(List<Snake> snakes) {
		List<Snake> toRemove = new LinkedList<Snake>();	
		for (Snake first : snakes) {
			for (Snake other : snakes) {
				if (first != other && other.getTiles().contains(first.getHead())) {
					toRemove.add(first);
				}
			
			}
		}
		for (Snake snake : toRemove) {
			snakes.remove(snake);
		}
	}

	private void moveSnakes(List<Snake> snakes, LinkedList<Fruit> fruits) {
		for (Snake snake : snakes) {
			if(!snake.move(fruits)) {
				snakes.remove(snake);
			}
		}
	}

	private boolean addTiles(HashSet<Tile> tiles, Snake snake) {
		Set<Tile> snakeTiles = snake.getTiles();
		for (Tile snakeTile : snakeTiles) {
			if (tiles.contains(snakeTile)) {
				return false;
			}
		}
		tiles.addAll(snakeTiles);
		return true;
	}

}
