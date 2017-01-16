package vn.com.phuclocbao.bean;

public class PropertyMetadata {
	private boolean isDisabled;
	private boolean isReadonly;
	public PropertyMetadata(boolean isReadonly, boolean isDisabled){
		this.isDisabled = isDisabled;
		this.isReadonly = isReadonly;
	}
	public boolean isDisabled() {
		return isDisabled;
	}
	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
	public boolean isReadonly() {
		return isReadonly;
	}
	public void setReadonly(boolean isReadonly) {
		this.isReadonly = isReadonly;
	}
	public static class PropertyMetadataBuilder{
		private boolean isDisabled;
		private boolean isReadonly;
		public static PropertyMetadataBuilder builder(){
			return new PropertyMetadataBuilder();
		}
		
		public PropertyMetadataBuilder setDisabled(boolean isDisabled){
			this.isDisabled = isDisabled;
			return this;
		}
		
		public PropertyMetadataBuilder setReadOnly(boolean isReadonly){
			this.isReadonly = isReadonly;
			return this;
		}
		public PropertyMetadata get(){
			PropertyMetadata pm = new PropertyMetadata(isReadonly, isDisabled);
			
			return pm;
		}
		
	}
	
}
