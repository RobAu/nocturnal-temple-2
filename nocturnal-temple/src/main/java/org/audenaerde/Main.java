package org.audenaerde;

import java.awt.MouseInfo;
import java.awt.Point;
import java.io.FileNotFoundException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class Main extends Application {
	
	
	/**
	 * See: https://opengameart.org/content/rpg-tiles-cobble-stone-paths-town-objects
	 */
	Image image = new Image(getClass().getResourceAsStream("/PathAndObjects.png"));

	int mx =0;
	int my =0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Hello World!");
		Group root = new Group();
		Canvas canvas = new Canvas(400, 400);

		root.getChildren().add(canvas);

		AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				GraphicsContext gc = canvas.getGraphicsContext2D();
				drawShapes(gc);
			}
		};
		Scene s = new Scene(root, 400, 400);

		EventHandler<MouseEvent> movedHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				mx = (int) arg0.getSceneX();
				my= (int) arg0.getSceneY();
			}
		};
		s.setOnMouseMoved(movedHandler);
		primaryStage.setScene(s);
		primaryStage.show();

		timer.start();
	}

	private void drawShapes(GraphicsContext gc) {

		final Point p = MouseInfo.getPointerInfo().getLocation();

		gc.drawImage(image, 0, 0);
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(1);
		
		int tileSize = 32;
		int tx = (mx/tileSize)*tileSize;
		int ty = (my/tileSize)*tileSize;

		gc.strokeRect(tx, ty, tileSize, tileSize);

	}

	public static void main(String[] args) {
		launch(args);
	}
}
