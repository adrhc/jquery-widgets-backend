package ro.go.adrhc.datarest.scenarios.scenario2;

import java.util.Optional;

public interface ParentRepositoryEx2 {
	Parent2 insert(Parent2 parent);

	Parent2 update(Parent2 parent);

	Optional<Parent2> getById(Integer id);
}
