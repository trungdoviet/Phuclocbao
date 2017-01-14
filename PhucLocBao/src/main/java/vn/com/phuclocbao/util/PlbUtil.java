package vn.com.phuclocbao.util;

import javax.servlet.http.HttpServletRequest;

import vn.com.phuclocbao.bean.PLBSession;

public class PlbUtil {
	public static PLBSession getPlbSession(HttpServletRequest request){
		return (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
	}
}
