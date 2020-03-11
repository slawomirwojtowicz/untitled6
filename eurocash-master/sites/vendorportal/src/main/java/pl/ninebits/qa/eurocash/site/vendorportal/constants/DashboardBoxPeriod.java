package pl.ninebits.qa.eurocash.site.vendorportal.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DashboardBoxPeriod {
  LAST_7_DAYS("Ostatnie 7 dni"),
  LAST_14_DAYS("Ostatnie 14 dni"),
  LAST_30_DAYS("Ostatnie 30 dni");

  private String val;
}
