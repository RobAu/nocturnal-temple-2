package org.audenaerde.characters;

import java.util.ArrayList;
import java.util.List;

import org.audenaerde.Main;
import org.audenaerde.gamestate.GameState;

import javafx.scene.image.Image;

public class Dummy extends GameCharacter {
	static Image image = new Image(Main.class.getResourceAsStream("/body/male/dummy.png"));

	static List<Image> images = new ArrayList<>();

	static {
		images.add(image);
	}

	public Dummy(GameState state) {
		super(state);
		lx=200;
		ly=200;
	}
	
	@Override
	public List<Image> getImages() {
		return images;
	}

	@Override
	public void nextCycle() {
	
		setAction(Action.REST);
		setDirection(Direction.UP);
		
		super.nextCycle();
	}

}
