package org.audenaerde;

import java.util.ArrayList;
import java.util.List;

import org.audenaerde.characters.GameCharacter;
import org.audenaerde.characters.GameCharacter.Action;
import org.audenaerde.characters.GameCharacter.Direction;
import org.audenaerde.characters.Man;
import org.audenaerde.characters.Skeleton;

import javafx.animation.AnimationTimer;
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
import javafx.scene.input.KeyCode;
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

	BigMap bigMap = new BigMap();

	Canvas mainMap;
	Canvas sourceTiles;

	public static int cycle = 0;

	Skeleton sk = new Skeleton();
	Man man = new Man();

	List<Tile> hotTiles = new ArrayList<>();
	List<ImageView> viewPorts = new ArrayList<>();
	int tileSize = 32;

	int mx = 0;
	int my = 0;
	int tx = (mx / tileSize);
	int ty = (my / tileSize);
	int sx = (mx / tileSize);
	int sy = (my / tileSize);

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

		sourceTiles = new Canvas(512, 512);

		leftPane.setTop(flow);
		leftPane.setCenter(sourceTiles);

		for (int i = 0; i < 10; i++) {
			hotTiles.add(new Tile(i, 0));
		}
		int index = 0;

		for (Tile t : hotTiles) {
			ImageView iv3 = new ImageView();
			iv3.setImage(image);
			Rectangle2D viewportRect = new Rectangle2D(32 * t.tx, 32 * t.ty, 32, 32);
			viewPorts.add(iv3);
			iv3.setViewport(viewportRect);
			flow.getChildren().add(new Button(String.valueOf(index++), iv3));
		}
		hotTiles.add(new Tile(0, 0));
		ImageView iv3 = new ImageView();
		iv3.setImage(image);
		Rectangle2D viewportRect = new Rectangle2D(32, 32, 32, 32);
		viewPorts.add(iv3);
		iv3.setViewport(viewportRect);
		flow.getChildren().add(new Button(String.valueOf("Mouse"), iv3));

		borderPane.setLeft(leftPane);

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

		GameCharacter curCharacter = man;
		s.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {

			switch (event.getCode()) {
			case DIGIT0:
			case DIGIT1:
			case DIGIT2:
			case DIGIT3:
			case DIGIT4:
			case DIGIT5:
			case DIGIT6:
			case DIGIT7:
			case DIGIT8:
			case DIGIT9:
				int digit = event.getCode().getCode() - KeyCode.DIGIT0.getCode();
				if (event.isControlDown()) {
					hotTiles.set(digit, new Tile(sx, sy));
					Rectangle2D rect = new Rectangle2D(32 * sx, 32 * sy, 32, 32);
					viewPorts.get(digit).setViewport(rect);
					//
					// System.out.println("Set 1 to tile");
				} else {
					bigMap.setTile(tx, ty, hotTiles.get(digit));
				}
				break;

			case UP:
				event.consume();
				curCharacter.setDirection(Direction.UP);
				ty--;
				break;

			case LEFT:
				event.consume();
				curCharacter.setDirection(Direction.LEFT);
				tx--;
				break;

			case RIGHT:
				event.consume();
				curCharacter.setDirection(Direction.RIGHT);
				tx++;
				break;

			case DOWN:
				event.consume();
				curCharacter.setDirection(Direction.DOWN);
				ty++;
				break;

			case SPACE:
				event.consume();
				curCharacter.setAction(Action.SLASH);
				break;
			}
			curCharacter.nextCycle();

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
		sourceTiles.setOnMouseMoved(sourceMovedHandler);
		mainMap.setOnMouseMoved(movedHandler);
		Object currentTile = null;
		mainMap.setOnMousePressed(e -> setTile(hotTiles.get(10)));
		primaryStage.setScene(s);
		primaryStage.show();

		AnimationTimer timer = new AnimationTimer() {
			long last = -1;
			int framecounter =0;
			
			@Override
			public void handle(long arg0) {

				// init
				if (last == -1) {
					last = arg0;
				} else {
					if (arg0 - last > 50_000_000) // 20 ms
					{
						System.out.println(framecounter++);
						curCharacter.nextCycle();
						updateCanvases();
						last = arg0;
					}
				}

			}
		};
		updateCanvases();
		timer.start();
	}

	private void setTile(Tile currentTile) {
		bigMap.setTile(tx, ty, currentTile); // 10 == mouse
		updateCanvases();
	}

	private void updateCanvases() {

		{
			GraphicsContext gc = mainMap.getGraphicsContext2D();
			double cw = mainMap.getWidth();
			double ch = mainMap.getHeight();

			gc.clearRect(0, 0, cw, ch);
			bigMap.drawTo(gc);
			gc.setStroke(Color.BLUE);
			gc.setLineWidth(2);
			gc.strokeRect(tx * tileSize, ty * tileSize, tileSize, tileSize);

			this.sk.draw(gc);
			this.man.draw(gc);
		}
		{
			GraphicsContext gc = sourceTiles.getGraphicsContext2D();
			double cw = sourceTiles.getWidth();
			double ch = sourceTiles.getHeight();
			gc.clearRect(0, 0, cw, ch);
			gc.drawImage(image, 0, 0);
			gc.setStroke(Color.BLUE);
			gc.setLineWidth(2);
			gc.strokeRect(sx * tileSize, sy * tileSize, tileSize, tileSize);

		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
