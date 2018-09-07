package scripts.api.scriptapi.utils.listener.messagelistening;

/**
 * Created by willb on 27/07/2018.
 */
public interface MessageListener {
	void serverMessageReceived(String s);

	void clanMessageReceived(String s, String s1);

	void duelRequestReceived(String s, String s1);

	void personalMessageReceived(String s, String s1);

	void playerMessageReceived(String s, String s1);

	void tradeRequestReceived(String s);
}
