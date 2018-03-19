package org.audenaerde.characters;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public interface GameObject {
	public Rectangle2D getCurrentCollisionBox();
	
	public void draw(GraphicsContext gc);
	
	public void nextCycle() ;
	
	public int getDrawOrderIndex();
}
