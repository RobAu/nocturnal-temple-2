package org.audenaerde;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tiled.core.Map;
import tiled.core.Tile;
import tiled.core.TileLayer;
import tiled.io.TMXMapReader;

public class BigMap {

	int width;
	int height ;
	Map testMap;

	java.util.Map<Integer, Image> tileCache= new HashMap<>();
	
	public BigMap() {
		try {
			testMap = new TMXMapReader().readMap("src/main/resources/test.tmx");
			width = testMap.getWidth() ;
			height = testMap.getHeight();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void drawTo(GraphicsContext gc) {
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				TileLayer later = (TileLayer) testMap.getLayer(0);
				Tile t = later.getTileAt(x, y);
				
				Image image = tileCache.get(t.getId());
				if (image == null)
				{
					image = SwingFXUtils.toFXImage((BufferedImage) t.getImage(), null);
					tileCache.put(t.getId(), image);
				}
				gc.drawImage(image, 0, 0, t.getWidth(), t.getHeight(), x*t.getWidth(), y*t.getHeight(), t.getWidth(),t.getHeight());

				
			}
	}

	
}
