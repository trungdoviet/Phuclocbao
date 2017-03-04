package vn.com.phuclocbao.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.enums.MenuState;

public class MenuBean {
	private Map<String, MenuItem> items;
	public MenuBean(){
		items = new HashMap<>();
		items.put(MenuDefinition.HOME.getName(), new MenuItem(MenuDefinition.HOME));
		items.put(MenuDefinition.NEW_CONTRACT.getName(), new MenuItem(MenuDefinition.NEW_CONTRACT));
		items.put(MenuDefinition.MANAGE_CONTRACT.getName(), new MenuItem(MenuDefinition.MANAGE_CONTRACT));
		items.put(MenuDefinition.OLD_CONTRACT.getName(), new MenuItem(MenuDefinition.OLD_CONTRACT));
		items.put(MenuDefinition.NOTIFICATION.getName(), new MenuItem(MenuDefinition.NOTIFICATION));
		items.put(MenuDefinition.DAILY_WORK.getName(), new MenuItem(MenuDefinition.DAILY_WORK));
		items.put(MenuDefinition.HISTORY.getName(), new MenuItem(MenuDefinition.HISTORY));
		items.put(MenuDefinition.BAD_CONTRACT.getName(), new MenuItem(MenuDefinition.BAD_CONTRACT));
		items.put(MenuDefinition.COMPANY_FINANCIAL.getName(), new MenuItem(MenuDefinition.COMPANY_FINANCIAL));
		items.put(MenuDefinition.COMPANY_BRANCH.getName(), new MenuItem(MenuDefinition.COMPANY_BRANCH));
		items.put(MenuDefinition.MANAGE_USER.getName(), new MenuItem(MenuDefinition.MANAGE_USER));
		items.put(MenuDefinition.COMPANY_PROFIT.getName(), new MenuItem(MenuDefinition.COMPANY_PROFIT));
		items.put(MenuDefinition.CUSTOMER_SEARCH.getName(), new MenuItem(MenuDefinition.CUSTOMER_SEARCH));
		makeActive(MenuDefinition.HOME);
	}
	
	public MenuDefinition getDefActiveMenu(){
		Optional<MenuDefinition> def = items.entrySet()
											.stream()
											.filter(item -> item.getValue().getState() == MenuState.ACTIVE)
											.map(item-> item.getValue().getIdentity())
											.findFirst();
		if(def.isPresent()){
			return def.get();
		}
		return null;
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
	
	public String getCompanyMenuCollapseState(){
		if(MapUtils.isNotEmpty(items)){
			return (items.get(MenuDefinition.COMPANY_BRANCH.getName()).getState() == MenuState.ACTIVE
					|| items.get(MenuDefinition.MANAGE_USER.getName()).getState() == MenuState.ACTIVE
					|| items.get(MenuDefinition.COMPANY_PROFIT.getName()).getState() == MenuState.ACTIVE
					|| items.get(MenuDefinition.COMPANY_FINANCIAL.getName()).getState() == MenuState.ACTIVE) ? "in" : StringUtils.EMPTY ;
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
