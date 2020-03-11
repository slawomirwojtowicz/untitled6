package pl.ninebits.qa.eurocash.site.vendorportal.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import pl.ninebits.qa.automated.tests.site.commons.BasePage;
import pl.ninebits.qa.eurocash.api.users.VendorPortalUserRole;
import pl.ninebits.qa.eurocash.site.vendorportal.constants.ProductStatus;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AdminLogisticsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AdminProductsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.AdminPromotionsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.ComplaintsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.GroupOffersPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.LogisticsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.OrdersPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.ProductGroupsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.ProductsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.PromotionsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.RefundsPage;
import pl.ninebits.qa.eurocash.site.vendorportal.pages.VendorPortalBasePage;

public class SideMenu extends VendorPortalBasePage {
  public SideMenu(BasePage pageObject) {
    super(pageObject);
  }

  public VendorPortalBasePage clickProductsSideLink(VendorPortalUserRole... role) {
    WebElement prodLink = findElement(By.xpath("//span[@class='nav-menu-option--name'][.='Produkty']"), "productsLink");
    prodLink.click();
    try {
      waitForElementToBeVisible(By.cssSelector("ven-product-list-filters"), 5);
      waitForElementToBeVisible(By.cssSelector("ven-product-list-table"), 5);
    } catch (Exception e) {
      throw new NoSuchElementException("Nie wczytała się tabela z produktami");
    }

    if (role.length == 0) {
      return new ProductsPage(this);
    } else {
      return role[0].equals(VendorPortalUserRole.ADMIN) ? new AdminProductsPage(this) : new ProductsPage(this);
    }
  }

  public OrdersPage clickOrdersSideLink() {
    WebElement orderLink = findElement(By.xpath("//span[@class='nav-menu-option--name'][.='Zamówienia (dostawy)']"));
    orderLink.click();
    try {
      waitForElementToBeVisible(By.cssSelector("ven-orders-list-filters"), 10);
      waitForElementToBeVisible(By.cssSelector("ven-orders-list-table"), 10);
    } catch (Exception e) {
      throw new NoSuchElementException("Nie wczytała się tabela z zamówieniami.");
    }

    return new OrdersPage(this);
  }

  public ComplaintsPage clickComplaintsSideLink() {
    WebElement complaintsLink = findElement(By.xpath("//span[@class='nav-menu-option--name'][.='Reklamacje']"));
    complaintsLink.click();
    try {
      waitForElementToBeVisible(By.cssSelector("ven-complaints-list-filters"), 10);
      waitForElementToBeVisible(By.cssSelector("ven-complaints-list-table"), 10);
    } catch (Exception e) {
      throw new NoSuchElementException("Nie wczytała się tabela z reklamacjami.");
    }

    return new ComplaintsPage(this);
  }

  public RefundsPage clickRefundSideLink() {
    WebElement refundsLink = findElement(By.xpath("//span[@class='nav-menu-option--name'][.='Zwroty należności']"));
    refundsLink.click();
    try {
      waitForElementToBeVisible(By.cssSelector("ven-cashback-list-filters > .table-filters"), 10);
      waitForElementToBeVisible(By.cssSelector(".mother-table > .ng-star-inserted"), 10);
    } catch (Exception e) {
      throw new NoSuchElementException("Nie wczytała się tabela ze zwrotami.");
    }

    return new RefundsPage(this);
  }

  public VendorPortalBasePage clickLogisticLink(VendorPortalUserRole role) {
    WebElement logisticLink = findElement(By.cssSelector("[ng-reflect-router-link='/logistic']"));
    logisticLink.click();

    return role.equals(VendorPortalUserRole.ADMIN) ? new AdminLogisticsPage(this) : new LogisticsPage(this);
  }

  public ProductGroupsPage clickProductGroupsSideLink() {
    WebElement productGroupsLink = findElement(By.cssSelector("ven-sidenav a[ng-reflect-router-link='/product-groups']"));
    productGroupsLink.click();

    return new ProductGroupsPage(this);
  }

  public GroupOffersPage clickGroupOffersSideLink() {
    WebElement groupOffersLink = findElement(By.cssSelector("li:nth-of-type(9) > .block.menu-link > .nav-menu-option--name"));
    groupOffersLink.click();

    return new GroupOffersPage(this);
  }

  public void openProductsStatuses() throws Exception {
    if (!isElementPresent(By.cssSelector("a[href^='/products?status']"))) {
      WebElement prodLink = findElement(By.xpath("//span[@class='nav-menu-option--name'][.='Produkty']"), "productsLink");
      prodLink.click();
      try {
        waitForElementToBeVisible(By.cssSelector("a[href^='/products?status']"), 5);
      } catch (Exception e) {
        throw new Exception("Menu ze statusami produktów nie zostało otwarte");
      }
    }
  }

  public void clickStatusLink(ProductStatus status) {
    WebElement statusFilterLink = findElement(By.cssSelector("a[href='/products?status=" + status.getId() + "']"));
    statusFilterLink.click();
    waitVendorForDataIsLoaded();
  }

  public VendorPortalBasePage clickPromotionsSideLink(VendorPortalUserRole role) {
    WebElement promotionsSideLink = findElement(By.cssSelector("[ng-reflect-router-link='/promotions']"));
    promotionsSideLink.click();
    waitVendorForDataIsLoaded();

    return role.equals(VendorPortalUserRole.ADMIN) ? new AdminPromotionsPage(this) : new PromotionsPage(this);
  }
}