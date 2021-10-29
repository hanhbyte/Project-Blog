package com.example.projectblog.api;

import com.example.projectblog.model.Account;
import com.example.projectblog.model.AccountLike;
import com.example.projectblog.model.Comment;
import com.example.projectblog.model.Friend;
import com.example.projectblog.model.Image;
import com.example.projectblog.model.Post;
import com.example.projectblog.service.account.IAccountService;
import com.example.projectblog.service.accountlike.IAccountLikeService;
import com.example.projectblog.service.comment.ICommentService;
import com.example.projectblog.service.friend.IFriendService;
import com.example.projectblog.service.image.IImageService;
import com.example.projectblog.service.jwt.JwtService;
import com.example.projectblog.service.post.IPostService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@EnableSpringDataWebSupport
public class AccountController {

  @Autowired
  IAccountService accountService;
  @Autowired
  IImageService imageService;
  @Autowired
  IPostService postService;
  @Autowired
  IAccountLikeService accountLikeService;
  @Autowired
  ICommentService commentService;
  @Autowired
  JwtService jwtService;
  @Autowired
  IFriendService friendService;


  @PostMapping("/createPost")
  public ResponseEntity<?> createPost(@RequestBody Image image) {
    Post post = image.getPost();
    Long idAc = post.getAccount().getId();
    Account account = accountService.findById(idAc).get();
    post.setAccount(account);

    Post newPost = postService.add(post);
    image.setPost(newPost);
    imageService.save(image);
    return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/findPost/{idPost}")
  public ResponseEntity<Post> findPostById(@PathVariable("idPost") Long idPost) {
    Post post = postService.findById(idPost).get();
    return new ResponseEntity<>(post, HttpStatus.OK);
  }

  @PostMapping("/timeline")
  public ResponseEntity<Page<Post>> timeline(@RequestBody String page) {
    String[] sortById = new String[2];
    Pageable pageable = PageRequest.of(Integer.parseInt(page), 5, Sort.by("id").descending());
    Page<Post> postPage = postService.findPostByPrivacyContaining("public", pageable);
    return new ResponseEntity<>(postPage, HttpStatus.OK);
  }

  @GetMapping("/likeshow/{idAcc}/{idPost}")
  public ResponseEntity<?> createlike(@PathVariable("idAcc") Long idAcc, @PathVariable("idPost") Long idPost) {
    AccountLike accountLike = accountLikeService.findByAccount_IdAndPost_Id(idAcc, idPost);
    if (accountLike != null) {
      Long idlike = accountLike.getId();
      accountLikeService.remove(idlike);
    } else {
      Account account = accountService.findById(idAcc).get();
      Post post = postService.findById(idPost).get();
      AccountLike accountLike1 = new AccountLike();
      accountLike1.setAccount(account);
      accountLike1.setPost(post);
      accountLikeService.save(accountLike1);
    }
    accountLikeService.remove(accountLike.getId());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/comment/{idAcc}/{idPost}")
  public ResponseEntity<String> createComment(@RequestBody Comment comment, @PathVariable("idAcc") Long idAcc, @PathVariable("idPost") Long idPost) {
    Account account = accountService.findById(idAcc).get();
    Post post = postService.findById(idPost).get();
    comment.setAccount(account);
    comment.setPost(post);
    commentService.save(comment);
    return new ResponseEntity<>("Ok", HttpStatus.OK);
  }

  @GetMapping("/deletecomment/{idComment}")
  public ResponseEntity<String> deleteComment(@PathVariable("idComment") Long idComment) {
    commentService.remove(idComment);
    return new ResponseEntity<>("Ok", HttpStatus.OK);
  }

  @PostMapping("/searchfriend")
  public ResponseEntity<Account> searchFriend(@RequestBody Account account) {
    Account account1 = accountService.loadUserByEmail(account.getEmail());
    if (account1 != null) {
      return new ResponseEntity<>(account1, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(account, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/sendaddfriend/{idAcc}/{idFriend}")
  public ResponseEntity<String> sendAddFriend(@PathVariable("idAcc") Long idAcc, @PathVariable("idFriend") Long idFriend) {
    Account account = accountService.findById(idAcc).get();
    Account friend = accountService.findById(idFriend).get();
    Friend friend1 = friendService.findByAccount_IdAndAccount_Id(account, friend);
    Friend friend2 = friendService.findByAccount_IdAndAccount_Id(friend, account);
    if (friend1 == null && friend2 == null) {
      Friend newFriend = new Friend();
      newFriend.setAccount(account);
      newFriend.setFriend(friend);
      newFriend.setStatus(false);
      friendService.save(newFriend);
      return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
    return new ResponseEntity<>("exits", HttpStatus.OK);
  }

  @GetMapping("/showfriend/{idAcc}")
  public ResponseEntity<List<Account>> showListFriend(@PathVariable("idAcc") Long idAcc) {
    Account account = accountService.findById(idAcc).get();
    List<Friend> list = friendService.findAllByIdAcc(account, true, account, true);
    List<Account> accountList = new ArrayList<>();
    if (list != null) {
      for (int i = 0; i < list.size(); i++) {
        if (list.get(i).getAccount().getId() == idAcc) {
          accountList.add(list.get(i).getFriend());
        } else {
          accountList.add(list.get(i).getAccount());
        }
      }
    }
    return new ResponseEntity<>(accountList, HttpStatus.OK);
  }

  @GetMapping("/showrequestfriend/{idAcc}")
  public ResponseEntity<List<Account>> showRequestFriend(@PathVariable("idAcc") Long idAcc) {
    Account account = accountService.findById(idAcc).get();
    List<Friend> list = friendService.findFriendByIdAcc(account, false);
    List<Account> accountList = new ArrayList<>();
    if (list != null) {
      for (int i = 0; i < list.size(); i++) {
        accountList.add(list.get(i).getAccount());
      }
    }
    return new ResponseEntity<>(accountList, HttpStatus.OK);
  }

  @GetMapping("/confirmfriend/{idAcc}/{idFriend}")
  public ResponseEntity<String> confirmFriend(@PathVariable("idAcc") Long idAcc, @PathVariable("idFriend") Long idFriend) {
    Account account = accountService.findById(idAcc).get();
    Account friend = accountService.findById(idFriend).get();
    Friend friend1 = friendService.findByAccount_IdAndAccount_Id(account, friend);
    Friend friend2 = friendService.findByAccount_IdAndAccount_Id(friend, account);
    if (friend1 != null) {
      friend1.setStatus(true);
      friendService.save(friend1);
    } else {
      friend2.setStatus(true);
      friendService.save(friend2);
    }
    return new ResponseEntity<>("ok", HttpStatus.OK);
  }

  @GetMapping("/showpostfriend/{idFriend}")
  public ResponseEntity<List<Post>> showPostFriend(@PathVariable("idFriend") Long idFriend) {
    List<Post> postList = postService.findAllByAccount_IdAndPrivacyIsNotContaining(idFriend, "onlyme");
    return new ResponseEntity<>(postList, HttpStatus.OK);
  }

  @GetMapping("/showuserdetail/{idAcc}")
  public ResponseEntity<List<Post>> showUserDetail(@PathVariable("idAcc") Long idAcc) {
    List<Post> postList = postService.findAllByAccount_Id(idAcc);
    return new ResponseEntity<>(postList, HttpStatus.OK);
  }

  @PutMapping("/reloadAvatar/{idAcc}")
  public ResponseEntity<String> reloadAvatar(@PathVariable("idAcc") Long idAcc, @RequestBody Image avatar) {
    Account account = accountService.findById(idAcc).get();
    Post newPost = new Post();
    newPost.setConten("Đã thay ảnh đại diện");
    newPost.setTimePost(new Date());
    newPost.setPrivacy("public");
    newPost.setAccount(account);
    Post post = postService.add(newPost);
    Image newAvatar = imageService.add(avatar);
    newAvatar.setPost(post);
    account.setAvatar(newAvatar);
    accountService.save(account);
    return new ResponseEntity<>("ok", HttpStatus.OK);
  }

  @GetMapping("/deletepost/{idPost}")
  public ResponseEntity<String> deletePost(@PathVariable Long idPost) {
    postService.remove(idPost);
    return new ResponseEntity<>("ok", HttpStatus.OK);
  }

  @DeleteMapping("/refuse/{idAcc}/{idFriend}")
  public ResponseEntity<String> refuseFriend(@PathVariable("idAcc") Long idAcc, @PathVariable("idFriend") Long idFriend) {
    Account account = accountService.findById(idAcc).get();
    Account friend = accountService.findById(idFriend).get();
    Friend f = friendService.findByAccount_IdAndAccount_Id(friend, account);
    friendService.remove(f.getId());
    return new ResponseEntity<>("ok", HttpStatus.OK);
  }
}
