package vn.com.phuclocbao.controller;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.bean.ProfitStatisticResponseBody;
import vn.com.phuclocbao.bean.StatisticInfo;
import vn.com.phuclocbao.bean.UserExistingResponseBody;
import vn.com.phuclocbao.bean.UserSearchCriteria;
import vn.com.phuclocbao.bean.Views;
import vn.com.phuclocbao.bean.YearProfitCriteria;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.ContractService;
import vn.com.phuclocbao.service.UserService;
import vn.com.phuclocbao.util.MessageBundleUtil;



@Controller
@RequestMapping("/")
public class HomePageAjaxController {
	private static final String MSG_CAN_NOT_PERFORM_SEARCH = "msg.canNotPerformSearch";

	private static final String STATUS_SERVER_ERROR = "500";

	private static final String STATUS_OK = "200";


	private static org.apache.log4j.Logger logger = Logger.getLogger(HomePageAjaxController.class);
	
	@Autowired
	@Qualifier(value="contractService")
	ContractService contractService;
	
	@JsonView(Views.ProfitStatistic.class)
	@ResponseBody
	@RequestMapping(value = {"/search/profitByYear"}, method=RequestMethod.POST)
	public ProfitStatisticResponseBody collectStatistic(HttpServletRequest request, @RequestBody YearProfitCriteria search) {
		
		ProfitStatisticResponseBody result = new ProfitStatisticResponseBody();
		if (isValidSearchCriteria(search)) {
			try {
				PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
				StatisticInfo profitStats = contractService.collectProfitStatistic(plbSession.getCompanyId(), Integer.parseInt(search.getYear()));
				result.setStatistic(profitStats);
				result.setCode(STATUS_OK);
			} catch (BusinessException e) {
				logger.error(e);
				result.setCode(STATUS_SERVER_ERROR);
				result.setMsg(MSG_CAN_NOT_PERFORM_SEARCH);
				e.printStackTrace();
			}

		}
		return result;

	}

	private boolean isValidSearchCriteria(YearProfitCriteria search) {
		return search != null && StringUtils.isNotEmpty(search.getYear()) && Integer.parseInt(search.getYear()) > 0;
	}

}