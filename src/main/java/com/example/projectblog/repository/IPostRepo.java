package com.example.projectblog.repository;

import com.example.projectblog.model.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepo extends JpaRepository<Post, Long> {

  Page<Post> findAll(Pageable pageable);
  Page<Post> findPostByPrivacyContaining(String s, Pageable pageable);
  List<Post> findAllByAccount_IdAndPrivacyIsNotContaining(Long idAcc, String privacy);
  List<Post> findAllByAccount_Id(Long idAcc);
}
