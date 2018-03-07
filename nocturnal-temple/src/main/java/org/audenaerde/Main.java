package org.audenaerde;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
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

	int tileSize = 32;

	int mx = 0;
	int my = 0;
	int tx = (mx / tileSize);
	int ty = (my / tileSize);

	@Override
	public void start(Stage primaryStage) throws Exception {

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

		FlowPane flow = new FlowPane();
		flow.setPrefWidth(300);
		flow.setVgap(8);
		flow.setHgap(4);
		flow.setPrefWrapLength(300); // preferred width = 300

		
		for (int i = 0; i < 10; i++) {
			ImageView iv3 = new ImageView();
			iv3.setImage(image);
			Rectangle2D viewportRect = new Rectangle2D(32*i, 32, 32, 32);
			iv3.setViewport(viewportRect);
			flow.getChildren().add(new Button(String.valueOf(i), iv3));
		}

		borderPane.setLeft(flow);

		// Put canvas in the center of the window (*)
		Canvas canvas1 = new Canvas(400, 400);
		wrapperPane.getChildren().add(canvas1);
		// Bind the width/height property so that the size of the Canvas will be
		// resized as the window is resized
		canvas1.widthProperty().bind(borderPane.widthProperty());
		canvas1.heightProperty().bind(borderPane.heightProperty());

		primaryStage.setTitle("Hello World!");

		// AnimationTimer timer = new AnimationTimer() {
		//
		// @Override
		// public void handle(long arg0) {
		//
		// }
		// };
		Scene s = new Scene(borderPane, 850, 450);

		s.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
			switch (event.getCode()) {
			case DIGIT1:
				if (event.isControlDown())
					System.out.println("Set 1 to tile");
				;
				break;

			case UP:
				event.consume();
				ty--;
				break;

			case LEFT:
				event.consume();
				tx--;
				break;

			case RIGHT:
				event.consume();
				tx++;
				break;

			case DOWN:
				event.consume();
				ty++;
				break;
			}

			GraphicsContext gc = canvas1.getGraphicsContext2D();

			drawShapes(gc, canvas1.getWidth(), canvas1.getHeight());
		});
		s.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
			}
		});

		GraphicsContext gc = canvas1.getGraphicsContext2D();

		drawShapes(gc, canvas1.getWidth(), canvas1.getHeight());

		EventHandler<MouseEvent> movedHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				GraphicsContext gc = canvas1.getGraphicsContext2D();
				mx = (int) arg0.getX();
				my = (int) arg0.getY();
				tx = (mx / tileSize);
				ty = (my / tileSize);
				drawShapes(gc, canvas1.getWidth(), canvas1.getHeight());

			}
		};
		canvas1.setOnMouseMoved(movedHandler);
		Object currentTile = null;
		canvas1.setOnMousePressed(e -> setTile(currentTile));
		primaryStage.setScene(s);
		primaryStage.show();

		drawShapes(gc, canvas1.getWidth(), canvas1.getHeight());

		// timer.start();
	}

	private Object setTile(Object currentTile) {
		System.out.println("Setting tile");
		return null;
	}

	private void drawShapes(GraphicsContext gc, double cw, double ch) {

		gc.clearRect(0, 0, cw, ch);
		gc.drawImage(image, 0, 0);
		gc.drawImage(image, 32, 32, 32, 32, 400, 0, 32, 32);
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(2);

		gc.strokeRect(tx * tileSize, ty * tileSize, tileSize, tileSize);

	}

	public static void main(String[] args) {
		launch(args);
	}
}
