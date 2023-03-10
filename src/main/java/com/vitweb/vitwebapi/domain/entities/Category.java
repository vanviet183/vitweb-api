package com.vitweb.vitwebapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitweb.vitwebapi.application.constants.TableNameConstant;
import com.vitweb.vitwebapi.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNameConstant.TBL_CATEGORY)
public class Category extends AbstractAuditingEntity {

  @Nationalized
  private String name;

  private String slug;

  private String description;

  @JoinTable(name = "category_blog",
      joinColumns = @JoinColumn(name = "category_id"),
      inverseJoinColumns = @JoinColumn(name = "blog_id"),
      foreignKey = @ForeignKey(name = "FK_CATEGORY_BLOG"))
  @JsonIgnore
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Blog> blogs;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "category")
  @JsonIgnore
  private List<Course> courses;

}
