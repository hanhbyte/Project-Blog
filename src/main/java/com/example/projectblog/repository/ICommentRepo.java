package com.example.projectblog.repository;

import com.example.projectblog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepo extends JpaRepository<Comment, Long> {
  @Modifying
  @Query("DELETE Comment WHERE id = ?1")
  void deleteById(Long id);
}
