package pl.lecture.hibernate.camel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CamelType {
  DROMADER("wielblad jednogarbny"),
  BAKTRIAN("wielblad dwugarbny"),
  BLUE_SUPERSLIMS("wielblad bezpłucny");
  private final String value;
}
