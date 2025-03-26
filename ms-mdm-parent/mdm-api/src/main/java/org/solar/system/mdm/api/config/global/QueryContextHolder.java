package org.solar.system.mdm.api.config.global;

import org.springframework.stereotype.Component;

@Component
public class QueryContextHolder {

	private static final ThreadLocal<String> queryThread = new ThreadLocal<>();

	public static void setQuery(String queryString) {
		queryThread.set(queryString);
	}

	public static String getQuery() {
		return queryThread.get();
	}

	public static void clear() {
		queryThread.remove();
	}

}
