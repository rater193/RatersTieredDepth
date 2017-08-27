package com.rater193.rtd;

import java.util.logging.Logger;

public class Debug {

	private static Logger logger = Logger.getLogger("Minecraft");
	public static void log(Object message) {
		logger.info("[Tiered Depth Limit] " + message);
	}
}
