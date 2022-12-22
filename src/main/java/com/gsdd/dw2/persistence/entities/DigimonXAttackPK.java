package com.gsdd.dw2.persistence.entities;

import java.io.Serializable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DigimonXAttackPK implements Serializable {

  private static final long serialVersionUID = -5991204361564728088L;

  @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "digimonId", referencedColumnName = "digimonId",
      foreignKey = @ForeignKey(name = "Fk_digimon"))
  private Digimon digimon;

  @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "attackId", referencedColumnName = "attackId",
      foreignKey = @ForeignKey(name = "Fk_attack"))
  private Attack attack;
}
