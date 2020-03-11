package pl.ninebits.qa.eurocash.site.marketplace.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ComplaintType {

  SHORT_TERM("Krótki termin ważności"),
  EXPIRED_GOODS ("Towar przeterminowany"),
  DEFECT("Wada ukryta"),
  RESIGNATION("Rezygnacja (towar oznaczony jako możliwy do zwrotu)"),
  PACKAGING_DAMAGE("Uszkodzenie opakowania"),
  NOT_SUPPLIED("Brak w dostawie"),
  SURPLUS_CORRECTION("Nadwyżka - korekta"),
  SURPLUS_REFUND("Nadwyżka - zwrot"),
  PRICE_OTHER_THAN_ON_EURCASH_MARKET("Cena inna niż w serwisie eurocash.pl Market"),
  NO_PROMOTION("Brak promocji"),
  ANOTHER_PROBLEM("Inny problem");


  private String complaintName;
  }
