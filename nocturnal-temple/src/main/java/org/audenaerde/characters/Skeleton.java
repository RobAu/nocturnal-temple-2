package org.audenaerde.characters;

import java.util.ArrayList;
import java.util.List;

import org.audenaerde.Main;

import javafx.scene.image.Image;

public class Skeleton extends GameCharacter {
	static Image image = new Image(Main.class.getResourceAsStream("/body/male/skeleton.png"));

	static List<Image> images = new ArrayList<>();
	
	static
	{
		images.add(image);
	}


	@Override
	public List<Image> getImages() {
		return images;
	}




	
}
