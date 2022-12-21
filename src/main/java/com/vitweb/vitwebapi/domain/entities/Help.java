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
import javax.validation.constraints.Email;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNameConstant.TBL_HELP)
public class Help extends AbstractAuditingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Nationalized
  private String fullName;

  @Email
  private String email;

  @Nationalized
  private String content;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JsonIgnore
  private User user;

}
