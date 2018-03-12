package org.audenaerde;

import javafx.scene.canvas.GraphicsContext;

public class BigMap {

	int w = 32;
	int h = 32;

	Tile[][] tiles = new Tile[w][h];

	public BigMap() {
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				tiles[x][y] = new Tile(1, 11);
			}
		}
	}

	public void drawTo(GraphicsContext gc) {
		for (int y = 0; y < h; y++)
			for (int x = 0; x < w; x++) {
				tiles[x][y].drawTo(gc, x, y);
			}
	}

	public void setTile(int x, int y, Tile tile) {
		tiles[x][y] = tile;
	}
}
