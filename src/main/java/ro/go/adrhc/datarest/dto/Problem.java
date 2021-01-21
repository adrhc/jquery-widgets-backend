package ro.go.adrhc.datarest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Problem<T> {
	private final String message;
	private final T data;
	@Setter
	private ProblemType type = ProblemType.FATAL;
}
