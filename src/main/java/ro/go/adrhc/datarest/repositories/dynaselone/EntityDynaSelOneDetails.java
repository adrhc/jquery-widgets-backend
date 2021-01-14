package ro.go.adrhc.datarest.repositories.dynaselone;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EntityDynaSelOneDetails<T> {
	private final String entityName;
	private final String titleField;
	private final Class<T> entityClass;
}
