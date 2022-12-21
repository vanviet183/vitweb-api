package com.vitweb.vitwebapi.domain.entities;

import com.vitweb.vitwebapi.adapter.web.base.EFavorite;
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
@Table(name = TableNameConstant.TBL_FAVORITE_ITEM)
public class FavoriteItem extends AbstractAuditingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private EFavorite type;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  private Blog blog;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  private Podcast podcast;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  private User user;

}
