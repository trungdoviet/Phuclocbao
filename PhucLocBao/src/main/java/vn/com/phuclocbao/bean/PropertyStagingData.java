package vn.com.phuclocbao.bean;

import java.util.HashMap;
import java.util.Map;

public class PropertyStagingData {
	private Map<String, PropertyMetadata> propsByStage ;
	public PropertyStagingData(){
		propsByStage = new HashMap<>();
	}
	public Map<String, PropertyMetadata> getPropsByStage() {
		return propsByStage;
	}

	public void setPropsByStage(Map<String, PropertyMetadata> propsByStage) {
		this.propsByStage = propsByStage;
	}
	public void addPropsByStage(String key, PropertyMetadata props){
		this.propsByStage.put(key, props);
	}
}
