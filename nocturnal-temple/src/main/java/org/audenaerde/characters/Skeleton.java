package org.audenaerde.characters;

import java.util.ArrayList;
import java.util.List;

import org.audenaerde.Main;

import javafx.scene.image.Image;

public class Skeleton extends GameCharacter {
	static Image image = new Image(Main.class.getResourceAsStream("/body/male/skeleton.png"));

	static List<Image> images = new ArrayList<>();

	static {
		images.add(image);
	}

	@Override
	public List<Image> getImages() {
		return images;
	}

	@Override
	public void nextCycle() {
	
		setAction(Action.WALK);
		if (lx<0)
		{
			setDirection(Direction.RIGHT);
		}
		if (lx>640)
		{
			setDirection(Direction.LEFT);
		}
		if (ly<0)
		{
			setDirection(Direction.DOWN);
		}
		if (ly>400)
		{
			setDirection(Direction.UP);
		}
		
		super.nextCycle();
	}

}
