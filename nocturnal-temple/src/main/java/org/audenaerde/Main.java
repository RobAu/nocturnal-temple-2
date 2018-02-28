package org.audenaerde;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
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
	Image image = new Image(getClass().getResourceAsStream("/PathAndObjects.png"));

	int mx = 0;
	int my = 0;

	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane borderPane = new BorderPane();

		// Put menu bar on the top of the window
		MenuBar menuBar = new MenuBar(new Menu("File"), new Menu("Edit"), new Menu("Help"));
		borderPane.setTop(menuBar);

		// Put canvas in the center of the window (*)
		Canvas canvas = new Canvas(400, 400);
		borderPane.setCenter(canvas);
		// Bind the width/height property so that the size of the Canvas will be
		// resized as the window is resized
		canvas.widthProperty().bind(borderPane.widthProperty());
		canvas.heightProperty().bind(borderPane.heightProperty());

		primaryStage.setTitle("Hello World!");

		AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				GraphicsContext gc = canvas.getGraphicsContext2D();
				drawShapes(gc);
			}
		};
		Scene s = new Scene(borderPane, 850, 450);

		EventHandler<MouseEvent> movedHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				mx = (int) arg0.getSceneX();
				my = (int) arg0.getSceneY();
			}
		};
		s.setOnMouseMoved(movedHandler);
		primaryStage.setScene(s);
		primaryStage.show();

		timer.start();
	}

	private void drawShapes(GraphicsContext gc) {

		gc.clearRect(0, 0, 400, 400);
		gc.drawImage(image, 0, 0);
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(2);

		int tileSize = 32;
		int tx = (mx / tileSize) * tileSize;
		int ty = (my / tileSize) * tileSize;

		gc.strokeRect(tx, ty, tileSize, tileSize);

	}

	public static void main(String[] args) {
		launch(args);
	}
}
