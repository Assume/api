package scripts.api.scriptapi.logging;

import java.util.Date;

public class ScriptLog implements Comparable<ScriptLog> {

	private int level;
	private Object message;
	private Date date;

	public ScriptLog(int level, Object message, Date date) {
		this.level = level;
		this.message = message;
		this.date = date;
	}

	public int getLevel() {
		return this.level;
	}

	public Object getMesage() {
		return this.message;
	}

	public Date getDate() {

		return this.date;
	}

	@Override
	public String toString() {
		return "[" + this.date.toString() + "] (" + this.level + ") " + this.message == null ? "null"
				: this.message.toString();
	}

	@Override
	public int compareTo(ScriptLog o) {
		if (o == null)
			return 1;
		return -date.compareTo(o.date);

	}

}
