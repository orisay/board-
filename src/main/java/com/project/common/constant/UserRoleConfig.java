package com.project.common.constant;
import java.util.Arrays;

import com.project.exception.UnknownException;

public final class UserRoleConfig {

	public enum UserRole {
		BLOCK(1), BASIC(2), SUB_MNG(3), MNG(4), ADMIN(5);


		
		private Integer level;

		UserRole(Integer level) {
			this.level = level;
		}

		public Integer getLevel() {
			return this.level;
		}

		public static UserRole getRole(Integer roleNum) {
			return Arrays.stream(UserRole.values())
					.filter(r -> r.getLevel() == roleNum)
					.findFirst()
					.orElseThrow(() -> new UnknownException("DB is not affected"));
		}

	}

}
