package pl.ninebits.qa.eurocash.site.vendorportal.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PromotionStatus {

  ACTIVE("Aktywna", "AKTYWNA"),
  PENDING("Oczekująca", "OCZEKUJE"),
  ENDED("Zakończona", "ZAKOŃCZONA");

  private String name;
  private String tableName;
}
