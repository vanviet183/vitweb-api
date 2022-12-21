package com.vitweb.vitwebapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitweb.vitwebapi.application.constants.TableNameConstant;
import com.vitweb.vitwebapi.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNameConstant.TBL_IMAGE)
public class Image extends AbstractAuditingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private String path;

  private Integer type;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JsonIgnore
  private Blog blog;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JsonIgnore
  private Comment comment;
}
