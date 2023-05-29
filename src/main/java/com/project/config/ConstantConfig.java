package com.project.config;

public final class ConstantConfig {

	public static final String Member_INFO = "mbInfo";

	public static final Integer perPage = 30;

	public static final String LastClick = "lastClickTime";

	public static final Integer rplPlus = 1;

	public static final String clientIp = "clientIp";

	public static final Integer SC_TOO_TOO_MANY_REQUESTS = 429;

	public static final String[] nullList = { "null", "Null", "NULL", "nuLL", "nulL", "nUll", "NUll", "NulL", "nULl",
			"NuLL", "nUlL", "NuLl", "NUlL", "nULl", "NULl" };

	public enum UserRole {
		BASIC
		,SUB_MANAGER
		,MANAGER
		,ADMIN
	}

}
