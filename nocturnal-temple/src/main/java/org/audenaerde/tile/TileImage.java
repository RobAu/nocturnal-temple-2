package org.audenaerde.tile;

import org.audenaerde.characters.GameObject;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TileImage implements GameObject {

	Image i;
	int lx;
	int ly;
	private Rectangle2D bbox;
	private int height;

	public TileImage(int lx, int ly, Image i, Rectangle2D rectangle2d, int height) {
		this.lx = lx;
		this.ly = ly;
		this.i =i; 
		this.height=height;
		if (rectangle2d!=null)
		{
			bbox = rectangle2d;
		}
		else
		{
			bbox = new Rectangle2D(lx, ly, 32, 32);
		
		}
	}

	@Override
	public Rectangle2D getCurrentCollisionBox() {

		return bbox;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(i, 0, 0, 32, 32, lx, ly, 32, 32);
	}

	@Override
	public void nextCycle() {
		// no animation yet.
	}
	public int getDrawOrderIndex()
	{
		return (int) getCurrentCollisionBox().getMaxY() + 64 * height;
	}
}
