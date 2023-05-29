package com.project.config;

public final class ConstantConfig {

	public static final String Member_INFO = "mbInfo";

	public static final Integer perPage = 30;

	public static final String LastClick = "lastClickTime";

	public static final Integer rplPlus = 1;

	public static final String clientIp = "clientIp";

	public static final Integer SC_TOO_TOO_MANY_REQUESTS = 429;

	public static final Integer checkDepthLevel = 1;

	public static final String[] nullList = { "null", "Null", "NULL", "nuLL", "nulL", "nUll", "NUll", "NulL", "nULl",
			"NuLL", "nUlL", "NuLl", "NUlL", "nULl", "NULl" };

	public enum UserRole {
		 BASIC(1)
		, SUB_MANAGER(2)
		, MANAGER(3)
		, ADMIN(4);
		//() 안은 ENUM 상수 값에 할당된 값이 아니라 연결된 값이다.
		//ENUM java 내에서 특수한 클래스라 할당하는 정규 문법이 없다. 대신 연결해 줘야 한다.
		//ENUM class 내에서 인스턴트 필드 생성후 거기에서 접근 하게 해줘야 한다.
		private Integer level;

		UserRole(Integer level){
			this.level = level;
		}
		public Integer getLevel() {
			return this.level;
		}
	}



}
