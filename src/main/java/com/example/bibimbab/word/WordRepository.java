package com.example.bibimbab.word;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordRepository extends JpaRepository<Word,Integer> {
    Page<Word> findAll(Pageable pageable);
    Optional<Word> findByName(String name);
}
