package org.audenaerde.gamestate;

public class Debug {

	private boolean cboxes = false;
	private boolean terrain = true;
	
	public boolean isTerrain() {
		return terrain;
	}

	public void setTerrain(boolean terrain) {
		this.terrain = terrain;
	}
	public void toggleTerrain()
	{
		this.terrain = !this.terrain;
	}
	public void toggleCollisionBoxes() {
		this.cboxes = !this.cboxes;
	}

	public boolean getCollisionBoxes() {
		return cboxes;
	}
	
}
