package snake;

import java.awt.Color;

public class Tile {

	private int x;
	private int y;
	private int width;
	private int height;
	private final Color color;

	public Tile(int x, int y, int width, int height, Color color) {
		this.width = width;
		this.height = height;
		this.color = color;
		this.setX(x);
		this.setY(y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Tile other = (Tile) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tile [x=" + x + ", y=" + y + "]";
	}

	public int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = modBound(x, width);
	}

	public int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = modBound(y, height);
	}

	public void move(Direction direction) {
		int x = getX();
		int y = getY();
		switch (direction) {
		case UP:
			setY(y - 1);
			break;
		case DOWN:
			setY(y + 1);
			break;
		case LEFT:
			setX(x - 1);
			break;
		case RIGHT:
			setX(x + 1);
			break;
		}
	}

	public Tile clone() {
		return new Tile(getX(), getY(), width, height, color);
	}

	private int modBound(int val, int bound) {
		bound = bound - 1;
		return (bound + val) % bound;
	}

	public Color getColor() {
		return color;
	}

}
