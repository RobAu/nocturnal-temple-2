package org.audenaerde.attacks;

import org.audenaerde.characters.GameCharacter;
import org.audenaerde.characters.GameObject;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Attack implements GameObject{

	int lx=150;
	int ly=150;
	int cycle = 0;
	
	GameCharacter owner;
	
	public Attack(GameCharacter gameCharacter) {
		owner = gameCharacter;
	}

	@Override
	public Rectangle2D getCurrentCollisionBox() {
		return new Rectangle2D(lx-5, ly-5, 10, 10);
	}

	@Override
	public void draw(GraphicsContext gc) {
		
		gc.setStroke(Color.RED);
		gc.setLineWidth(2);

		Rectangle2D cbox = getCurrentCollisionBox();
		gc.strokeRect(cbox.getMinX(), cbox.getMinY(), cbox.getWidth(), cbox.getHeight());
	}

	@Override
	public void nextCycle() {
		cycle++;
		
	}

	public boolean endOfLife() {
		return cycle>=getMaxCycles();
	}

	 abstract protected int getMaxCycles() ;

}
