package vn.com.phuclocbao.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import vn.com.phuclocbao.bean.CityDto;

public class VietnamCityService {
	private static final Map<String, Object> cities = new HashMap<String, Object>() {
		private static final long serialVersionUID = 7762325715745353754L;
	{
		put("01", "TP Hồ Chí Minh");
		put("02", "Hà Nội");
	    put("03",    "An Giang");
	    put("04", "Bà Rịa Vũng Tàu");
	    put("05", "Bắc Giang");
	    put("06", "Bắc Kạn");
	    put("07", "Bạc Liêu");
	    put("08", "Bắc Ninh");
	    put("09", "Bến Tre");
	    put("10", "Bình Định");
	    put("11", "Bình Dương");
	    put("12", "Bình Phước");
	    put("13", "Bình Thuận");
	    put("14", "Cà Mau");
	    put("15", "Cần Thơ");
	    put("16", "Cao Bằng");
	    put("17", "Đà Nẵng");
	    put("18", "Đắc Lắc");
	    put("19", "Đắc Nông");
	    put("20", "Điện Biên");
	    put("21", "Đồng Nai");
	    put("22", "Đồng Tháp");
	    put("23", "Gia Lai");
	    put("24", "Hà Giang");
	    put("25", "Hà Nam");
	    put("26", "Hà Tây");
	    put("27", "Hà Tĩnh");
	    put("28", "Hải Dương");
	    put("29", "Hải Phòng");
	    put("30", "Hậu Giang");
	    put("31", "Hoà Bình");
	    put("32", "Hưng Yên");
	    put("33", "Khánh Hoà");
	    put("34", "Kiên Giang");
	    put("35", "Kon Tum");
	    put("36", "Lai Châu");
	    put("37", "Lâm Đồng");
	    put("38", "Lạng Sơn");
	    put("39", "Lào Cai");
	    put("40", "Long An");
	    put("41", "Nam Định");
	    put("42", "Nghệ An");
	    put("43", "Ninh Bình");
	    put("44", "Ninh Thuận");
	    put("45", "Phú Thọ");
	    put("46", "Phú Yên");
	    put("47", "Quảng Bình");
	    put("48", "Quảng Nam");
	    put("49", "Quảng Ngãi");
	    put("50", "Quảng Ninh");
	    put("51", "Quảng Trị");
	    put("52", "Sóc Trăng");
	    put("53", "Sơn La");
	    put("54", "Tây Ninh");
	    put("55", "Thái Bình");
	    put("56", "Thái Nguyên");
	    put("57", "Thanh Hoá");
	    put("58", "Thừa thiên Huế");
	    put("59", "Tiền Giang");
	    put("60", "Trà Vinh");
	    put("61", "Tuyên Quang");
	    put("62", "Vĩnh Long");
	    put("63", "Vĩnh Phúc");
	    put("64", "Yên Bái");
	}};
	public static List<CityDto> loadCities(){
		List<CityDto> dtos = new ArrayList<>();
		cities.forEach((k,v) -> {
			dtos.add(new CityDto(k, (String) v));
		});
		
		return dtos.stream().sorted((c1, c2) -> StringUtils.compare(c1.getCode(), c2.getCode())).collect(Collectors.toList());
	}
}
