package ro.go.adrhc.datarest.scenarios.scenario1;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Country1Repository extends CrudRepository<Country1, Integer> {}
