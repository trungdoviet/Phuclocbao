package vn.com.phuclocbao.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.enums.MenuState;

public class MenuBean {
	private Map<String, MenuItem> items;
	public MenuBean(){
		items = new HashMap<>();
		items.put(MenuDefinition.HOME.getName(), new MenuItem());
		items.put(MenuDefinition.NEW_CONTRACT.getName(), new MenuItem());
		items.put(MenuDefinition.CONTRACT.getName(), new MenuItem());
		makeActive(MenuDefinition.HOME);
	}
	
	public void makeActive(MenuDefinition menu){
		makeActive(menu.getName());
	}
	
	private void makeActive(String name){
		if(MapUtils.isNotEmpty(items) && items.containsKey(name)){
			makeAllInactive();
			items.get(name).setState(MenuState.ACTIVE);
		}
	}
	
	public String getStateClass(String name){
		if(MapUtils.isNotEmpty(items) && items.containsKey(name)){
			return items.get(name).getState().getClassName();
		}
		return StringUtils.EMPTY;
	}
	
	private void makeAllInactive(){
		if(MapUtils.isNotEmpty(items)){
			items.forEach((k,v) -> {
				v.setState(MenuState.INACTIVE);
			});
		}
	}
}
