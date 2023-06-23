package com.project.config;
import java.util.Arrays;

import com.project.exception.UnknownException;

public final class ConstantUserRoleConfig {

	public enum UserRole {
		BLOCK(1), BASIC(2), SUB_MNG(3), MNG(4), ADMIN(5);

		// () 안은 ENUM 상수 값에 할당된 값이 아니라 연결된 값이다.
		// ENUM java 내에서 특수한 클래스라 할당하는 정규 문법이 없다. 대신 연결해 줘야 한다.
		// ENUM class 내에서 인스턴트 필드 생성후 거기에서 접근 하게 해줘야 한다.
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
