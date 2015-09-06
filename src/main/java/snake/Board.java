package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

@SuppressWarnings("serial")
public class Board extends DoubleBuffer {

	private static final Color BOARD_BG_COLOR = Color.BLACK;
	private int tileSize;
	private Collection<? extends Tile> tiles = new LinkedList<Tile>();
	

	public Board(int nrTilesX, int nrTilesY, int tileSize) {
		super();
		this.tileSize = tileSize;
		setSize(nrTilesX * tileSize, nrTilesY * tileSize);
		setBackground(BOARD_BG_COLOR);
	}


	public void repaint(Set<Tile> collection) {
		this.tiles = collection;
		repaint();
	}
	
	private void drawTile(Graphics2D graphics2d, Tile tile) {
		Float tileShape = new Rectangle2D.Float(tile.getX() * tileSize, tile.getY()
				* tileSize, tileSize, tileSize);
		graphics2d.setPaint(tile.getColor());
		graphics2d.fill(tileShape);
		graphics2d.setPaint(BOARD_BG_COLOR);
		
	}


	@Override
	void paintBuffer(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		for (Tile tile : tiles) {
			drawTile(graphics2d, tile);
		}
		
	}

}
