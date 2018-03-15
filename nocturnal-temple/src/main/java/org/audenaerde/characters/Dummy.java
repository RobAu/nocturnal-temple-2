package org.audenaerde.characters;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.audenaerde.Main;
import org.audenaerde.gamestate.GameState;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Dummy extends GameCharacter {
	
	static URL soundSourceResource = Main.class.getResource("/spin.wav");
	static AudioClip spinSound = new AudioClip(soundSourceResource.toExternalForm());
	
	static Image image = new Image(Main.class.getResourceAsStream("/body/male/dummy.png"));

	static List<Image> images = new ArrayList<>();

	static {
		images.add(image);
	}

	public Dummy(GameState state) {
		super(state);
	
		setAction(Action.REST);
		setDirection(Direction.UP);
	
		lx=200;
		ly=200;
	}
	public Rectangle2D getCollisionBox(int px, int py) {
		return new Rectangle2D(px + 12, py + 48, 64 - 12 * 2, 16);
	}
	
	@Override
	public void setAction(Action a) {
		if (a==Action.WALK_IN_PLACE)
			spinSound.play();
		super.setAction(a);
	}
	
	@Override
	public List<Image> getImages() {
		return images;
	}

	@Override
	public void nextCycle() {
	
		
		super.nextCycle();
	}

}
