package com.example.projectblog.service.accountlike;

import com.example.projectblog.model.AccountLike;
import com.example.projectblog.service.IGeneralService;

public interface IAccountLikeService extends IGeneralService<AccountLike> {

  AccountLike findByAccount_IdAndPost_Id(Long idAcc,Long idPost);
  public void delete(AccountLike accountLike);
}
