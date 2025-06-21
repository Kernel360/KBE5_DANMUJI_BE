package com.back2basics.adapter.external.mongodb;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestDocumentService {

    private final TestDocumentRepository repository;

    // 저장
    public TestDocument saveTestDoc() {
        TestDocument doc = new TestDocument(null, "제목", "메시지입니다");
        return repository.save(doc);
    }

    // 조회
    public Optional<TestDocument> findById(String id) {
        return repository.findById(id);
    }
}

