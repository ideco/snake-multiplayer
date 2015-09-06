package snake;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

@SuppressWarnings("serial")
public abstract class DoubleBuffer extends Canvas {

	private int bufferWidth;
	private int bufferHeight;
	private Image bufferImage;
	private Graphics bufferGraphics;

	public void paint(Graphics g) {
		if (bufferWidth != getSize().width || bufferHeight != getSize().height
				|| bufferImage == null || bufferGraphics == null)
			resetBuffer();

		if (bufferGraphics != null) {
			bufferGraphics.clearRect(0, 0, bufferWidth, bufferHeight);
			paintBuffer(bufferGraphics);
			g.drawImage(bufferImage, 0, 0, this);
		}
	}

	private void resetBuffer() {
		bufferWidth = getSize().width;
		bufferHeight = getSize().height;
		if (bufferGraphics != null) {
			bufferGraphics.dispose();
			bufferGraphics = null;
		}
		if (bufferImage != null) {
			bufferImage.flush();
			bufferImage = null;
		}
		System.gc();
		bufferImage = createImage(bufferWidth, bufferHeight);
		bufferGraphics = bufferImage.getGraphics();
	}

	abstract void paintBuffer(Graphics g);

}
