package pl.ninebits.qa.eurocash.site.vendorportal.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {

  ACTIVE("20", "Aktywny"),
  INACTIVE("30", "Nieaktywny"),
  REJECTED("90", "Odrzucony"),
  SAVED("1", "Zapisany"),
  TO_BE_MADE_IN_DP("15", "Do złożenia w DP"),
  IN_VERIFICATION("10", "W weryfikacji");

  private String id;
  private String name;
}
