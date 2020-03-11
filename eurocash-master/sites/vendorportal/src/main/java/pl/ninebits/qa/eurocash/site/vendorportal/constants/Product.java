package pl.ninebits.qa.eurocash.site.vendorportal.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Product {

  STOCK_VODKA("5902573004551", "WÓDKA STOCK PRESTIGE 40% 0,50L", "WODKA STOCK PRESTIGE 40% 500ML", 29.99),
  JACK_DANIELS("5099873090442", "WHISKY JACK DANIEL`S 40% 1,00L", "WHISKEY JACK DANIEL'S 40% 1L BROWN-FORMAN", 95.99),
  POLISH_SAUSAGES("5908230524141", "KABANOS NA RAZ WIEPRZOWY TARCZYŃSKI 25G", "KABANOS NA RAZ WIEPRZOWY 25G TARCZYNSKI", 1.50);

  private String EAN;
  private String name;
  private String marketName;
  private Double price;
}
