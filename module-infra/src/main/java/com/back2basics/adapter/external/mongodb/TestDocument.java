package com.back2basics.adapter.external.mongodb;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document("test_document")
@AllArgsConstructor
public class TestDocument {

    @Id
    private ObjectId id;
    private String title;
    private String message;
}
