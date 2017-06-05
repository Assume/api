package scripts.api.scriptapi.paint;

import java.text.DecimalFormat;

public class Calculations {

	public static final int getPerHour(int amount, long run_time) {
		return (int) ((3600000.0 / run_time) * amount);

	}

	/*
	 * @param num the number to be formatted
	 * 
	 * @return the value as a String, at its smallest value, with a letter
	 * following signifying its size
	 */
	public static String formatNumber(int num) {
		DecimalFormat df = new DecimalFormat("0");
		double i = num;
		if (i >= 1000000 || i <= -1000000)
			if (i % 1000000 == 0)
				return df.format(i / 1000000) + "M";
			else
				return (i / 1000000) + "M";
		if (i >= 1000 || i <= -1000)
			return df.format((i / 1000)) + "k";
		return "" + num;
	}

}
