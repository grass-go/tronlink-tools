package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.Step;
import tron.common.utils.WebBrowser;
import tron.common.utils.Configuration;

public class MtoS_TRC10Token {
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

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "从主链往侧链转入TRC10")
    public void testMtoS_TRC10Token() throws Exception{
        Step.login(driver);
        {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }

        driver.findElement(By.cssSelector(".account-token-tab > a.active:nth-child(1)")).click();
        driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(5) > button")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath(".//form/div[3]/input")).click();
        driver.findElement(By.xpath("//form/div[3]/input")).sendKeys("1");
        Thread.sleep(200);
        driver.findElement(By.xpath("//form/button")).click();
        Thread.sleep(3000);
//        Assert.assertTrue((driver.findElement(By.cssSelector(".sweet-alert > h2 > span")).getText()== "Deposit Success")||(driver.findElement(By.cssSelector(".sweet-alert > h2 > span")).getText()== "转入成功"));
    }

    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
