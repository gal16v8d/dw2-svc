package com.gsdd.dw2.persistence.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @JoinColumn(
            name = "digimonId",
            referencedColumnName = "digimonId",
            foreignKey = @ForeignKey(name = "Fk_digimon"))
    private Digimon digimon;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "attackId",
            referencedColumnName = "attackId",
            foreignKey = @ForeignKey(name = "Fk_attack"))
    private Attack attack;
}
