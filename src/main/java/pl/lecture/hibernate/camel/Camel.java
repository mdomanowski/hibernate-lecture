package pl.lecture.hibernate.camel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "camels")
@NoArgsConstructor
@AllArgsConstructor
public class Camel extends AbstractEntity {

  @Column(nullable = false, unique = true)
  private String name;

  @Column(name = "camel_color", length = 50)
  private String color;
  @Enumerated(EnumType.STRING)
  private CamelType camelType;
  @OneToMany(mappedBy = "camel", cascade = {CascadeType.MERGE, CascadeType.PERSIST,
      CascadeType.REMOVE}, orphanRemoval = true)
  private List<Leg> legs = new ArrayList<>();

  @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
  @JoinColumn(name = "head_id")
  private Head head;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinTable(name = "camels_places",
      joinColumns = {@JoinColumn(name = "places_id")},
      inverseJoinColumns = {@JoinColumn(name = "camels_id")})
  private List<Oasis> visitedOases = new ArrayList<>();
}
