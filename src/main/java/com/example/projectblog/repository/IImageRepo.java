package com.example.projectblog.repository;

import com.example.projectblog.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageRepo extends JpaRepository<Image, Long> {
}
