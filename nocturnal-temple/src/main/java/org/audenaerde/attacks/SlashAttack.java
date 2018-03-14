package org.audenaerde.attacks;

import org.audenaerde.characters.GameCharacter;

import javafx.geometry.Rectangle2D;

public class SlashAttack extends Attack {

	public final static int LENGTH = 6;
	
	int width=17;
	int height=20;
	
	public SlashAttack( GameCharacter gameCharacter) {
		super( gameCharacter);
		
		initPos(gameCharacter);

	}
	
	private void initPos(GameCharacter gameCharacter) {
		
		Rectangle2D bbox = gameCharacter.getCurrentCollisionBox();
		switch (gameCharacter.getDirection()) {
		case DOWN:
			lx = (int) (bbox.getMaxX() - width + 4);
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
			lx = (int) (bbox.getMaxX() - width +4);
			ly = (int) bbox.getMinY()-height;
			break;
		}
		
	}

	@Override
	public Rectangle2D getCurrentCollisionBox() {
		return new Rectangle2D(lx, ly, width, height);
	}


	@Override
	protected int getMaxCycles() {
		return LENGTH;
	}

}
