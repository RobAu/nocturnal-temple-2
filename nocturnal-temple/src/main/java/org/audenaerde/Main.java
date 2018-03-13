package org.audenaerde;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.audenaerde.characters.Dummy;
import org.audenaerde.characters.GameCharacter;
import org.audenaerde.characters.GameCharacter.Action;
import org.audenaerde.characters.GameCharacter.Direction;
import org.audenaerde.characters.Man;
import org.audenaerde.characters.Skeleton;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
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

	URL soundSourceResource = getClass().getResource("/knife-slash.wav");
	AudioClip plonkSound = new AudioClip(soundSourceResource.toExternalForm());

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

	@Override
	public void start(Stage primaryStage) throws Exception {

		plonkSound.setBalance(0.5);
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
		mainMap = new Canvas(400, 400);
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
		Scene s = new Scene(borderPane, 850, 450);
		
		

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

			case SPACE:
				event.consume();
				curCharacter.setAction(Action.SLASH);
				
				//play slash sound
				plonkSound.play();
				 
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
					if (arg0 - last > 50_000_000) // 20 ms
					{
						System.out.println(framecounter++);
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
