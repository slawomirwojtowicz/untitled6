package pl.ninebits.qa.eurocash.site.marketplace.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Delivery {

  NESTLE("NESTLE_2"),
  KRUGER("KRUGER_2");

  private String deliveyName;
}
