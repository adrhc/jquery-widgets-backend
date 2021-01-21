package ro.go.adrhc.datarest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProblemType {
	FATAL("fatal"), WARNING("warning");

	private final String value;
}
