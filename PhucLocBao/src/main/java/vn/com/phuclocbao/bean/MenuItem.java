package vn.com.phuclocbao.bean;

import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.enums.MenuState;

public class MenuItem {
	private MenuState state;
	private MenuDefinition identity;
	public MenuItem(MenuDefinition identity){
		this.state = MenuState.INACTIVE;
		this.identity = identity;
	}
	public MenuState getState() {
		return state;
	}

	public void setState(MenuState state) {
		this.state = state;
	}
	public MenuDefinition getIdentity() {
		return identity;
	}
}
