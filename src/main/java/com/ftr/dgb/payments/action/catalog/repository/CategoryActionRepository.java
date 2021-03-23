package com.ftr.dgb.payments.action.catalog.repository;

import com.ftr.dgb.payments.action.catalog.domain.CategoryAction;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the CategoryAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryActionRepository extends MongoRepository<CategoryAction, String> {
}
