package org.audenaerde;

import java.net.URL;

import org.audenaerde.characters.GameCharacter;
import org.audenaerde.characters.GameCharacter.Action;
import org.audenaerde.characters.GameCharacter.Direction;
import org.audenaerde.gamestate.GameState;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class Main extends Application {

	/**
	 * See:
	 * https://opengameart.org/content/rpg-tiles-cobble-stone-paths-town-objects
	 */

	GameState gameState = new GameState();

	Canvas mainMap;

	public static int cycle = 0;

	int tileSize = 32;

	int mx = 0;
	int my = 0;
	int tx = (mx / tileSize);
	int ty = (my / tileSize);
	int sx = (mx / tileSize);
	int sy = (my / tileSize);
	MediaPlayer player;

	@Override
	public void start(Stage primaryStage) throws Exception {

		URL backgroudMusic = Main.class.getResource("/prisonwalls.mp3");
		Media media = new Media(backgroudMusic.toExternalForm()); // replace /Movies/test.mp3 with your file
		player = new MediaPlayer(media);
		player.play();

		BorderPane borderPane = new BorderPane();
		Pane wrapperPane = new Pane();
		borderPane.setCenter(wrapperPane);
		// Put canvas in the center of the window

		// Put menu bar on the top of the window
		Menu fileMenu = new Menu("File");
		MenuItem saveItem = new MenuItem("Save");
		saveItem.setOnAction(e -> System.out.println(e));
		fileMenu.getItems().addAll(saveItem);

		MenuBar menuBar = new MenuBar(fileMenu, new Menu("Edit"), new Menu("Help"));
		borderPane.setTop(menuBar);

		// Put canvas in the center of the window (*)
		mainMap = new Canvas(640, 480);
		wrapperPane.getChildren().add(mainMap);
		// Bind the width/height property so that the size of the Canvas will be
		// resized as the window is resized
		mainMap.widthProperty().bind(borderPane.widthProperty());
		mainMap.heightProperty().bind(borderPane.heightProperty());

		primaryStage.setTitle("Game thingie");

		// AnimationTimer timer = new AnimationTimer() {
		//
		// @Override
		// public void handle(long arg0) {
		//
		// }
		// };
		Scene s = new Scene(borderPane, 640, 480 + 30);

		GameCharacter curCharacter = gameState.getCurrentCharacter();
		s.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {

			switch (event.getCode()) {

			case UP:
				event.consume();
				curCharacter.setAction(Action.WALK);
				curCharacter.setDirection(Direction.UP);
				ty--;
				break;

			case LEFT:
				event.consume();
				curCharacter.setAction(Action.WALK);
				curCharacter.setDirection(Direction.LEFT);
				tx--;
				break;

			case RIGHT:
				event.consume();
				curCharacter.setAction(Action.WALK);
				curCharacter.setDirection(Direction.RIGHT);
				tx++;
				break;

			case DOWN:
				event.consume();
				curCharacter.setAction(Action.WALK);
				curCharacter.setDirection(Direction.DOWN);
				ty++;
				break;

			case B:
				event.consume();
				gameState.getDebug().toggleCollisionBoxes();
				break;
			case T:
				event.consume();
				gameState.getDebug().toggleTerrain();
				break;
			case A:
				event.consume();
				gameState.getDebug().toggleAttackBoxes();
				break;
			case W:
				event.consume();
				gameState.getDebug().toggleWalkableTerrain();
				break;
			case SPACE:
				event.consume();
				curCharacter.setAction(Action.SLASH);

				break;
			}

		});

		GraphicsContext gc = mainMap.getGraphicsContext2D();

		updateCanvases();

		EventHandler<MouseEvent> movedHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				GraphicsContext gc = mainMap.getGraphicsContext2D();
				mx = (int) arg0.getX();
				my = (int) arg0.getY();
				tx = (mx / tileSize);
				ty = (my / tileSize);
				updateCanvases();

			}
		};
		EventHandler<MouseEvent> sourceMovedHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				GraphicsContext gc = mainMap.getGraphicsContext2D();
				mx = (int) arg0.getX();
				my = (int) arg0.getY();
				sx = (mx / tileSize);
				sy = (my / tileSize);
				updateCanvases();

			}
		};
		mainMap.setOnMouseMoved(movedHandler);
		Object currentTile = null;

		primaryStage.setScene(s);
		primaryStage.show();

		AnimationTimer timer = new AnimationTimer() {
			long last = -1;
			int framecounter = 0;

			@Override
			public void handle(long arg0) {

				// init
				if (last == -1) {
					last = arg0;
				} else {
					if (arg0 - last > 33_000_000) // 50 ms
					{
						// System.out.println(framecounter++);
						gameState.nextCycle();
						updateCanvases();
						last = arg0;
					}
				}

			}
		};
		updateCanvases();
		timer.start();
	}

	private void updateCanvases() {

		{
			GraphicsContext gc = mainMap.getGraphicsContext2D();
			double cw = mainMap.getWidth();
			double ch = mainMap.getHeight();

			gameState.drawTo(gc);

		}

	}

	public static void main(String[] args) throws Exception {

		launch(args);
	}
}
