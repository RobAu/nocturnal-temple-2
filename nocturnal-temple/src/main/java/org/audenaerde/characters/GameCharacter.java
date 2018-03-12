package org.audenaerde.characters;

import java.util.List;

import org.audenaerde.Main;
import org.audenaerde.characters.GameCharacter.Direction;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameCharacter {

	private static final int CHARACTER_TILE_SIZE = 64;

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

	Direction d = Direction.UP;
	private int cycle = 1;;

	int lx = 100;
	int ly = 100;

	public abstract List<Image> getWalkImages();

	public void nextCycle() {
		System.out.println(cycle);
		cycle = (cycle  % 8) + 1;
		int pixelsPerStep=4;
		switch (d) {
		case DOWN:
			ly+=pixelsPerStep;
			break;
		case LEFT:
			lx-=pixelsPerStep;
			break;
		case RIGHT:
			lx+=pixelsPerStep;
			break;
		case UP:
			ly-=pixelsPerStep;
			break;

		}

	}
	public void setDirection(Direction newDirection) {
		d = newDirection;
		
	}
	public void draw(GraphicsContext gc) {

		int tx = CHARACTER_TILE_SIZE * cycle;
		if (d == Direction.UP || d == Direction.DOWN)
		{
		}
		int ty = CHARACTER_TILE_SIZE * d.getRow();

		for (Image i : getWalkImages())
		{
			gc.drawImage(i, tx, ty, CHARACTER_TILE_SIZE, CHARACTER_TILE_SIZE, lx, ly, CHARACTER_TILE_SIZE,
				CHARACTER_TILE_SIZE);
		}
	}
}
