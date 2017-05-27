package scripts.api.scriptapi.logging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tribot.api.General;

public class Logger {

	public static final boolean isScripter() {
		int id = General.getTRiBotUserID();
		return id == 10385 || id == 5359;
	}

	private final List<ScriptLog> logs;

	public static final int GENERIC = 0;
	public static final int WARNING = 1;
	public static final int CRITICAL = 2;
	public static final int SCRIPTER_ONLY = 3;
	public static final int ABC_LOGS = 4;

	private int warning_level;

	private static Logger logger;

	public static Logger getLogger() {
		return logger == null ? logger = new Logger() : logger;
	}

	private Logger() {
		this.warning_level = 0;
		this.logs = new ArrayList<ScriptLog>();
	}

	public void print(Object message) {
		print(GENERIC, message);
	}

	public void print(int level, Object message) {
		logs.add(new ScriptLog(level, message, new Date()));
		if (level < warning_level)
			return;
		if (level == SCRIPTER_ONLY && !isScripter())
			return;
		System.out.println(message == null ? "null" : message.toString());
	}

	public void setWarningLevel(int level) {
		if (level < 0)
			warning_level = GENERIC;
		else if (level > 4)
			warning_level = SCRIPTER_ONLY;
		else
			warning_level = level;
	}

	public String[] getAllLogsAsString(int level, int number) {
		// return (String[]) this.logs.stream().filter(a -> a != null &&
		// a.getLevel() >= level)
		// .sorted((a, b) ->
		// a.getDate().compareTo(b.getDate())).limit(number).map(a ->
		// a.toString()).toArray();

		List<String> temp = new ArrayList<String>();
		for (int i = this.logs.size() - 1; i >= 0 && temp.size() < number; i--) {
			ScriptLog log = logs.get(i);
			if (log != null && log.getLevel() >= level)
				temp.add(log.toString());
		}
		return temp.toArray(new String[temp.size()]);

	}

	public ScriptLog[] getAllLogs(int level, int number) {
		return (ScriptLog[]) this.logs.stream().filter(a -> a != null && a.getLevel() >= level).limit(number).toArray();
		/*
		 * List<ScriptLog> temp = new ArrayList<ScriptLog>(); for (int i =
		 * this.logs.size() - 1; i >= 0 && temp.size() < number; i--) {
		 * ScriptLog log = logs.get(i); if (log != null && log.getLevel() >=
		 * level) temp.add(log); else number++; } return temp.toArray(new
		 * ScriptLog[temp.size()]);
		 */
	}

}
