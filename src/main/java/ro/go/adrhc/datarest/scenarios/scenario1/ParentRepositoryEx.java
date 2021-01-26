package ro.go.adrhc.datarest.scenarios.scenario1;

public interface ParentRepositoryEx {
	Parent generateParent();

	Parent insert(Parent person);

	Parent update(Parent person);
}
