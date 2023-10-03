package com.project.config;

import java.util.Arrays;
import java.util.List;

public final class ConstantConfig {

	public static final String Member_INFO = "mbInfo";

	public static final Integer perPage = 30;

	public static final Integer startView = 0;

	public static final Integer plusView = 1;

	public static final Integer compare = 0;

	public enum Target {
		CREATOR(1)
		, TTL(2)
		, CN(3)
		, COMP(4)
		, ALL(5);

		private Integer level;

		Target(Integer level) {
			this.level = level;
		}

		public Integer getLevel() {
			return this.level;
		}

		public static List<Target> getCompValue() {
			return Arrays.asList(TTL, CN);
		}

		public static List<Target> getAllValue() {
			return Arrays.asList(values());
		}

		public static Integer getLevel(Target target) {
			return target.getLevel();
		}
	}

	public static final String LastClick = "lastClickTime";

	public static final Integer insertStartNum = 0;

	public static final Integer rplPlus = 1;

	public static final String clientIp = "clientIp";

	public static final Integer SC_TOO_TOO_MANY_REQUESTS = 429;

	public static final Integer checkDepthLevel = 1;

	public static final Integer startDepth = 1;

	public static final String[] nullList = { "null", "Null", "NULL", "nuLL", "nulL", "nUll", "NUll", "NulL", "nULl",
			"NuLL", "nUlL", "NuLl", "NUlL", "nULl", "NULl" };

	public enum UserRole {
		BASIC(1), SUB_MNG(2), MNG(3), ADMIN(4);

		private Integer level;

		UserRole(Integer level) {
			this.level = level;
		}

		public Integer getLevel() {
			return this.level;
		}
	}

}
