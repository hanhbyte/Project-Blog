package com.example.projectblog.service.post;

import com.example.projectblog.model.Post;
import com.example.projectblog.service.IGeneralService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostService extends IGeneralService<Post> {

  List<Post> findAllByAccount_Id(Long idAcc);
  Post add(Post post);
  Page<Post> findAll(Pageable pageable);
  Page<Post> findPostByPrivacyContaining(String optional, Pageable pageable);
  List<Post> findAllByAccount_IdAndPrivacyIsNotContaining(Long idAcc, String privacy);
}
