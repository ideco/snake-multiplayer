package snake;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Fruit extends Tile {

	private final int points;
	private static final List<Color> COLORS = new LinkedList<Color>();

	static {
		COLORS.add(Color.BLUE);
		COLORS.add(Color.MAGENTA);
		COLORS.add(Color.YELLOW);
		COLORS.add(Color.GREEN);
	}

	public static Fruit getNew(int points, Set<Tile> tiles, int width,
			int height) {
		Random random = new Random();
		Fruit fruit = null;
		do {
			fruit = new Fruit(random.nextInt(width), random.nextInt(height),
					width, height, points, COLORS.get(random.nextInt(COLORS
							.size())));
		} while (tiles.contains(fruit));
		return fruit;
	}

	public Fruit(int x, int y, int width, int height, int points, Color color) {
		super(x, y, width, height, color);
		this.points = points;
	}

	public int getPoints() {
		return points;
	}

}
