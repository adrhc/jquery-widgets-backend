package ro.go.adrhc.datarest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.go.adrhc.datarest.entities.scenario1.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {}
