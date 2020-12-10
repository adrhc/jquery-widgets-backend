package ro.go.adrhc.datarest.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ro.go.adrhc.datarest.entities.Cat;

public interface CatsRepository extends PagingAndSortingRepository<Cat, Integer> {}
