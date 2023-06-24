package com.project.common.constant;

import java.util.Arrays;
import java.util.List;

public class SearchLevelConfig {

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

}
