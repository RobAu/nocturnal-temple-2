package org.audenaerde.characters;

import java.net.URL;
import java.util.List;

import org.audenaerde.Main;
import org.audenaerde.attacks.SlashAttack;
import org.audenaerde.gamestate.GameState;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

public abstract class GameCharacter implements GameObject {

	static URL soundSourceResource = Main.class.getResource("/knife-slash.wav");
	static AudioClip slashSound = new AudioClip(soundSourceResource.toExternalForm());
	static {
		slashSound.setBalance(0.5);
		
	}
	private static final int CHARACTER_TILE_SIZE = 64;

	public enum Action {
		REST, WALK, SLASH, WALK_IN_PLACE;
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
	private int hitCycle = 0;

	GameState state;

	public GameCharacter(GameState state) {
		this.state = state;
	}

	public int getLx() {
		return lx;
	}

	public int getLy() {
		return ly;
	}

	int lx = 100;
	int ly = 100;

	public abstract List<Image> getImages();

	public void nextCycle() {

		if (action == Action.WALK_IN_PLACE)
		{
			if (walkCycle == 8)
			{
				action = Action.REST;
			}
		}
		if (action == Action.WALK || action == Action.WALK_IN_PLACE) {
			walkCycle = (walkCycle % 8) + 1;
			int pixelsPerStep = (action == Action.WALK) ? 4 : 0;

			// we need to check if we can actually go there
			int nx = lx;
			int ny = ly;

			switch (d) {
			case DOWN:
				ny += pixelsPerStep;
				break;
			case LEFT:
				nx -= pixelsPerStep;
				break;
			case RIGHT:
				nx += pixelsPerStep;
				break;
			case UP:
				ny -= pixelsPerStep;
				break;

			}
			if (isValid(nx, ny)) {
				lx = nx;
				ly = ny;
			}
		}
		if (action == Action.SLASH) {
		
			
			slashCycle++;

			if (slashCycle == SlashAttack.LENGTH) {
				slashCycle = 0;
				action = Action.REST;
			}
		}

	}

	private boolean isValid(int nx, int ny) {

		Rectangle2D colbox = getCollisionBox(nx, ny);
		return state.getScreenBox().contains(colbox) && !state.collidesWithOthers(this, colbox);

	}

	public void setAction(Action a) {
		if (action != Action.SLASH && a == Action.SLASH) {
			slashCycle = 0;
			this.action = Action.SLASH;
			
			//play slash sound
			slashSound.play();
			
			state.addAttack(new SlashAttack(this));
			
		}
		if (action != Action.WALK && a == Action.WALK) {
			walkCycle = 1;
			this.action = Action.WALK;
		}
		if (action != Action.WALK_IN_PLACE && a == Action.WALK_IN_PLACE) {
			walkCycle = 1;
			this.action = Action.WALK_IN_PLACE;
		}
	}

	public void setDirection(Direction newDirection) {
		d = newDirection;

	}

	public Rectangle2D getCollisionBox(int px, int py) {
		return new Rectangle2D(px + 16, py + 32, 64 - 16 * 2, 64 - 32);
	}

	public void draw(GraphicsContext gc) {

		maybeDrawCollisionBox(gc);

		drawSpriteImages(gc);

	}

	private void drawSpriteImages(GraphicsContext gc) {
		int tx = 0;
		int ty = 0;
		if (action == Action.REST) {
			tx = 0;
			ty = CHARACTER_TILE_SIZE * (8 + d.getRow());
		} else if (action == Action.SLASH) {
			tx = CHARACTER_TILE_SIZE * slashCycle;
			ty = CHARACTER_TILE_SIZE * (12 + d.getRow());
		} else if (action == Action.WALK || action == Action.WALK_IN_PLACE) {
			tx = CHARACTER_TILE_SIZE * walkCycle;
			ty = CHARACTER_TILE_SIZE * (8 + d.getRow());
		}

		for (Image i : getImages()) {
			gc.drawImage(i, tx, ty, CHARACTER_TILE_SIZE, CHARACTER_TILE_SIZE, lx, ly, CHARACTER_TILE_SIZE,
					CHARACTER_TILE_SIZE);

		}
	}

	private void maybeDrawCollisionBox(GraphicsContext gc) {
		if (state.getDebug().getCollisionBoxes()) {
			// draw bounding box as well
			gc.setStroke(Color.BLUE);
			gc.setLineWidth(2);

			Rectangle2D cbox = getCollisionBox(lx, ly);
			gc.strokeRect(cbox.getMinX(), cbox.getMinY(), cbox.getWidth(), cbox.getHeight());
		}
	}

	public Rectangle2D getCurrentCollisionBox() {
		return getCollisionBox(lx, ly);
	}

	public Direction getDirection() {
		return d;
	}

	public GameState getGameState() {
		return state;
	}

}
