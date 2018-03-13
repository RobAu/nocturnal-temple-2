package org.audenaerde.gamestate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.audenaerde.BigMap;
import org.audenaerde.characters.Dummy;
import org.audenaerde.characters.GameCharacter;
import org.audenaerde.characters.Man;
import org.audenaerde.characters.Skeleton;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class GameState {

	BigMap bigMap = new BigMap();
	List<GameCharacter> allCharacters;

	Rectangle2D screenBox = new Rectangle2D(0, 0, 640, 480);

	GameCharacter skeleton = new Skeleton(this);
	GameCharacter man = new Man(this);
	GameCharacter dummy = new Dummy(this);

	GameCharacter curCharacter = man;
	int tileSize = 32;
	
	private Debug debug = new Debug();

	public GameState() {

		allCharacters = new ArrayList<>();
		allCharacters.add(man);
		allCharacters.add(skeleton);
		allCharacters.add(dummy);
	}

	public void drawTo(GraphicsContext gc) {

		if (debug.isTerrain())
		{
			bigMap.drawTo(gc);
		}
		Collections.sort(allCharacters, (a, b) -> a.getLy() - b.getLy());
		for (GameCharacter c : allCharacters) {
			c.draw(gc);
		}

	}

	public GameCharacter getCurrentCharacter() {
		return man;
	}

	public void nextCycle() {
		for (GameCharacter c : allCharacters) {
			c.nextCycle();
		}

	}

	public Rectangle2D getScreenBox() {
		return screenBox;
	}

	public boolean collidesWithOthers(GameCharacter gameCharacter, Rectangle2D newPosColBox) {
		Set<GameCharacter> others = allCharacters.stream().filter(o -> o!=gameCharacter).collect(Collectors.toSet());
		
		for (GameCharacter o: others)
		{
			if (newPosColBox.intersects(o.getCurrentBox()))
					{
				return true;
					}
		}
		return false;
	}

	public Debug getDebug() {
		// TODO Auto-generated method stub
		return this.debug;
	}

}
