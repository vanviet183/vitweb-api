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

  private String name;

  private String slug;

  private String content;

  private Long amountReader;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "blog")
  @JsonIgnore
  private List<Media> medias;

  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JsonIgnore
  @JoinTable(name = "blog_category",
      joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id",
          foreignKey = @ForeignKey(name = "FK_BLOG_CATEGORY"))
  )
  private List<Category> categories;

  // list comment of blog
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "blog")
  @JsonIgnore
  private List<Comment> comments;

}
