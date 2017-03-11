package vn.com.phuclocbao.controller;
import java.util.Optional;
import java.util.function.Predicate;

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

import vn.com.phuclocbao.bean.MonthlyCompanyProfitCriteria;
import vn.com.phuclocbao.bean.MonthlyProfitDetail;
import vn.com.phuclocbao.bean.MonthlyProfitStatisticResponseBody;
import vn.com.phuclocbao.bean.Views;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.ContractService;



@Controller
@RequestMapping("/")
public class CompanyProfitAjaxController {
	private static final String MSG_CAN_NOT_PERFORM_SEARCH = "msg.canNotPerformSearch";

	private static final String STATUS_SERVER_ERROR = "500";

	private static final String STATUS_OK = "200";


	private static org.apache.log4j.Logger logger = Logger.getLogger(CompanyProfitAjaxController.class);
	
	@Autowired
	@Qualifier(value="contractService")
	ContractService contractService;
	
	@JsonView(Views.ProfitStatistic.class)
	@ResponseBody
	@RequestMapping(value = {"/get/profitDetail"}, method=RequestMethod.POST)
	public MonthlyProfitStatisticResponseBody getProfitDetailOfCompany(HttpServletRequest request, @RequestBody MonthlyCompanyProfitCriteria search) {
		
		MonthlyProfitStatisticResponseBody result = new MonthlyProfitStatisticResponseBody();
		if (isValidSearchCriteria(search)) {
			try {
				MonthlyProfitDetail monthlyProfitDetail = contractService.collectMonthlyProfitStatistic(Integer.parseInt(search.getCompanyId()), 
																Integer.parseInt(search.getYear()), 
																Integer.parseInt(search.getMonth()));
				result.setMonthlyProfitDetail(monthlyProfitDetail);
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

	private boolean isValidSearchCriteria(MonthlyCompanyProfitCriteria search) {
		Optional.ofNullable(search)
				.filter(item -> StringUtils.isNotEmpty(item.getYear()) 
							&& StringUtils.isNotEmpty(item.getMonth()) 
							&& StringUtils.isNotEmpty(item.getMonth()))
				.filter(isYearValid())
				.filter(isMonthValid())
				.filter(isCompanyIdValid())
				.isPresent();
		return search != null && StringUtils.isNotEmpty(search.getYear()) && Integer.parseInt(search.getYear()) > 0;
	}
	private Predicate<MonthlyCompanyProfitCriteria> isYearValid(){
		return new Predicate<MonthlyCompanyProfitCriteria>() {
			@Override
			public boolean test(MonthlyCompanyProfitCriteria search) {
				return Integer.parseInt(search.getYear()) > 0;
			}
		};
	}
	
	private Predicate<MonthlyCompanyProfitCriteria> isCompanyIdValid(){
		return new Predicate<MonthlyCompanyProfitCriteria>() {
			@Override
			public boolean test(MonthlyCompanyProfitCriteria search) {
				return Integer.parseInt(search.getCompanyId()) > 0;
			}
		};
	}
	
	private Predicate<MonthlyCompanyProfitCriteria> isMonthValid(){
		return new Predicate<MonthlyCompanyProfitCriteria>() {
			@Override
			public boolean test(MonthlyCompanyProfitCriteria search) {
				return Integer.parseInt(search.getMonth()) > 0 && Integer.parseInt(search.getMonth()) <=13 ;
			}
		};
	}

}