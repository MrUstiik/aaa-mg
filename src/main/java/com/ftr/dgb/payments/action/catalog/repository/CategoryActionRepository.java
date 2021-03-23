package com.ftr.dgb.payments.action.catalog.repository;

import com.ftr.dgb.payments.action.catalog.domain.CategoryAction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB reactive repository for the CategoryAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryActionRepository extends ReactiveMongoRepository<CategoryAction, String> {}
