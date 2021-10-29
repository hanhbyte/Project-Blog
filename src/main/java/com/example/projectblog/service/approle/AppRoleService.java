package com.example.projectblog.service.approle;

import com.example.projectblog.model.AppRole;
import com.example.projectblog.repository.IAppRoleRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppRoleService implements IAppRoleService {

  @Autowired
  private IAppRoleRepo appRoleRepo;

  @Override
  public Iterable<AppRole> findAll() {
    return appRoleRepo.findAll();
  }

  @Override
  public Optional<AppRole> findById(Long id) {
    return appRoleRepo.findById(id);
  }

  @Override
  public void save(AppRole appRole) {
    appRoleRepo.save(appRole);
  }

  @Override
  public void remove(Long id) {
    appRoleRepo.deleteById(id);
  }
}
