package ro.go.adrhc.datarest.repositories.parent;

import ro.go.adrhc.datarest.entities.scenario1.Parent;

public interface ParentRepositoryEx {
	Parent generateParent();

	Parent insert(Parent person);
}
