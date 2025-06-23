package com.back2basics.adapter.persistence.history;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryDocumentRepository extends MongoRepository<HistoryDocument, String> {

}
