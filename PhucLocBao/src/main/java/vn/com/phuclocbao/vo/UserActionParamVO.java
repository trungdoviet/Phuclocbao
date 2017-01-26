package vn.com.phuclocbao.vo;

public class UserActionParamVO {
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public static class UserActionParamVOBuilder {
		private String username;

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
			return vo;
		}
		
	}
}
