package org.audenaerde;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tiled.core.Map;
import tiled.core.TileLayer;
import tiled.io.TMXMapReader;

public class BigMap {

	int w = 32;
	int h = 32;
	Map testMap;

	java.util.Map<Integer, Image> tileCache= new HashMap<>();
	
	public BigMap() {
		try {
			testMap = new TMXMapReader().readMap("/home/raudenaerde/test.tmx");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void drawTo(GraphicsContext gc) {
		for (int y = 0; y < h; y++)
			for (int x = 0; x < w; x++) {
				TileLayer later = (TileLayer) testMap.getLayer(0);
				tiled.core.Tile t = later.getTileAt(x, y);
				
				Image image = tileCache.get(t.getId());
				if (image == null)
				{
					image = SwingFXUtils.toFXImage((BufferedImage) t.getImage(), null);
					tileCache.put(t.getId(), image);
				}
				gc.drawImage(image, 0, 0, t.getWidth(), t.getHeight(), x*t.getWidth(), y*t.getHeight(), t.getWidth(),t.getHeight());

				
			}
	}

	public void setTile(int x, int y, Tile tile) {

	}
}
