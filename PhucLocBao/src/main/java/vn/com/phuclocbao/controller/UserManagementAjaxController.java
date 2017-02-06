package vn.com.phuclocbao.controller;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import vn.com.phuclocbao.bean.UserExistingResponseBody;
import vn.com.phuclocbao.bean.UserSearchCriteria;
import vn.com.phuclocbao.bean.Views;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.UserService;
import vn.com.phuclocbao.util.MessageBundleUtil;



@Controller
@RequestMapping("/")
public class UserManagementAjaxController {
	private static final String MSG_CAN_NOT_PERFORM_SEARCH = "msg.canNotPerformSearch";

	private static final String STATUS_SERVER_ERROR = "500";

	private static final String STATUS_OK = "200";

	private static final String MSG_USER_IS_NOT_AVAILABLE = "msg.userIsNotAvailable";

	private static final String MSG_USER_IS_AVAILABLE = "msg.userIsAvailable";

	private static org.apache.log4j.Logger logger = Logger.getLogger(UserManagementAjaxController.class);
	
	@Autowired
	UserService userService;
	
	@JsonView(Views.User.class)
	@ResponseBody
	@RequestMapping(value = {"/search/getUser"}, method=RequestMethod.POST)
	public UserExistingResponseBody getUser(HttpServletRequest request, @RequestBody UserSearchCriteria search) {
		
		UserExistingResponseBody result = new UserExistingResponseBody();
		if (isValidSearchCriteria(search)) {
			try {
				boolean isExist = userService.isUserExist(search.getUsername());
				result.setIsExist(isExist);
				result.setCode(STATUS_OK);
				if(isExist) {
					result.setMsg( MessageBundleUtil.getMessage(MSG_USER_IS_NOT_AVAILABLE));
				} else {
					result.setMsg( MessageBundleUtil.getMessage(MSG_USER_IS_AVAILABLE));
				}
			} catch (BusinessException e) {
				logger.error(e);
				result.setCode(STATUS_SERVER_ERROR);
				result.setMsg(MSG_CAN_NOT_PERFORM_SEARCH);
				e.printStackTrace();
			}

		}
		return result;

	}

	private boolean isValidSearchCriteria(UserSearchCriteria search) {
		return search != null && StringUtils.isNotEmpty(search.getUsername());
	}

}