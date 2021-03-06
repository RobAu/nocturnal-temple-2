package org.audenaerde.gamestate;

public class Debug {

	private boolean cboxes = false;
	private boolean aboxes = false;

	private boolean terrain = true;
	private boolean walkableTerrain = false;
	
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
	public void toggleAttackBoxes() {
		this.aboxes = !this.aboxes;
	}

	public boolean getAttackBoxes() {
		return aboxes;
	}

	public void toggleWalkableTerrain() {
		this.walkableTerrain = !this.walkableTerrain;
	}


	public boolean isWalkableTerrain() {
		return walkableTerrain;
	}
	
}
