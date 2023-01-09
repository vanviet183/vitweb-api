package com.vitweb.vitwebapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.vitweb.vitwebapi.application.constants.TableNameConstant;
import com.vitweb.vitwebapi.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNameConstant.TBL_BLOG)
public class Blog extends AbstractAuditingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private String subject;

  private String slug;

  private String content;

  private String images;

  private Long amountReader;

  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  private List<Category> categories;

  // list favorite item of user
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "blog")
  @JsonIgnore
  private List<FavoriteItem> favoriteItems;

  // list comment of blog
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "blog")
  @JsonIgnore
  private List<Comment> comments;

}
