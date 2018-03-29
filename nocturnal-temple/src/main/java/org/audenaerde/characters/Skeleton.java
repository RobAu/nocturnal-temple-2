package org.audenaerde.characters;

import java.util.ArrayList;
import java.util.List;

import org.audenaerde.Main;
import org.audenaerde.characters.GameCharacter.Action;
import org.audenaerde.gamestate.GameState;
import org.audenaerde.sounds.Sounds;

import javafx.scene.image.Image;

public class Skeleton extends GameCharacter {


	static Image image = new Image(Main.class.getResourceAsStream("/body/male/skeleton.png"));

	static List<Image> images = new ArrayList<>();

	static {
		images.add(image);
	}
	
	public Skeleton(GameState state) {
		super(state);
	}
	
	@Override
	public List<Image> getImages() {
		return images;
	}
	
	@Override
	public void setAction(Action a) {
		if (a==Action.DIE)
			Sounds.COLLAPSE.play();
		super.setAction(a);
	}

	@Override
	public void nextCycle() {
		
		if (action == Action.REST)
		{
			setAction(Action.WALK);
		}
		
		
//		setAction(Action.WALK);
//		if (lx<0)
//		{
//			setDirection(Direction.RIGHT);
//		}
//		if (lx>640)
//		{
//			setDirection(Direction.LEFT);
//		}
//		if (ly<0)
//		{
//			setDirection(Direction.DOWN);
//		}
//		if (ly>250)
//		{
//			setDirection(Direction.UP);
//		}
		
		super.nextCycle();
	}

}
