package com.example.projectblog.api;

import com.example.projectblog.dto.LoginAccount;
import com.example.projectblog.model.Account;
import com.example.projectblog.model.AppRole;
import com.example.projectblog.model.Image;
import com.example.projectblog.model.VerifiAccount;
import com.example.projectblog.service.account.IAccountService;
import com.example.projectblog.service.approle.IAppRoleService;
import com.example.projectblog.service.image.IImageService;
import com.example.projectblog.service.jwt.JwtService;
import com.example.projectblog.service.post.IPostService;
import com.example.projectblog.service.verifi.IVerifiService;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
public class SignupController {

  @Autowired
  IAccountService accountService;
  @Autowired
  IAppRoleService appRoleService;
  @Autowired
  IImageService imageService;
  @Autowired
  IPostService postService;
  @Autowired
  JwtService jwtService;
  @Autowired
  IVerifiService verifyService;


  @PostMapping("/signup")
  public ResponseEntity<String> createAcc(@Valid @RequestBody Account account) {
    String message = "";
    AppRole role = appRoleService.findById(2L).get();
    Image image = imageService.findById(1L).get();
    account.setAvatar(image);
    account.setRole(role);
    account.setEnable(false);
    if (accountService.add(account)) {
      String str = "GP Coder";
      Account acc = accountService.loadUserByEmail(account.getEmail());
      String token = Base64.getEncoder().encodeToString(account.getEmail().getBytes());
      VerifiAccount verifiAccount = new VerifiAccount();
      verifiAccount.setIdAcc(acc.getId());
      verifiAccount.setToken(token);
      VerifiAccount newVerifi = verifyService.add(verifiAccount);
      SimpleMailMessage sendmail = new SimpleMailMessage();
      sendmail.setTo(account.getEmail());
      sendmail.setSubject("Bấm vào link bên dưới để xác thực email!");
      sendmail.setText(
          "https://vilo-vn.herokuapp.com/account/verification/" + newVerifi.getId() + "/"
              + acc.getId() + "?token=" + token);
      javaMailSender.send(sendmail);
      message = "Check email to verification!";
    } else {
      message = "account exited!";
    }
    return new ResponseEntity<>(message, HttpStatus.CREATED);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(HttpServletRequest request, @RequestBody Account account) {
    String result = "";
    HttpStatus httpStatus = null;
    try {
      if (accountService.checkLogin(account)) {
        result = jwtService.generateTokenLogin(account.getEmail());
        Account account1 = accountService.loadUserByEmail(account.getEmail());
        LoginAccount loginAccount = new LoginAccount();
        loginAccount.setId(account1.getId());
        loginAccount.setFullName(account1.getFullName());
        loginAccount.setAvatar(account1.getAvatar());
        loginAccount.setToken(result);
        return new ResponseEntity(loginAccount, HttpStatus.OK);
      } else {
        result = "Wrong email or password or not verification";
        httpStatus = HttpStatus.BAD_REQUEST;
      }
    } catch (Exception ex) {
      result = "Server Error";
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(result, httpStatus);
  }

  @PutMapping("/addroleandimage")
  public ResponseEntity<String> fixRoleAndDefaultAvatar() {
    List<AppRole> appRoles = (List<AppRole>) appRoleService.findAll();
    List<Image> images = (List<Image>) imageService.findAll();
    if (appRoles.size() == 0) {
      AppRole admin = new AppRole();
      admin.setId(1L);
      admin.setRole("ROLE_ADMIN");
      AppRole user = new AppRole();
      user.setRole("ROLE_USER");
      user.setId(2L);
      appRoleService.save(admin);
      appRoleService.save(user);
    }
    if (images.size() == 0) {
      Image image = new Image();
      image.setId(1L);
      image.setPath(
          "https://firebasestorage.googleapis.com/v0/b/filebase-70567.appspot.com/o/images%2F84156601_1148106832202066_479016465572298752_o.jpg?alt=media&token=4ca2d074-2b3b-4524-a017-e951067fa3f5");
      imageService.save(image);
    }
    return new ResponseEntity<>("ok", HttpStatus.OK);
  }

  @Autowired
  JavaMailSender javaMailSender;

  @GetMapping("/verification/{id}/{idAcc}")
  public ResponseEntity<String> verification(@RequestParam("token") String token,
      @PathVariable("id") Long id, @PathVariable("idAcc") Long idAcc) {
    VerifiAccount verifiAccount = verifyService.findById(id).get();
    Account account = accountService.findById(idAcc).get();
    if (verifiAccount.getToken().equals(token)) {
      account.setEnable(true);
      accountService.save(account);
      return new ResponseEntity<>("Bấm vào link để đăng nhập https://fakeface.netlify.app/",
          HttpStatus.OK);
    }
    return new ResponseEntity<>("Xác thực hết hiệu lực!", HttpStatus.OK);
  }
}
