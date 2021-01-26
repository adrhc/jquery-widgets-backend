package ro.go.adrhc.datarest.repositories.parent;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.go.adrhc.datarest.entities.scenario1.Parent;

@Repository
public interface ParentRepository extends CrudRepository<Parent, Integer>, ParentRepositoryEx {}
