package org.audenaerde.characters;

import java.util.ArrayList;
import java.util.List;

import org.audenaerde.Main;

import javafx.scene.image.Image;

public class Man extends GameCharacter {
	static Image image = new Image(Main.class.getResourceAsStream("/png/walkcycle/BODY_male.png"));
	static Image image_legs = new Image(Main.class.getResourceAsStream("/png/walkcycle/LEGS_pants_greenish.png"));
	

	static List<Image> images = new ArrayList<>();
	
	static
	{
		images.add(image);
		images.add(image_legs);
	}
	@Override
	public List<Image> getWalkImages() {
		// TODO Auto-generated method stub
		return images;
	}

//	@Override
//	public List<Image> getSlashImages() {
//		// TODO Auto-generated method stub
//		return images;
//	}

	
}
