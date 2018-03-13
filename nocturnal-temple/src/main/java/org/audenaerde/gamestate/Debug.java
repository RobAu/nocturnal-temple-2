package org.audenaerde.gamestate;

public class Debug {

	private boolean cboxes = false;

	public void toggleCollisionBoxes() {
		this.cboxes = !this.cboxes;
		
	}

	public boolean getCollisionBoxes() {
		return cboxes;
	}
	
}
