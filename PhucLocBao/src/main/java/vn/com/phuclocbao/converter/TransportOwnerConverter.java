package vn.com.phuclocbao.converter;
import vn.com.phuclocbao.dto.TransportOwnerDto;
import vn.com.phuclocbao.entity.TransportOwner;

public class TransportOwnerConverter extends BaseConverter<TransportOwnerDto, TransportOwner>{

	private static TransportOwnerConverter instance;
	
	private TransportOwnerConverter() {}
	
	public static synchronized  TransportOwnerConverter getInstance() {
		if (instance == null) {
			instance = new TransportOwnerConverter();
		}
		return instance;
	}

	@Override
	public String[] getIgnoredProperties() {
		return new String[]{ "contract"};
	}



	
	
	
}
