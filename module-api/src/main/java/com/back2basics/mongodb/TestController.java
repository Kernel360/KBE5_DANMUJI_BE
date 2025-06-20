package com.back2basics.mongodb;

import com.back2basics.adapter.external.mongodb.TestDocument;
import com.back2basics.adapter.external.mongodb.TestDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestDocumentService testService;

    @PostMapping("/mongo/test")
    public String testInsert() {
        testService.saveTestDoc();
        return "삽입 완료";
    }

    @GetMapping("/mongo/test/{id}")
    public TestDocument getDoc(@PathVariable String id) {
        return testService.findById(id)
            .orElseThrow(() -> new RuntimeException("문서 없음"));
    }
}
