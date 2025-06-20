package com.back2basics.adapter.external.mongodb;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document("test_document")
@AllArgsConstructor
public class TestDocument {

    @Id
    private String id; // string이면 자동지정, Long으로하면 생성 시 직접 지정해줘야하는듯
    private String title;
    private String message;
}
