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

    // ��������
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
    // ��ʾ�ȴ�����£�ֻ���ض�����������WebDriver�Ż����ִ�к���������
    // ��ʽ�ȴ�����£�WebDriver �ȴ�һ��ʱ�䣬��ʱ����ڣ�����ض�Ԫ��û���سɹ������׳��쳣��
    public WebElement waitVisibility(By by) {
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    // ��by��Ԫ���ж��ʱ���ȴ��������
    public List<WebElement> waitVisibilityMulti(By by) {
        return webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    public String getPageTitle(  ){
        return webDriver.getTitle();
    }
}
