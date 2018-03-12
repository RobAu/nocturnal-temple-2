package org.audenaerde.characters;

import java.util.ArrayList;
import java.util.List;

import org.audenaerde.Main;

import javafx.scene.image.Image;

public class Man extends GameCharacter {
	static Image image = new Image(Main.class.getResourceAsStream("/png/walkcycle/BODY_male.png"));
	static Image image_legs = new Image(Main.class.getResourceAsStream("/png/walkcycle/LEGS_pants_greenish.png"));
	
	static Image image_slash     = new Image(Main.class.getResourceAsStream("/png/slash/BODY_human.png"));
	static Image image_slash_legs= new Image(Main.class.getResourceAsStream("/png/slash/LEGS_pants_greenish.png"));
	static Image image_slash_dagger= new Image(Main.class.getResourceAsStream("/png/slash/WEAPON_dagger.png"));

	static List<Image> images = new ArrayList<>();
	static List<Image> images_slash = new ArrayList<>();
	
	static
	{
		images.add(image);
		images.add(image_legs);
		images_slash.add(image_slash);
		images_slash.add(image_slash_legs);
		images_slash.add(image_slash_dagger);
	}
	@Override
	public List<Image> getWalkImages() {
		return images;
	}

	@Override
	public List<Image> getSlashImages() {
		return images_slash;
	}

	
	
}
