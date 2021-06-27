package com.example.ex4;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository providing CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<Message,Long> {

    List<Message> findByName(String name);
    List<Message> findFirst5ByOrderByCreatedAtDesc();
    List<Message> findAllByChatContains(String word);


}
