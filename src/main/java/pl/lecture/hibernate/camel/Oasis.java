package pl.lecture.hibernate.camel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lecture.hibernate.AbstractEntity;

@Builder
@Getter
@Setter
@Entity
@Table(name = "places")
@NoArgsConstructor
@AllArgsConstructor
public class Oasis extends AbstractEntity {

  private String name;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinTable(name = "camels_places",
      joinColumns = {@JoinColumn(name = "camels_id")},
      inverseJoinColumns = {@JoinColumn(name = "places_id")})
  private List<Camel> camels = new ArrayList<>();
}
