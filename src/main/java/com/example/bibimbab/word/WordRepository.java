package com.example.bibimbab.word;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word,Integer> {
    Page<Word> findAll(Pageable pageable);
}
