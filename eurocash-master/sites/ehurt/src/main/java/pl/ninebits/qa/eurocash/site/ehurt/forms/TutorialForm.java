package pl.ninebits.qa.eurocash.site.ehurt.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;

import java.text.MessageFormat;
import java.util.List;

public class TutorialForm extends EhurtBaseForm {
  public TutorialForm(BasePage page) {
    super(page);
  }

  public String getTutorialNumberOfSteps() {
    String numberOfSteps = getTextFromElement(By.xpath("//span[contains(@class,'currentStepNumber')]"));
    numberOfSteps = numberOfSteps.substring(numberOfSteps.indexOf("/") + 1).trim();

    return numberOfSteps;
  }

  public void checkFormVisible(boolean shouldBeVisible, List<String> errors) {
    boolean formPresent = isElementPresent(By.xpath("//div[@class='introjs-tooltip']"));

    if (shouldBeVisible && !formPresent) {
      Assert.fail("Tutorial powinien byc widoczny a nie jest");
    }
    if (!shouldBeVisible && formPresent) {
      errors.add("Tutorial nie powinien być widoczny a jest");
    }
  }

  public void checkCurrentStepPresent(int expectedStep, List<String> errors) {
    sleep(1000);
    String currentTutorialStep = getCurrentTutorialStep();

    if (!currentTutorialStep.equals(String.valueOf(expectedStep))) {
      errors.add(MessageFormat.format("W Tutorialu oczekiwano stepa {0} a wyświetla się {1}", expectedStep, currentTutorialStep));
    }
  }

  private String getCurrentTutorialStep() {
    String stepNumber = getTextFromElement(By.xpath("//span[contains(@class,'currentStepNumber')]"));

    return stepNumber.substring(0, stepNumber.indexOf("/"));
  }

  public void checkAreaHighlighted(int stepNumber, List<String> errors) {
    if (!isElementPresent(By.xpath("//div[contains(@class,'introjs-tooltipReferenceLayer')]"))) {
      errors.add(MessageFormat.format("Brak podświetlonego obszaru dla kroku {0}", stepNumber));
    }
  }

  public void clickNextBtn(int nextStepNumber) {
    WebElement nextBtn = findClickableElement(By.xpath("//a[contains(@class,'nextbutton')]"));
    nextBtn.click();
    waitForElementToBeVisible(By.xpath("//span[contains(@class,'introjs-helperNumberLayer') and contains(text(),'" + nextStepNumber + "')]"));
  }

  public void clickCloseBtn() {
    WebElement closeBtn = findClickableElement(By.xpath("//a[contains(@class,'donebutton')]"));
    closeBtn.click();
    waitForElementToBeInvisible(closeBtn);
    waitForOverlayToBeInvisible();
  }

  public void clickPreviousStepBtn() {
    WebElement prevStepBtn = findClickableElement(By.xpath("//a[contains(@class,'prevbutton')]"));
    prevStepBtn.click();
    waitForElementToBeInvisible(By.xpath("//a[contains(@class,'donebutton')]"));
  }

  public void clickWatchLaterBtn() {
    WebElement watchLaterBtn = findClickableElement(By.xpath("//a[contains(@class,'skipbutton')]"));
    watchLaterBtn.click();
    waitForElementToBeInvisible(watchLaterBtn);
    waitForOverlayToBeInvisible();
  }

  private void waitForOverlayToBeInvisible() {
    waitForElementToBeInvisible(By.xpath("//div[@class='introjs-overlay')]"));
    sleep();
  }
}
