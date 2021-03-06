package org.audenaerde.gamestate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.audenaerde.BigMap;
import org.audenaerde.attacks.Attack;
import org.audenaerde.characters.Dummy;
import org.audenaerde.characters.GameCharacter;
import org.audenaerde.characters.GameObject;
import org.audenaerde.characters.GameCharacter.Action;
import org.audenaerde.characters.Man;
import org.audenaerde.characters.Skeleton;
import org.audenaerde.effects.AmountHit;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class GameState {

	BigMap bigMap = new BigMap(this);
	List<GameCharacter> allCharacters;
	List<Attack> attacks;
	List<AmountHit> hits;

	Rectangle2D screenBox = new Rectangle2D(0, 0, 640, 480);

	GameCharacter skeleton = new Skeleton(this);
	GameCharacter man = new Man(this);

	GameCharacter curCharacter = man;
	int tileSize = 32;

	private Debug debug = new Debug();

	public GameState() {

		hits = new ArrayList<>();
		attacks = new ArrayList<>();
		allCharacters = new ArrayList<>();
		allCharacters.add(man.setPos(100, 200));
		allCharacters.add(skeleton.setPos(100, 300));
		allCharacters.add(new Dummy(this).setPos(200, 140));
		allCharacters.add(new Dummy(this).setPos(200, 200));
		allCharacters.add(new Dummy(this).setPos(200, 260));
		allCharacters.add(new Dummy(this).setPos(200, 320));

	}

	public void drawTo(GraphicsContext gc) {

		if (debug.isTerrain()) {
			bigMap.drawTo(gc);
		}

		List<GameObject> drawables = new ArrayList<GameObject>();

		drawables.addAll(allCharacters);
		drawables.addAll(bigMap.getNonTerrainTiles());

		Collections.sort(drawables, (a, b) -> (int) (a.getDrawOrderIndex() - b.getDrawOrderIndex()));
		for (GameObject c : drawables) {
			c.draw(gc);
		}

		for (Attack attack : attacks) {
			attack.draw(gc);
		}

		for (AmountHit amountHit : hits) {
			amountHit.draw(gc);
		}

	}

	public GameCharacter getCurrentCharacter() {
		return man;
	}

	public void nextCycle() {

		updateAttacks();

		processHits();

		updateEffects();

		//spawn new skeletons ;)
		
		if (allCharacters.size()<3)
		{
			allCharacters.add(new Skeleton(this).setPos(220, 220));
		}
	}

	private void processHits() {
		for (Iterator<GameCharacter> cit = allCharacters.iterator(); cit.hasNext();) {

			GameCharacter c= cit.next();
			// check if hit by an attack?
			for (Attack a : attacks) {
				if (a.getCurrentCollisionBox().intersects(c.getCurrentCollisionBox())) {
					if (a.getOwner() != c) {
						
					
						c.takeHit(10);
					}
				}
			}
			c.nextCycle();
			if (c.endOfLife())
			{
				cit.remove();
			}
		}
	}

	private void updateEffects() {

		for (Iterator<AmountHit> amountHit = hits.iterator(); amountHit.hasNext();) {
			AmountHit hit = amountHit.next();
			hit.nextCycle();
			if (hit.endOfLife()) {
				amountHit.remove();
			}
		}

	}

	private void updateAttacks() {
		Iterator<Attack> it = attacks.iterator();
		while (it.hasNext()) {
			Attack a = it.next();
			a.nextCycle();
			if (a.endOfLife()) {
				it.remove();
			}
		}
	}

	public Rectangle2D getScreenBox() {
		return screenBox;
	}

	public boolean collidesWithOthers(GameCharacter gameCharacter, Rectangle2D newPosColBox) {
		Set<GameCharacter> others = allCharacters.stream().filter(o -> o != gameCharacter).collect(Collectors.toSet());

		for (GameCharacter o : others) {
			if (newPosColBox.intersects(o.getCurrentCollisionBox())) {
				return true;
			}
		}
		return false;
	}

	public Debug getDebug() {
		// TODO Auto-generated method stub
		return this.debug;
	}

	public void addAttack(Attack attack) {
		this.attacks.add(attack);
	}

	public boolean canWalk(Rectangle2D colbox) {
		return bigMap.canWalk(colbox);
	}

	public void addHit(AmountHit amountHit) {
		this.hits.add(amountHit);
	}

}
