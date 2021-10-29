package com.example.projectblog.repository;

import com.example.projectblog.model.Account;
import com.example.projectblog.model.Friend;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFriendRepo extends JpaRepository<Friend, Long> {

  @Query("select f from Friend f where f.account = ?1 and  f.friend = ?2")
  Friend findByAccount_IdAndAccount_Id(Account account, Account friend);


  @Query("select f from Friend f where f.account = ?1 and f.status = ?2 or f.friend = ?3 and f.status = ?4")
  List<Friend> findAllByIdAcc(Account account, Boolean status1, Account friend, Boolean status2);

  @Query("select f from Friend f where f.friend = ?1 and f.status = ?2")
  List<Friend> findFriendByIdAcc(Account account, Boolean status1);
}
