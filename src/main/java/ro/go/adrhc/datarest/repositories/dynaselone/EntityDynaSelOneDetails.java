package ro.go.adrhc.datarest.repositories.dynaselone;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@Getter
public class EntityDynaSelOneDetails<T, R> {
	private final String entityName;
	private final String titleField;
	private final Class<T> entityClass;
	private final Function<List<T>, List<R>> transformer;
}
