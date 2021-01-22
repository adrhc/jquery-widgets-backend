package ro.go.adrhc.datarest.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
public class HibernateUtils {
	public static <T> List<T> initializeNestedProperties(List<T> entities, String... nestedProperties) {
		if (entities != null) {
			entities.forEach(e -> initializeNestedProperties(e, nestedProperties));
		}
		return entities;
	}

	public static <T> T initializeNestedProperties(T entity, String... nestedProperties) {
		if (nestedProperties != null) {
			Arrays.asList(nestedProperties).forEach(it -> initializeNestedProperty(entity, it));
		}
		return entity;
	}

	public static <T> T initializeNestedProperty(T entity, String nestedPropertyName) {
		try {
			Object object = PropertyUtils.getNestedProperty(entity, nestedPropertyName);
			if (object instanceof Collection) {
				((Collection) object).size();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return entity;
	}
}
