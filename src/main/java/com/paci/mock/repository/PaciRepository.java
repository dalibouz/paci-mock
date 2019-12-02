package com.paci.mock.repository;
import com.paci.mock.domain.Paci;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Paci entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaciRepository extends MongoRepository<Paci, String> {

}
