import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LogicTest {

    WebDriver driver;

    @BeforeMethod
    public void before(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("https://rozetka.com.ua/");
    }

    @Test
    public void testRozetkaTitle() throws InterruptedException, IOException {
        WebElement searchInput = driver.findElement((
                By.xpath("/html/body/app-root/div/div/rz-header" +
                        "/rz-main-header/header/div/div/div/form/div/div/input")));
        searchInput.sendKeys("Mac");
        WebElement searchBtn = driver.findElement(
                By.xpath("/html/body/app-root/div/div" +
                        "/rz-header/rz-main-header/header/div/div/div/form/button"));
        searchBtn.click();
        WebElement firstProduct = (new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/app-root/div/div" +
                        "/rz-search/rz-catalog/div/div[2]/section/rz-grid/ul" +
                        "/li[1]/rz-catalog-tile/app-goods-tile-default/div/div[2]/a[2]/span"))));
        String firstProductTitle = firstProduct.getText();
        //Assert.assertEquals("Title doesn't equals searching data", firstProductTitle, "Mac");
        firstProduct.click();
        WebElement productPageTitle = driver.findElement(
                By.xpath("/html/body/app-root/div/div/rz-product/div/rz-product-top/div/div[1]/h1"));
        //String firstTitle = productPageTitle.getAttribute("innerText");
        //Assert.assertEquals("Title doesn't equals", firstProductTitle, firstTitle);
        WebElement everythingAboutProduct = driver.findElement(
                By.xpath("/html/body/app-root/div/div/rz-product/div/rz-product-navbar" +
                        "/rz-tabs/div/div/ul/li[1]/a"));
        String firstActiveLinkColor = everythingAboutProduct.getCssValue("color");
        //Assert.assertEquals(firstActiveLinkColor, "rgba(0, 160, 70, 1)", "Color of first active tab isn't green");
        WebElement productPrice = driver.findElement(By.xpath("//*[@id=\"#scrollArea\"]/div[1]" +
                "/div[2]/rz-product-main-info/div[1]/div/div/p"));
        if (everythingAboutProduct.isDisplayed() && firstActiveLinkColor.equals("rgba(0,160,70,1)")) {
            String price = productPrice.getText();
            String title = productPageTitle.getText();
            FileWriter fileWriter = new FileWriter("text.txt");
            fileWriter.write(price + " " + title);
            fileWriter.close();
        } else {
            System.out.println("Error");
        }
    }

    @AfterMethod
    public void after() {
        driver.quit();
    }
}
