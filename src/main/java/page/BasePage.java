package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BasePage {

    public String baseURL = "https://server-cn.growatt.com/login";

    public WebDriver webDriver;

    public WebDriverWait webDriverWait;

    BasePage( WebDriver driver ){
        webDriver = driver;
        this.webDriverWait = new WebDriverWait(driver,10);
    }

    public void click(By by) {
        waitVisibility(by).click();
    }

    // 清空输入框
    public void clearText( By by ){
        waitVisibility(by).clear();
    }

    //Write Text
    public void writeText(By by, String text) {
        waitVisibility(by).sendKeys(text);
    }

    //Read Text
    public String readText(By by) {
        return waitVisibility(by).getText();
    }


    // Wait
    // 显示等待情况下，只有特定条件触发后，WebDriver才会继续执行后续操作。
    // 隐式等待情况下，WebDriver 等待一定时间，该时间段内，如果特定元素没加载成功，则抛出异常。
    public WebElement waitVisibility(By by) {
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    // 当by的元素有多个时，等待采用这个
    public List<WebElement> waitVisibilityMulti(By by) {
        return webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    public String getPageTitle(  ){
        return webDriver.getTitle();
    }
}
