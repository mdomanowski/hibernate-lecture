package pl.lecture.hibernate.camel;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/camels")
public class CamelController {

  private final CamelRepository camelRepository;

  @Transactional
  @PostMapping
  public ResponseEntity<?> createCamel() {
//     NEW
    Head head = Head.builder().size(10).build();
    head.setEyes(List.of(
        Eye.builder().color("green").head(head).build(),
        Eye.builder().color("green").head(head).build()));
//     NEW
    Camel camel = Camel.builder()
        .name("camel1")
        .camelType(CamelType.DROMADER)
        .color("brown")
        .head(head)
        .build();
//                  MANAGED
    camelRepository.save(camel);
    camel.setLegs(List.of(Leg.builder().camel(camel).build()));

    return ResponseEntity.ok().build();
  }
  // DETACHED

  @PostMapping("/{camelId}:update-no-transaction")
  public ResponseEntity<?> updateCamelNoTransactional(@PathVariable("camelId") Long camelId) {
    Optional<Camel> optionalOfCamel = camelRepository.findById(camelId);
    if (optionalOfCamel.isEmpty()) {
      throw new RuntimeException();
    }
    Camel camel = optionalOfCamel.get();
    camel.setCamelType(CamelType.BLUE_SUPERSLIMS);
    camel.setName("c a m e l l i n i");

    camelRepository.save(camel);

    return ResponseEntity.ok().build();
  }
  // DETACHED

  @Transactional
  @PostMapping("/{camelId}:update-transaction")
  public ResponseEntity<?> updateCamelTransactional(@PathVariable("camelId") Long camelId) {
    // MANAGED
    Optional<Camel> optionalOfCamel = camelRepository.findById(camelId);
    if (optionalOfCamel.isEmpty()) {
      throw new RuntimeException();
    }
    Camel camel = optionalOfCamel.get();
    camel.setCamelType(CamelType.BAKTRIAN);
    camel.setName("CAMELCAMEL");

    return ResponseEntity.ok().build();
  }
  // DETACHED

  @Transactional
  @PostMapping("/visit")
  public ResponseEntity<?> addCamelsToVisitedPlaces() {
    // NEW
    Camel camel1 = Camel.builder()
        .name("camel1")
        .camelType(CamelType.DROMADER)
        .visitedOases(new ArrayList<>())
        .build();
    Camel camel2 = Camel.builder()
        .name("camel2")
        .camelType(CamelType.BAKTRIAN)
        .visitedOases(new ArrayList<>())
        .build();
    Camel camel3 = Camel.builder()
        .name("camel3")
        .camelType(CamelType.BLUE_SUPERSLIMS)
        .visitedOases(new ArrayList<>())
        .build();

    // MANAGED
    camelRepository.save(camel1);
    camelRepository.save(camel2);
    camelRepository.save(camel3);

    Oasis oasis1 = Oasis.builder().name("duza oaza").build();
    Oasis oasis2 = Oasis.builder().name("mala oaza").build();

    camel1.getVisitedOases().add(oasis1);
    camel1.getVisitedOases().add(oasis2);
    camel2.getVisitedOases().add(oasis1);
    camel3.getVisitedOases().add(oasis2);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{camelId}")
  public ResponseEntity<?> deleteCamel(@PathVariable("camelId") Long camelId) {
    // MANAGED
    Optional<Camel> optionalOfCamel = camelRepository.findById(camelId);
    if (optionalOfCamel.isEmpty()) {
      throw new RuntimeException();
    }

    Camel camel = optionalOfCamel.get();
    camelRepository.delete(camel);

    return ResponseEntity.noContent().build();
  }

  @Transactional
  @PostMapping("/orphanRemoval")
  public ResponseEntity<?> deleteCamel() {
    Optional<Camel> optionalOfCamel = camelRepository.findById(1L);
    if (optionalOfCamel.isEmpty()) {
      throw new RuntimeException();
    }
    Camel camel = optionalOfCamel.get();
    camel.getLegs().remove(0);

    return ResponseEntity.ok().build();
  }
}
