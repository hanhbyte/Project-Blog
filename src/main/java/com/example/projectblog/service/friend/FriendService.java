package com.example.projectblog.service.friend;

import com.example.projectblog.model.Account;
import com.example.projectblog.model.Friend;
import com.example.projectblog.repository.IFriendRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService implements IFriendService{

  @Autowired
  private IFriendRepo friendRepo;

  @Override
  public Iterable<Friend> findAll() {
    return friendRepo.findAll();
  }

  @Override
  public Optional<Friend> findById(Long id) {
    return friendRepo.findById(id);
  }

  @Override
  public void save(Friend friend) {
    friendRepo.save(friend);
  }

  @Override
  public void remove(Long id) {
    friendRepo.deleteById(id);
  }


  @Override
  public Friend findByAccount_IdAndAccount_Id(Account account, Account friend) {
    return friendRepo.findByAccount_IdAndAccount_Id(account,friend);
  }

  @Override
  public List<Friend> findAllByIdAcc(Account account, Boolean status1, Account friend, Boolean status2) {
    return friendRepo.findAllByIdAcc(account,status1,friend,status2);
  }

  @Override
  public List<Friend> findFriendByIdAcc(Account account, Boolean status1) {
    return friendRepo.findFriendByIdAcc(account,status1);
  }

}
