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

	Canvas mainMap;
	
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

		BorderPane leftPane = new BorderPane();
		
		
		FlowPane flow = new FlowPane();
		flow.setPrefWidth(300);
		flow.setVgap(8);
		flow.setHgap(4);
		flow.setPrefWrapLength(300); // preferred width = 300

		leftPane.setTop(flow);
		
		for (int i = 0; i < 10; i++) {
			ImageView iv3 = new ImageView();
			iv3.setImage(image);
			Rectangle2D viewportRect = new Rectangle2D(32*i, 32, 32, 32);
			iv3.setViewport(viewportRect);
			flow.getChildren().add(new Button(String.valueOf(i), iv3));
		}

		borderPane.setLeft(leftPane);

		Canvas sourceTiles = new Canvas();
		
		
		// Put canvas in the center of the window (*)
		mainMap = new Canvas(400, 400);
		wrapperPane.getChildren().add(mainMap);
		// Bind the width/height property so that the size of the Canvas will be
		// resized as the window is resized
		mainMap.widthProperty().bind(borderPane.widthProperty());
		mainMap.heightProperty().bind(borderPane.heightProperty());

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

			updateCanvases();
		});
		s.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
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
		mainMap.setOnMouseMoved(movedHandler);
		Object currentTile = null;
		mainMap.setOnMousePressed(e -> setTile(currentTile));
		primaryStage.setScene(s);
		primaryStage.show();

		updateCanvases();

		// timer.start();
	}

	private Object setTile(Object currentTile) {
		System.out.println("Setting tile");
		return null;
	}

	private void updateCanvases() {

		GraphicsContext gc = mainMap.getGraphicsContext2D();
		double cw =  mainMap.getWidth(); 
		double ch = mainMap.getHeight();
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
