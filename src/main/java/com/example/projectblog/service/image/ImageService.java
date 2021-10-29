package com.example.projectblog.service.image;

import com.example.projectblog.model.Image;
import com.example.projectblog.repository.IImageRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService implements IImageService{

  @Autowired
  private IImageRepo iImageRepo;
  @Override
  public Iterable<Image> findAll() {
    return iImageRepo.findAll();
  }

  @Override
  public Optional<Image> findById(Long id) {
    return iImageRepo.findById(id);
  }

  @Override
  public void save(Image image) {
    iImageRepo.save(image);
  }

  @Override
  public void remove(Long id) {
    iImageRepo.deleteById(id);
  }

  @Override
  public Image add(Image post) {
    return iImageRepo.save(post);
  }
}
