package com.gsdd.dw2.persistence.entities;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Generated
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AttackTargetType {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "attackTargetTypeId")
    private Long attackTargetTypeId;

    @NotEmpty(message = "attack target type name should not be empty")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "attackTargetType", fetch = FetchType.LAZY)
    private Set<Attack> attacks;
}
