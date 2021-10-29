package com.example.projectblog.service.verifi;

import com.example.projectblog.model.VerifiAccount;
import com.example.projectblog.service.IGeneralService;

public interface IVerifiService extends IGeneralService<VerifiAccount> {
  VerifiAccount add(VerifiAccount verifiAccount);

}
