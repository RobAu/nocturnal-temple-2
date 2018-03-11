package org.audenaerde;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tile {
	
	static Image image = new Image(Tile.class.getResourceAsStream("/PathAndObjects.png"));

	String name;
	int tx;
	int ty;
	
	int tileSize = 32;
	
	Tile(int tx, int ty)
	{
		this.tx = tx;
		this.ty =ty;
	}
	
	public void drawTo(GraphicsContext gc, int x, int y) {

		gc.drawImage(image, tx*tileSize, ty*tileSize, tileSize, tileSize, x*tileSize, y*tileSize, tileSize,tileSize);
		
	}

}
