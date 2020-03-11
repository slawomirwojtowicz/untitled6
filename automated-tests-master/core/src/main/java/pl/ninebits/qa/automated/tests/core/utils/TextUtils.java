package pl.ninebits.qa.automated.tests.core.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import java.text.Collator;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

  public static String stripAccents(String text) {
    if (text.contains("ł")) {
      text = text.replaceAll("ł", "l");
    }
    if (text.contains("Ł")) {
      text = text.replaceAll("Ł", "L");
    }

    return StringUtils.stripAccents(text);
  }

  public static void sortListWithPolishChars(List<String> words) {
    Collator coll = Collator.getInstance(Locale.getDefault());
    coll.setStrength(Collator.PRIMARY);
    words.sort(coll);
  }

  public static List<String> getTextFromWebElementsList(List<WebElement> elements) throws Exception {
    List<String> dump = new ArrayList<>();
    if (!elements.isEmpty()) {
      for (WebElement element : elements) {
        dump.add(element.getText().trim());
      }
      TextUtils.clearNullsFromList(dump);
    } else {
      throw new Exception("List is empty");
    }

    return dump;
  }

  public static List<String> getAttributesFromWebElementsListWithoutTheQuotes(List<WebElement> elements, String attribute) {
    List<String> dump = new ArrayList<>();
    for (WebElement element : elements) {
      dump.add(element.getAttribute(attribute).replaceAll("[\"]", "").replaceAll("_", ""));
    }
    TextUtils.clearNullsFromList(dump);

    return dump;
  }

  public static List<String> getAttributesFromWebElementsList(List<WebElement> elements, String attribute) {
    List<String> dump = new ArrayList<>();
    for (WebElement element : elements) {
      dump.add(element.getAttribute(attribute));
    }
    TextUtils.clearNullsFromList(dump);

    return dump;
  }

  public static List<String> capitalizeStringsOnList(List<String> data) {
    List<String> capitalizedList = new ArrayList<>();

    for (String dataString : data) {
      dataString = dataString.toUpperCase();
      capitalizedList.add(dataString);
    }

    return capitalizedList;
  }

  public static List<String> lowerCaseStringsOnList(List<String> data) {
    List<String> loweredList = new ArrayList<>();

    for (String dataString : data) {
      dataString = dataString.toLowerCase();
      loweredList.add(dataString);
    }

    return loweredList;
  }

  public static void clearNullsFromList(List<String> list) {
    list.removeAll(Arrays.asList("", null));
  }

  public static String generatePassword(int length) {
    return RandomStringUtils.random(length, true, true) + "@!";
  }

  public static String generate9bLogin(int length) {
    String login = MessageFormat.format("cm_smyk+testy-qa-{0}@9bits.pl",
      RandomStringUtils.random(length, true, true));
    return login.toLowerCase();
  }

  public static String getArticleIdFromHref(String href) {
    String articleId = href;
    Pattern pattern = Pattern.compile("(?<=,)[^,]+[.^]");
    Matcher matcher = pattern.matcher(articleId);

    if (matcher.find()) {
      articleId = matcher.group();
    }
    return articleId.replaceAll("[\\D]", "");
  }

  public static String standardPolishAndSpecialCharacters() {
    return "qwertyuiopasdfghjklzxcvbnm QWERTYUIOPASDFGHJKLZXCVBNM ęóąśłżźćń ĘÓĄŚŁŻŹĆŃ `1234567890-= !@#$%^&*()_+ [];\\,./ {}:\"|<>? €";
  }

  public static List<String> getReversedList(List<String> listToReverse) {
    Collections.reverse(listToReverse);
    return listToReverse;
  }

  public static String priceToStringReadyToBeDoubleFormat(String productPrice) {
    return productPrice
      .replaceAll("[\\s\\u00A0A-Za-zęóąśłżćń\":]", "")
      .replaceAll(",", ".");
  }
}
