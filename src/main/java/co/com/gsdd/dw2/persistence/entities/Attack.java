package co.com.gsdd.dw2.persistence.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

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
public class Attack {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "attackId")
	private Long attackId;
	
	@NotEmpty(message = "attack name should not be empty")
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "attackTypeId", referencedColumnName = "attackTypeId", foreignKey = @ForeignKey(name = "Fk_attack_type"))
	private AttackType attackType;
	
	@PositiveOrZero(message = "Magical points (MP) should be positive")
	@Column(name = "mp", nullable = false)
	private Integer mp;

}
