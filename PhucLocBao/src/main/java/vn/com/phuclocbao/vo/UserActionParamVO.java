package vn.com.phuclocbao.vo;

public class UserActionParamVO {
	private String username;
	private Integer companyId;
	private String companyName;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}


	public String getCompanyName() {
		return companyName;
	}

	public static class UserActionParamVOBuilder {
		private String username;
		private Integer companyId;
		private String companyName;
		public static UserActionParamVOBuilder createBuilder(){
			return new UserActionParamVOBuilder();
		}


		public UserActionParamVOBuilder setUsername(String username) {
			this.username = username;
			return this;
		}
		public UserActionParamVO build(){
			UserActionParamVO vo = new UserActionParamVO();
			vo.setUsername(username);
			vo.companyId = companyId;
			vo.companyName = companyName;
			return vo;
		}

		public UserActionParamVOBuilder setCompanyId(Integer companyId) {
			this.companyId = companyId;
			return this;
		}

		public UserActionParamVOBuilder setCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		
	}
}
