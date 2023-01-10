package com.vitweb.vitwebapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitweb.vitwebapi.application.constants.TableNameConstant;
import com.vitweb.vitwebapi.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNameConstant.TBL_PODCAST)
public class Podcast extends AbstractAuditingEntity {

  private String path;

  private String name;

  private String slug;

  private String description;

  private String author;

  private Integer type;

  private Integer amountLiked;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JsonIgnore
  private User user;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  private Category category;

  // list favorite item of podcast
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "podcast")
  @JsonIgnore
  private List<FavoriteItem> favoriteItems;

}
