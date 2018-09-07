package scripts.api.scriptapi.utils.listener;

import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.api2007.Projectiles;
import org.tribot.api2007.types.RSProjectile;

import java.util.ArrayList;

/**
 * Created by willb on 26/07/2018.
 */
public class ProjectileObserver extends Thread {

	private ArrayList<ProjectileListener> listeners;
	private boolean running;

	private ArrayList<RSProjectile> projectileCache;

	public ProjectileObserver() {
		this.listeners = new ArrayList<>();
		this.running = true;
		this.projectileCache = new ArrayList<>();
	}

	@Override
	public void run() {
		while (Login.getLoginState() != Login.STATE.INGAME) {
			General.sleep(500);
		}

		while (running) {
			RSProjectile[] projectiles = Projectiles.getAll();

			for (RSProjectile p : projectiles) {
				if (!projectileCache.contains(p)) {
					projectileMovedTrigger(p);
					projectileCache.add(p);
				}
			}
			General.sleep(50);
		}
	}

	public void addListener(ProjectileListener listener) {
		this.listeners.add(listener);
	}

	public void projectileMovedTrigger(RSProjectile projectile) {
		for (ProjectileListener l : listeners) {
			l.projectileMoved(projectile);
		}
	}
}
