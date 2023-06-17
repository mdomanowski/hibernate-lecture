package pl.lecture.hibernate.camel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.lecture.hibernate.AbstractEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "heads")
public class Head extends AbstractEntity {

  private int size;

  @OneToMany(mappedBy = "head", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
  private List<Eye> eyes;

  @OneToOne(mappedBy = "head", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private Camel camel;

}
