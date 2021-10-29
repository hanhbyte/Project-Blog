package com.example.projectblog.service.friend;

import com.example.projectblog.model.Account;
import com.example.projectblog.model.Friend;
import com.example.projectblog.service.IGeneralService;
import java.util.List;

public interface IFriendService extends IGeneralService<Friend> {

  Friend findByAccount_IdAndAccount_Id(Account account, Account friend);
  List<Friend> findAllByIdAcc(Account account, Boolean status1, Account friend, Boolean status2);
  List<Friend> findFriendByIdAcc(Account account, Boolean status1);
}
