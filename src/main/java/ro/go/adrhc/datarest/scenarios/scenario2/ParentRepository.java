package ro.go.adrhc.datarest.scenarios.scenario2;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends CrudRepository<Parent, Integer>, ParentRepositoryEx {}
