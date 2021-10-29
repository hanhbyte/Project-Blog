package com.example.projectblog.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Data
@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String conten;
  private String privacy;
  private Date timePost;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
  @LazyCollection(LazyCollectionOption.FALSE)
  @JsonManagedReference
  private List<Image> imageList;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JsonManagedReference
  private List<AccountLike> likeList;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JsonManagedReference
  private List<Comment> commentList;

  @ManyToOne
  private Account account;

  @Override
  public String toString() {
    return "";
  }
}
