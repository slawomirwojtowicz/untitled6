package pl.ninebits.qa.automated.tests.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Map;

public class NumberUtils {

  public static Double roundNumber(double number, int decimalPlaces, RoundingMode roundingMode) {
    return new BigDecimal(number).setScale(decimalPlaces, roundingMode).doubleValue();
  }

  public static Double getMinValueFromMap(Map<String, Double> numbers) {
    return Collections.min(numbers.values());
  }

  public static void replaceValue(Map<String, Double> numbers, Double oldVal, Double newVal) {
    for (Map.Entry<String, Double> entry : numbers.entrySet()) {
      String k = entry.getKey();
      Double v = entry.getValue();
      if (v.equals(oldVal)) {
        entry.setValue(newVal);
      }
    }
  }

  public static String getKeyForGivenValue(Map<String, Double> products, Double value) throws Exception {
    String productId = "";
    for (Map.Entry<String, Double> entry : products.entrySet()) {
      if (entry.getValue().equals(value)) {
        productId = entry.getKey();
      }
    }

    if (!productId.isEmpty()) {
      return productId;
    } else {
      throw new Exception("Nie pobrano z mapy szukanego id produktu");
    }
  }
}
