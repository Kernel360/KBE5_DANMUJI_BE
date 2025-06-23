package com.back2basics.adapter.external.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDocumentRepository extends MongoRepository<TestDocument, String> {

}
