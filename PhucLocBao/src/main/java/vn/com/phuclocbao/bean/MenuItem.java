package vn.com.phuclocbao.bean;

import vn.com.phuclocbao.enums.MenuState;

public class MenuItem {
	private MenuState state;
	public MenuItem(){
		this.state = MenuState.INACTIVE;
	}
	public MenuState getState() {
		return state;
	}

	public void setState(MenuState state) {
		this.state = state;
	}
	
}
