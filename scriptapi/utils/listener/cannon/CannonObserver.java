package scripts.api.scriptapi.utils.listener.cannon;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Login;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSProjectile;
import org.tribot.api2007.types.RSTile;

import scripts.api.scriptapi.utils.listener.ProjectileListener;
import scripts.api.scriptapi.utils.listener.messagelistening.MessageListener;

/**
 * Created by willb on 26/07/2018.
 */
public class CannonObserver extends Thread implements ProjectileListener, MessageListener {

	private ArrayList<CannonListener> listeners;
	private boolean running;

	private int cBallsRemaining;
	private RSTile cannonTile;

	public CannonObserver() {
		this.listeners = new ArrayList<>();
		this.running = true;
	}

	@Override
	public void run() {
		while (Login.getLoginState() != Login.STATE.INGAME) {
			General.sleep(500);
		}

		while (running) {

			General.sleep(50);
		}
	}

	public void addListener(CannonListener listener) {
		this.listeners.add(listener);
	}

	public void onEmptyTrigger() {
		for (CannonListener l : listeners) {
			l.onEmpty();
		}
	}

	public void onReloadTrigger() {
		for (CannonListener l : listeners) {
			l.onReload();
		}
	}

	public void onPlacedTrigger(RSTile cannonPos) {
		for (CannonListener l : listeners) {
			l.onCannonPlaced(cannonPos);
		}
	}

	public void onFiredTrigger(RSProjectile projectile) {
		for (CannonListener l : listeners) {
			l.onFired(projectile, cBallsRemaining);
		}
	}

	@Override
	public void serverMessageReceived(String s) {
		if (s.contains("You load")) {
			cBallsRemaining = 30; // Could be less than 30 balls
			onReloadTrigger();
		} else if (s.contains("Out of ammo")) {
			cBallsRemaining = 0;
			onEmptyTrigger();
		} else if (s.contains("You add the furnace")) {
			RSObject[] b = Objects.findNearest(10, "Dwarf multicannon");

			if (b.length > 0 && b[0] != null) {
				RSModel model = b[0].getModel();

				if (model != null) {
					cannonTile = model.getPosition();
					onPlacedTrigger(model.getPosition());
				}
			}
		}
	}

	@Override
	public void clanMessageReceived(String s, String s1) {

	}

	@Override
	public void duelRequestReceived(String s, String s1) {

	}

	@Override
	public void personalMessageReceived(String s, String s1) {

	}

	@Override
	public void playerMessageReceived(String s, String s1) {

	}

	@Override
	public void tradeRequestReceived(String s) {

	}

	@Override
	public void projectileMoved(RSProjectile projectile) {
		if (cannonTile != null && projectile.getGraphicID() == 53) {
			int startX = ((projectile.getStartX() >>> 7) + Game.getBaseX());
			int startY = ((projectile.getStartY() >>> 7) + Game.getBaseY());

			if (startX == cannonTile.getX() && startY == cannonTile.getY()) {
				cBallsRemaining--;
				onFiredTrigger(projectile);
			}
		}
	}
}
