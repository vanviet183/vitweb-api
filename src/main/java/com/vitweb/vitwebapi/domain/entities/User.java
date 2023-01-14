package com.vitweb.vitwebapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitweb.vitwebapi.application.constants.AuthenticationProvider;
import com.vitweb.vitwebapi.application.constants.TableNameConstant;
import com.vitweb.vitwebapi.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = TableNameConstant.TBL_USER)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractAuditingEntity {

  private String name;

  private String idName;

  private String email;

  private String password;

  private String birthday;

  private String phone;

  private String address;

  private String school;

  private String gender;

  private String avatar;

  private String grade;

  private String faculty;

  private Double coin = 0.0;

  private Double money = 0.0;

  @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  @JsonIgnore
  private List<Role> roles;

  // list following
  @JoinTable(name = "followings",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "follow_id"),
      foreignKey = @ForeignKey(name = "FK_FOLLOW"))
  @JsonIgnore
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<User> followings;

  // list courses
  @JoinTable(name = "user_course",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "course_id"),
      foreignKey = @ForeignKey(name = "FK_USER_COURSE"))
  @JsonIgnore
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Course> courses;

  // list courses
  @JoinTable(name = "user_notification",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "notification_id"),
      foreignKey = @ForeignKey(name = "FK_USER_NOTIFICATION"))
  @JsonIgnore
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Notification> notifications;

  // list podcast created by user
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  @JsonIgnore
  private List<Podcast> podcasts;

  // list lesson learned by user
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  @JsonIgnore
  private List<UserLesson> userLessons;

  // list bill of user
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  @JsonIgnore
  private List<Bill> bills;

  // list help of user
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  @JsonIgnore
  private List<Help> helps;

  // list favorite item of user
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  @JsonIgnore
  private List<FavoriteItem> favoriteItems;

  // list comment of user
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  @JsonIgnore
  private List<Comment> comments;

  // list rate of user
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  @JsonIgnore
  private List<Rate> rates;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  @JsonIgnore
  private List<Post> posts;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sender")
  @JsonIgnore
  private List<Message> messages;

  @Enumerated(EnumType.STRING)
  private AuthenticationProvider authProvider;

  private Boolean status = Boolean.FALSE;

  @JsonIgnore
  private String tokenResetPass;

  public User(String email, String password, String name,
              List<Role> roles, AuthenticationProvider authProvider, Boolean status) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.roles = roles;
    this.authProvider = authProvider;
    this.status = status;
  }

}
