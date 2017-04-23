package vn.com.phuclocbao.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.MethodUtils;

public class BeanUtilsExtension  {
	private BeanUtilsBean beanUtilBean;
	public BeanUtilsExtension(BeanUtilsBean beanUtilBean) {
		this.beanUtilBean = beanUtilBean;
    }

	
	public Map<String, String> describe(Object bean, Set<String> ignoreProps)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		  if (bean == null) {
		        //            return (Collections.EMPTY_MAP);
		            return (new java.util.HashMap<String, String>());
		        }


		        final Map<String, String> description = new HashMap<String, String>();
		        if (bean instanceof DynaBean) {
		            final DynaProperty[] descriptors =
		                ((DynaBean) bean).getDynaClass().getDynaProperties();
		            for (DynaProperty descriptor : descriptors) {
		                final String name = descriptor.getName();
		                description.put(name, beanUtilBean.getProperty(bean, name));
		            }
		        } else {
		            final PropertyDescriptor[] descriptors =
		            		beanUtilBean.getPropertyUtils().getPropertyDescriptors(bean);
		            final Class<?> clazz = bean.getClass();
		            for (PropertyDescriptor descriptor : descriptors) {
		                final String name = descriptor.getName();
		                if (!ignoreProps.contains(name) && getReadMethod(clazz, descriptor) != null) {
		                    description.put(name, beanUtilBean.getProperty(bean, name));
		                }
		            }
		        }
		        return (description);
	}

	Method getReadMethod(final Class<?> clazz, final PropertyDescriptor descriptor) {
        return (MethodUtils.getAccessibleMethod(clazz, descriptor.getReadMethod()));
    }
}
