package com.and.flights.repository;

import com.and.flights.model.data.ProductDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductDao, Long> {
}
