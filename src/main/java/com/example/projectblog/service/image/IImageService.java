package com.example.projectblog.service.image;

import com.example.projectblog.model.Image;
import com.example.projectblog.service.IGeneralService;

public interface IImageService extends IGeneralService<Image> {

  Image add(Image post);

}
