package pl.ninebits.qa.eurocash.site.marketplace.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductName {
  LOG_KRASYSTAW("ZEST.MOTYLEK BŁĘKITNY SPÓD+SKRZ+OPA+RÓŻ."),
  LOG_NESTLE2("MLEKO UHT LACIATE ZIELONE 0,5% 500ML MLEKPOL"),
  LOG_ZYWIEC("JOGURT PIĄTUŚ MAILNA 125G"),
  LOG_KRUGER("KONCENTRAT NAPOJU SUNQUICK MARAKUJA 580ML"),
  LOG_EUROCASH("SOS WINIARY AZJATYCKI 250ML");

 private String name;
}
