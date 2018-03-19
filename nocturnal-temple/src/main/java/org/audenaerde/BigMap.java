package org.audenaerde;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Properties;

import org.audenaerde.gamestate.GameState;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tiled.core.Map;
import tiled.core.Tile;
import tiled.core.TileLayer;
import tiled.io.TMXMapReader;

public class BigMap {

	int width;
	int height;
	Map testMap;

	java.util.Map<Integer, Image> tileCache = new HashMap<>();
	private GameState state;

	public BigMap(GameState state) {
		this.state = state;
		try {
			testMap = new TMXMapReader().readMap("src/main/resources/test.tmx");
			width = testMap.getWidth();
			height = testMap.getHeight();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void drawTo(GraphicsContext gc) {
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				{
					TileLayer layer = (TileLayer) testMap.getLayer(0);
					Tile t = layer.getTileAt(x, y);
					if (t != null) {
						Image image = tileCache.get(10000 + t.getId());
						if (image == null) {
							image = SwingFXUtils.toFXImage((BufferedImage) t.getImage(), null);
							tileCache.put(10000 + t.getId(), image);
						}
						gc.drawImage(image, 0, 0, t.getWidth(), t.getHeight(), x * t.getWidth(), y * t.getHeight(),
								t.getWidth(), t.getHeight());
					}
				}
				{
					TileLayer layer = (TileLayer) testMap.getLayer(1);
					Tile t = layer.getTileAt(x, y);
					if (t != null) {
						Image image = tileCache.get(t.getId());
						if (image == null) {
							image = SwingFXUtils.toFXImage((BufferedImage) t.getImage(), null);
							tileCache.put(t.getId(), image);
						}
						gc.drawImage(image, 0, 0, t.getWidth(), t.getHeight(), x * t.getWidth(), y * t.getHeight(),
								t.getWidth(), t.getHeight());
					}
				}
				if (state.getDebug().isWalkableTerrain()) {
					TileLayer layer = (TileLayer) testMap.getLayer(2);
					Tile t = layer.getTileAt(x, y);
					if (t != null) {
						Image image = tileCache.get(20000 + t.getId());
						if (image == null) {
							image = SwingFXUtils.toFXImage((BufferedImage) t.getImage(), null);
							tileCache.put(20000 + t.getId(), image);
						}
						gc.drawImage(image, 0, 0, t.getWidth(), t.getHeight(), x * t.getWidth(), y * t.getHeight(),
								t.getWidth(), t.getHeight());
					}
				}
			}
	}

	public boolean canWalk(Rectangle2D colbox) {
		TileLayer layer = (TileLayer) testMap.getLayer(2);
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				Tile t = layer.getTileAt(x, y);
				if (t != null) {

					Properties p = t.getProperties();
					Rectangle2D myBox = new Rectangle2D(x * t.getWidth(), y * t.getHeight(), t.getWidth(),
							t.getHeight());

					if (p != null && p.getProperty("orientation")!=null) {
						String orientation = p.getProperty("orientation");
						switch (orientation) {
						case "top":
							myBox = new Rectangle2D(x * t.getWidth(), y * t.getHeight(), t.getWidth(),
									t.getHeight() / 2);
							break;
						case "left":
							myBox = new Rectangle2D(x * t.getWidth(), y * t.getHeight(), t.getWidth() / 2,
									t.getHeight());
							break;
						case "right":
							myBox = new Rectangle2D(x * t.getWidth() + t.getWidth() / 2, y * t.getHeight(),
									t.getWidth() / 2, t.getHeight());
							break;
						case "bottom":
							myBox = new Rectangle2D(x * t.getWidth(), y * t.getHeight() + t.getHeight() / 2,
									t.getWidth(), t.getHeight() / 2);
							break;

						}
					}

					if (myBox.intersects(colbox)) {
						return false;

					}
				}

			}
		return true;
	}

}
