package org.audenaerde.characters;

import java.util.List;

import org.audenaerde.Main;
import org.audenaerde.characters.GameCharacter.Direction;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameCharacter {

	private static final int CHARACTER_TILE_SIZE = 64;

	public enum Action {
		REST, WALK, SLASH
	};

	public enum Direction {
		UP(0), LEFT(1), DOWN(2), RIGHT(3);

		private int row;

		Direction(int row) {
			this.row = row;

		};

		public int getRow() {
			return this.row;
		}
	};

	Action action = Action.REST;
	Direction d = Direction.UP;
	private int walkCycle = 1;
	private int slashCycle = 0;

	int lx = 100;
	int ly = 100;

	public abstract List<Image> getImages();

	public void nextCycle() {

		if (action == Action.WALK) {
			walkCycle = (walkCycle % 8) + 1;
			int pixelsPerStep = 4;

			switch (d) {
			case DOWN:
				ly += pixelsPerStep;
				break;
			case LEFT:
				lx -= pixelsPerStep;
				break;
			case RIGHT:
				lx += pixelsPerStep;
				break;
			case UP:
				ly -= pixelsPerStep;
				break;

			}
		}
		if (action == Action.SLASH) {
			slashCycle++;

			if (slashCycle == 6) {
				slashCycle = 0;
				action = Action.REST;
			}
		}

	}

	public void setAction(Action a) {
		if (action != Action.SLASH && a == Action.SLASH) {
			slashCycle = 0;
			this.action = Action.SLASH;
		}
		if (action != Action.WALK && a == Action.WALK) {
			walkCycle = 1;
			this.action = Action.WALK;
		}
	}

	public void setDirection(Direction newDirection) {
		d = newDirection;

	}

	public void draw(GraphicsContext gc) {
		int tx = 0;
		int ty = 0;
		if (action == Action.REST) {
			tx = 0;
			ty =  CHARACTER_TILE_SIZE *( d.getRow());;
		} else if (action == Action.SLASH) {
			tx = CHARACTER_TILE_SIZE * slashCycle;
			ty = CHARACTER_TILE_SIZE * (12 + d.getRow());
		} else if (action == Action.WALK) {
			tx = CHARACTER_TILE_SIZE * walkCycle;
			ty =  CHARACTER_TILE_SIZE *(8+ d.getRow());
		}

		for (Image i : getImages()) {

			gc.drawImage(i, tx, ty, CHARACTER_TILE_SIZE, CHARACTER_TILE_SIZE, lx, ly, CHARACTER_TILE_SIZE,
					CHARACTER_TILE_SIZE);
		}

	
	}


}
