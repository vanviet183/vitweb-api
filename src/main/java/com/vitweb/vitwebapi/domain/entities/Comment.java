package com.vitweb.vitwebapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitweb.vitwebapi.application.constants.TableNameConstant;
import com.vitweb.vitwebapi.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNameConstant.TBL_COMMENT)
public class Comment extends AbstractAuditingEntity {

  private String content;

  private String mediaUrl;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JsonIgnore
  private Blog blog;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JsonIgnore
  private Post post;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JsonIgnore
  private Lesson lesson;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JsonIgnore
  private User user;

  // Comment parent
  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = "parent_comment", foreignKey = @ForeignKey(name = "FK_COMMENT_PARENT_CHILDREN"))
  private Comment parentComment;

  // List comment children
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentComment")
  private List<Comment> childrenComments;
}
