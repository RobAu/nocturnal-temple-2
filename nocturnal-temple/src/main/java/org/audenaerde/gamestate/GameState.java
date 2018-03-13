package org.audenaerde.gamestate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.audenaerde.BigMap;
import org.audenaerde.characters.Dummy;
import org.audenaerde.characters.GameCharacter;
import org.audenaerde.characters.Man;
import org.audenaerde.characters.Skeleton;

import javafx.scene.canvas.GraphicsContext;

public class GameState {

	BigMap bigMap = new BigMap();
	List<GameCharacter> allCharacters;

	GameCharacter skeleton = new Skeleton();
	GameCharacter man = new Man();
	GameCharacter dummy = new Dummy();
	
	GameCharacter curCharacter = man;
	
	public GameState() {
		
		allCharacters = new ArrayList<>();
		allCharacters.add(man);
		allCharacters.add(skeleton);
		allCharacters.add(dummy);
	}

	public void drawTo(GraphicsContext gc) {
		
		bigMap.drawTo(gc);
		Collections.sort(allCharacters, (a,b) -> a.getLy() - b.getLy());
		for (GameCharacter c : allCharacters)
		{
				c.draw(gc);
		}
		
	}

	public GameCharacter getCurrentCharacter() {
		return man;
	}

	public void nextCycle() {
		for (GameCharacter c : allCharacters)
		{
			c.nextCycle();
		}
	
	}
}
