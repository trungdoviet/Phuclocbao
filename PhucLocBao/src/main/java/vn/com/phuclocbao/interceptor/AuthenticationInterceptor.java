package vn.com.phuclocbao.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import vn.com.phuclocbao.bean.PLBSession;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	private static org.apache.log4j.Logger logger = Logger.getLogger(AuthenticationInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("handle interceptor");
		// Avoid a redirect loop for some urls
		if (!request.getRequestURI().equals("/PhucLocBao/") && !request.getRequestURI().equals("/PhucLocBao/index")
				&& !request.getRequestURI().equals("/PhucLocBao/login")) {
			PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
			if (plbSession == null) {
				response.sendRedirect("/PhucLocBao/");
				return false;
			}
		}
		return true;

	}

}
