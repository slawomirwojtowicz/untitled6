package pl.ninebits.qa.automated.tests.site.commons;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class DocumentReadyStateCondition implements ExpectedCondition<Boolean> {

    public Boolean apply(WebDriver driver) {
        String readyState = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState");
        return readyState.equals("complete");
    }
}
