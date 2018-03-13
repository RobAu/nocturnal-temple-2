package org.audenaerde.characters;

import java.util.ArrayList;
import java.util.List;

import org.audenaerde.Main;
import org.audenaerde.gamestate.GameState;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Man extends GameCharacter {
	static Image image = new Image(Main.class.getResourceAsStream("/body/male/light.png"));
	static Image image_legs = new Image(Main.class.getResourceAsStream("/legs/pants/male/red_pants_male.png"));
	static Image image_dagger = new Image(Main.class.getResourceAsStream("/weapons/right hand/male/dagger_male.png"));

	static List<Image> images = new ArrayList<>();
	static List<Image> images_slash = new ArrayList<>();

	static {
		images.add(image);
		images.add(image_legs);
		images.add(image_dagger);
	}

	public Man(GameState state) {
		super(state);
		this.lx=400;
	}

	@Override
	public List<Image> getImages() {
		return images;
	}

	public void draw(GraphicsContext gc) {
		super.draw(gc);
//		System.out.println(getCollisionBox(lx, ly));
	}
}
