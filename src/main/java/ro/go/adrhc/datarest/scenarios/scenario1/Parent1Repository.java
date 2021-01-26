package ro.go.adrhc.datarest.scenarios.scenario1;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Parent1Repository extends CrudRepository<Parent1, Integer>, Parent1RepositoryEx {}
