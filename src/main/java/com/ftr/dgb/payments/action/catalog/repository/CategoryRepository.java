package com.ftr.dgb.payments.action.catalog.repository;

import com.ftr.dgb.payments.action.catalog.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {


}
