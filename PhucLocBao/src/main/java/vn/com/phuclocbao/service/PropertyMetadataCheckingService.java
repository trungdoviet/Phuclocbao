package vn.com.phuclocbao.service;

import java.util.HashMap;
import java.util.Map;

import vn.com.phuclocbao.bean.PropertyMetadata;
import static vn.com.phuclocbao.bean.PropertyMetadata.PropertyMetadataBuilder.builder;
import vn.com.phuclocbao.bean.PropertyStagingData;
import vn.com.phuclocbao.enums.ProcessStaging;

import static vn.com.phuclocbao.enums.ProcessStaging.NEW;
import static vn.com.phuclocbao.enums.ProcessStaging.PAYOFF;
import static vn.com.phuclocbao.enums.ProcessStaging.PAID;
import static vn.com.phuclocbao.enums.ProcessStaging.UPDATE;

public class PropertyMetadataCheckingService {
	private static final Map<String, PropertyStagingData> propertiesByStaging = new HashMap<String, PropertyStagingData>();
	static {
		putOrMerge("contractDto.customer.idNo", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.customer.idNo", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.customer.idNo", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.customer.idNo", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.customer.name", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.customer.name", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.customer.name", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.customer.name", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.customer.birthYear", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.customer.birthYear", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.customer.birthYear", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.customer.birthYear", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.customer.phone", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.customer.phone", PAID, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.customer.phone", PAYOFF, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.customer.phone", UPDATE, builder().setDisabled(false).setReadOnly(false).get());
		
		putOrMerge("contractDto.customer.address", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.customer.address", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.customer.address", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.customer.address", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.customer.province", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.customer.province", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.customer.province", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.customer.province", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.contractType", NEW, builder().setDisabled(false).setReadOnly(true).get());
		putOrMerge("contractDto.contractType", PAID, builder().setDisabled(false).setReadOnly(true).get());
		putOrMerge("contractDto.contractType", PAYOFF, builder().setDisabled(false).setReadOnly(true).get());
		putOrMerge("contractDto.contractType", UPDATE, builder().setDisabled(false).setReadOnly(true).get());
		
		putOrMerge("contractDto.company.id", NEW, builder().setDisabled(false).setReadOnly(true).get());
		putOrMerge("contractDto.company.id", PAID, builder().setDisabled(false).setReadOnly(true).get());
		putOrMerge("contractDto.company.id", PAYOFF, builder().setDisabled(false).setReadOnly(true).get());
		putOrMerge("contractDto.company.id", UPDATE, builder().setDisabled(false).setReadOnly(true).get());
		
		putOrMerge("contractDto.totalAmount", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.totalAmount", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.totalAmount", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.totalAmount", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.feeADay", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.feeADay", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.feeADay", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.feeADay", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.customerDebt", NEW, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.customerDebt", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.customerDebt", PAYOFF, builder().setDisabled(false).setReadOnly(true).get());
		putOrMerge("contractDto.customerDebt", UPDATE, builder().setDisabled(false).setReadOnly(true).get());
		
		putOrMerge("contractDto.companyDebt", NEW, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.companyDebt", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.companyDebt", PAYOFF, builder().setDisabled(false).setReadOnly(true).get());
		putOrMerge("contractDto.companyDebt", UPDATE, builder().setDisabled(false).setReadOnly(true).get());
		
		putOrMerge("contractDto.note", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.note", PAID, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.note", PAYOFF, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.note", UPDATE, builder().setDisabled(false).setReadOnly(false).get());
		
		putOrMerge("contractDto.startDate", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.startDate", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.startDate", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.startDate", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.payoffDate", NEW, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.payoffDate", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.payoffDate", PAYOFF, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.payoffDate", UPDATE, builder().setDisabled(false).setReadOnly(true).get());
		
		putOrMerge("contractDto.expireDate", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.expireDate", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.expireDate", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.expireDate", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.periodOfPayment", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.periodOfPayment", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.periodOfPayment", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.periodOfPayment", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.owner.name", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.owner.name", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.owner.name", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.owner.name", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.owner.transportType", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.owner.transportType", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.owner.transportType", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.owner.transportType", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.owner.numberPlate", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.owner.numberPlate", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.owner.numberPlate", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.owner.numberPlate", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.owner.chassisFrameNumber", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.owner.chassisFrameNumber", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.owner.chassisFrameNumber", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.owner.chassisFrameNumber", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.owner.chassisNumber", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.owner.chassisNumber", PAID, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.owner.chassisNumber", PAYOFF, builder().setDisabled(true).setReadOnly(true).get());
		putOrMerge("contractDto.owner.chassisNumber", UPDATE, builder().setDisabled(true).setReadOnly(true).get());
		
		putOrMerge("contractDto.owner.detail", NEW, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.owner.detail", PAID, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.owner.detail", PAYOFF, builder().setDisabled(false).setReadOnly(false).get());
		putOrMerge("contractDto.owner.detail", UPDATE, builder().setDisabled(false).setReadOnly(false).get());
		
	}
	

	private static void putOrMerge(String key, ProcessStaging stage, PropertyMetadata propMeta) {
	
		PropertyStagingData ps = null;
		if (propertiesByStaging.containsKey(key)) {
			ps = propertiesByStaging.get(key);
		} else {
			ps = new PropertyStagingData();
		}
		ps.addPropsByStage(stage.getName(), propMeta);
		propertiesByStaging.put(key, ps);
	}

	public static Map<String, PropertyStagingData> getPropertiesByStaging() {
		return propertiesByStaging;
	}
	public static PropertyMetadata getByStateAndKey(String key, String staging){
		if(propertiesByStaging.containsKey(key)){
			if(propertiesByStaging.get(key).getPropsByStage().containsKey(staging)){
				return propertiesByStaging.get(key).getPropsByStage().get(staging);
			}
		}
		return new PropertyMetadata(false, false);
	}

}
