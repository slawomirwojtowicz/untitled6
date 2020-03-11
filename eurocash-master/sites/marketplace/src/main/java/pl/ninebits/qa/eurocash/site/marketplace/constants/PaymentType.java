package pl.ninebits.qa.eurocash.site.marketplace.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {

  TEST_PAYMENT("106");

  private String paymentId;
}
