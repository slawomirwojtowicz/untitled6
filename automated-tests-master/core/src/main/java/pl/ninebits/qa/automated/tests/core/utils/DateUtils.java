package pl.ninebits.qa.automated.tests.core.utils;

import org.testng.Assert;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

  public static String changeDateFromSearchToGridFormat(String date) {
    String newDate = "";
    date = date.replaceAll("-", "");
    if (date.length() == 8) {
      newDate = date.substring(6, 8) + "-" + date.substring(4, 6) + "-" + date.substring(0, 4);
    } else if (date.length() == 10) {
      newDate = date.substring(8, 10) + date.substring(4) + date.substring(5, 7) + date.substring(7) + date.substring(0, 4);
    } else {
      Assert.fail(MessageFormat.format("Data {0} ma nieprawidłową długość", date));
    }
    return newDate;
  }

  public static String changeDateFromGridToSearchFormat(String date) {
    String newDate = "";
    date = date.replaceAll("-", "");
    if (date.length() == 8) {
      newDate = date.substring(4, 8) + "-" + date.substring(2, 4) + "-" + date.substring(0, 2);
    } else if (date.length() == 10) {
      newDate = date.substring(6, 10) + date.substring(2) + date.substring(3, 5) + date.substring(5) + date.substring(0, 2);
    } else {
      Assert.fail(MessageFormat.format("Data {0} ma nieprawidłową długość", date));
    }
    return newDate;
  }

  public static String monthPrefixInCalendarToNumber(String monthPrefix) {
    List<String> monthPrefixes = Arrays.asList(
      "sty", "lut", "mar", "kwi", "maj", "cze", "lip", "sie", "wrz", "paź", "lis", "gru"
    );
    String monthNumber = Integer.toString(monthPrefixes.indexOf(monthPrefix) + 1);
    if (monthNumber.length() == 1) {
      return "0" + monthNumber;
    } else {
      return monthNumber;
    }
  }

  public static String monthInCalendarToNumber(String monthPrefix) {
    List<String> monthPrefixes = Arrays.asList(
      "styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"
    );
    String monthNumber = Integer.toString(monthPrefixes.indexOf(monthPrefix) + 1);
    if (monthNumber.length() == 1) {
      return "0" + monthNumber;
    } else {
      return monthNumber;
    }
  }

  public static String getCurrentDate() {
    return new SimpleDateFormat("yyyyMMdd").format(new Date());
  }

  public static String getCurrentDate(String pattern) {
    return new SimpleDateFormat(pattern).format(new Date());
  }

  public static String getCurrentDateDashFormat() {
    return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
  }

  public static String getPastDate(int daysBackward) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -daysBackward);
    return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
  }

  public static String getPastDateDashFormat(int daysBackward) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -daysBackward);
    return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
  }

  public static String getPastDateDashFormatMP(int daysBackward) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -daysBackward);
    return new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
  }

  public static String getLaterDateDashFormat(int daysBackward) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, +daysBackward);
    return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
  }

  public static String getLaterDateDashFormatMP(int daysBackward) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, +daysBackward);
    return new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
  }

  public static Date getDate(Date date, int days) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DATE, days);
    return cal.getTime();
  }
}
