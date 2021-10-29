package com.example.projectblog.service.comment;

import com.example.projectblog.model.Comment;
import com.example.projectblog.repository.ICommentRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements ICommentService {

  @Autowired
  private ICommentRepo commentRepo;

  @Override
  public Iterable<Comment> findAll() {
    return commentRepo.findAll();
  }

  @Override
  public Optional<Comment> findById(Long id) {
    return commentRepo.findById(id);
  }

  @Override
  public void save(Comment comment) {
    commentRepo.save(comment);
  }

  @Override
  public void remove(Long id) {
    commentRepo.deleteById(id);
  }
}
