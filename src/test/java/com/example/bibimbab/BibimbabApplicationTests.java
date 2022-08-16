package com.example.bibimbab;

import com.example.bibimbab.word.WordForm;
import com.example.bibimbab.word.WordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BibimbabApplicationTests {

    @Autowired
    private WordService wordService;

    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String name = String.format("단어:[%03d]", i);
            String meaning = String.format("뜻-[%03d]",i);
            String example = String.format("예제-[%03d]",i);
            this.wordService.create(new WordForm(name,meaning,example));
        }
    }
}
