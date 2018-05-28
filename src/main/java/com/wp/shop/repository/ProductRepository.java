package com.wp.shop.repository;

import com.wp.shop.model.data.ProductDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductDao, Long> {
}
