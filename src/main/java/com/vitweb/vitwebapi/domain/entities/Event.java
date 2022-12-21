package com.vitweb.vitwebapi.domain.entities;

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
@Table(name = TableNameConstant.TBL_EVENT)
public class Event extends AbstractAuditingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private String name;

  private String description;

  private String expired;

}
