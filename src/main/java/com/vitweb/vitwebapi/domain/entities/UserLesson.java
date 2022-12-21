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
@Table(name = TableNameConstant.TBL_USER_LESSON, uniqueConstraints = {
    @UniqueConstraint(columnNames = "user_id"),
    @UniqueConstraint(columnNames = "lesson_id")
})
public class UserLesson extends AbstractAuditingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private Double mark;

  private Boolean attendance;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id")
  @JsonIgnore
  private Lesson lesson;
}
