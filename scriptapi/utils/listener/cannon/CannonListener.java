package scripts.api.scriptapi.utils.listener.cannon;

import org.tribot.api2007.types.RSProjectile;
import org.tribot.api2007.types.RSTile;

/**
 * Created by willb on 27/07/2018.
 */
public interface CannonListener {

    void onCannonPlaced(RSTile cannonPosition);

    void onFired(RSProjectile projectile, int cBallsRemaining);

    void onReload();

    void onEmpty();
}
