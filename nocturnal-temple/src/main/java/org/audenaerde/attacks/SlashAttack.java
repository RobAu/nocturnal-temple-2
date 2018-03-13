package org.audenaerde.attacks;

import org.audenaerde.characters.GameCharacter;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SlashAttack extends Attack {

	public final static int LENGTH = 6;
	
	int width=15;
	int height=20;
	
	public SlashAttack(GameCharacter gameCharacter) {
		super(gameCharacter);
		
		initPos(gameCharacter);

	}
	
	private void initPos(GameCharacter gameCharacter) {
		
		Rectangle2D bbox = gameCharacter.getCurrentCollisionBox();
		switch (gameCharacter.getDirection()) {
		case DOWN:
			lx = (int) (bbox.getMinX() + width);
			ly = (int) bbox.getMaxY()-height/2;
			break;
		case LEFT:
			lx = (int) (bbox.getMinX() - width);
			ly = (int) bbox.getMinY();
			break;
		case RIGHT:
			lx = (int) (bbox.getMaxX() );
			ly = (int) bbox.getMinY();
			break;
		case UP:
			lx = (int) (bbox.getMaxX() - width);
			ly = (int) bbox.getMinY()-height;
			break;
		}
		
	}

	@Override
	public Rectangle2D getCurrentCollisionBox() {
		return new Rectangle2D(lx, ly, width, height);
	}

	@Override
	public void draw(GraphicsContext gc) {
		
		gc.setStroke(Color.RED);
		gc.setLineWidth(2);

		Rectangle2D cbox = getCurrentCollisionBox();
		gc.strokeRect(cbox.getMinX(), cbox.getMinY(), cbox.getWidth(), cbox.getHeight());
	}

	@Override
	protected int getMaxCycles() {
		return LENGTH;
	}

}
