package org.audenaerde.effects;

import org.audenaerde.characters.GameCharacter;
import org.audenaerde.characters.GameObject;
import org.audenaerde.gamestate.GameState;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AmountHit implements GameObject{

	public final static int LENGTH = 10;
	
	int lx = 150;
	int ly = 150;
	int cycle = 0;

	private GameState state;

	private GameCharacter character;
	
	public AmountHit(GameState state, GameCharacter gc) {
		this.state = state;
		this.character = gc;
		Rectangle2D bbox = character.getCurrentCollisionBox();
		
		this.lx = (int) bbox.getMinX();
		this.ly = (int) bbox.getMinY() - 24;
	}
	
	
	@Override
	public Rectangle2D getCurrentCollisionBox() {
		return null;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.RED);
		gc.setFont(Font.font("Ubuntu", FontWeight.EXTRA_BOLD, 26));
		gc.fillText("-10 HP", lx, ly);
		gc.setStroke(Color.BLACK);
		gc.strokeText("-10 HP", lx, ly);
		
	}

	@Override
	public void nextCycle() {
		cycle++;
		ly-=3;
	}

	@Override
	public int getDrawOrderIndex() {
		return ly;
	}


	public boolean endOfLife() {
		return cycle > LENGTH;
	}

}
