package vn.com.phuclocbao.controller;
import java.util.List;

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

import vn.com.phuclocbao.bean.ContractResponseBody;
import vn.com.phuclocbao.bean.CustomerSearchCriteria;
import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.bean.Views;
import vn.com.phuclocbao.dto.CustomerDto;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.CustomerService;



@Controller
@RequestMapping("/")
public class ContractAjaxController {
	private static org.apache.log4j.Logger logger = Logger.getLogger(ContractController.class);
	
	@Autowired
	CustomerService customerService;
	
	
	@JsonView(Views.Public.class)
	@ResponseBody
	@RequestMapping(value = "/search/getContract", method=RequestMethod.POST)
	public ContractResponseBody getSearchResultViaAjax(HttpServletRequest request, @RequestBody CustomerSearchCriteria search) {
		
		ContractResponseBody result = new ContractResponseBody();
		if (isValidSearchCriteria(search)) {
			logger.info("Search name: "+search.getCustomerId());
			try {
				List<CustomerDto> dtos = customerService.getCustomersByIdNo(search.getCustomerId());
				result = customerService.buildContractResponse(dtos);
				PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
				plbSession.setContractResponseBody(result);
				logger.info("Search size:"+dtos.size());
			} catch (BusinessException e) {
				logger.error(e);
				e.printStackTrace();
			}

		}
		return result;

	}

	private boolean isValidSearchCriteria(CustomerSearchCriteria search) {
		return search != null && StringUtils.isNotEmpty(search.getCustomerId());
	}

}