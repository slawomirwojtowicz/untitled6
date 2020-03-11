package pl.ninebits.qa.eurocash.site.vendorportal.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PromotionType {

  QUOTA("Rabat kwotowy"),
  PERCENT("Rabat procentowy"),
  PROMO_CODE("Kod rabatowy");

  private String name;
}
