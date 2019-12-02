package com.paci.mock.repository;
import com.paci.mock.domain.PaciContext;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the PaciContext entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaciContextRepository extends MongoRepository<PaciContext, String> {

}
