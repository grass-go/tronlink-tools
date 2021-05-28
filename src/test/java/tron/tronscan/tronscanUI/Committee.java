package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.WebBrowser;

public class Committee {
  private String tronScanNode = Configuration.getByPath("testng.conf")
    .getString("tronscanIP");
private  String URL = "https://"+tronScanNode+"/#/sr/committee";
  WebBrowser webBrowser = new WebBrowser();
  public static WebDriver driver;
  @BeforeMethod(enabled = true)
  public void start() throws Exception {
    try {
      driver = webBrowser.startChrome(URL);
    } catch (Exception e) {

    }
  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void testCommittee() throws Exception{
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/h4/span/span/span")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[4]/h4/span/span")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div/div/div/div/div/div/div/table/tbody/tr[1]/td[1]/span")).getText().isEmpty());
  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void testSRCommittee() throws Exception{
    driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div[2]/a/button")).click();
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div[2]/div/div/div/div/div/table/tbody/tr[1]/td[1]/div")).getText().isEmpty());
    Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div[2]/div/div/div/div/div/table/tbody/tr[1]/td[2]/div/div/div/div")).getText().isEmpty());
  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
  public void end() throws Exception {
    WebBrowser.tearDownBrowser();
  }
}
