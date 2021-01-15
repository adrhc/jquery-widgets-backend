package ro.go.adrhc.datarest.repositories.dynaselone;

import lombok.Getter;

@Getter
public class DynaSelOneDetails<T> {
	private final String entityName;
	private final String titleField;
	private final Class<T> entityClass;
	private final String[] nestedProperties;

	public DynaSelOneDetails(String entityName, String titleField, Class<T> entityClass, String... nestedProperties) {
		this.entityName = entityName;
		this.titleField = titleField;
		this.entityClass = entityClass;
		this.nestedProperties = nestedProperties;
	}
}
