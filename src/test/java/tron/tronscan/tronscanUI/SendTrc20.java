package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.Step;
import tron.common.utils.WebBrowser;
import tron.common.utils.Configuration;

public class SendTrc20 {
    private String tronScanNode = Configuration.getByPath("testng.conf")
    .getString("tronscanIP");
private  String URL = "https://"+tronScanNode+"/#/";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;
    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
        } catch (Exception e) {

        }
    }

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "Trc20转账")
    public void testTrc20() throws Exception{
        Step.login(driver);
        {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        driver.findElement(By.cssSelector(".dropdown-item:nth-child(10)")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(1) .form-control")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(1) .form-control")).sendKeys("TYvBUrZp7QboQzKhFVMYYkD4jDYsU33aQh");
        //选择TRC20
        Thread.sleep(200);
        driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/div[2]/form/div[2]/div/div/div/div")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        driver.findElement(By.xpath("/html/body/div[3]/div/div/div/ul/li[2]/ul/li[1]")).click();
        Thread.sleep(200);
        driver.findElement(By.cssSelector(".form-group:nth-child(3) .form-control")).sendKeys("1");
        driver.findElement(By.cssSelector(".form-group:nth-child(4) .form-control")).click();
        Thread.sleep(200);
        driver.findElement(By.cssSelector(".form-group:nth-child(4) .form-control")).sendKeys("TRC 20");
        Thread.sleep(300);
        driver.findElement(By.xpath("//form/button")).click();
        Thread.sleep(300);
        driver.findElement(By.cssSelector("div:nth-child(1) > div.sweet-alert > p > span:nth-child(2) > button")).click();
        Thread.sleep(300);
        Assert.assertEquals(driver.findElement(By.cssSelector(".sweet-alert > h2")).getText(), "Successful Transaction");
    }

    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
