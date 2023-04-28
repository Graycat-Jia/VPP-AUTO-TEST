package testcase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import page.AddPowerPlantPage;
import page.BasePage;
import page.LoginPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class BaseCase {

    public WebDriver chromDriver;

    public BasePage basePage;

    public AddPowerPlantPage addPowerPlantPage;

    public LoginPage loginPage;

    /** ��ʼ�����ã�����google���������������  */
    @BeforeClass(groups = "ui_auto")
    public void initSource() throws IOException {
        Properties properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
        properties.load(input);
        String localDriverPath = properties.getProperty("localDriverPath");
        String driverType = properties.getProperty("driverType");
        // ���� WebDriver ���������ͺͱ�������·��
        System.setProperty(driverType, localDriverPath);
        this.chromDriver = new FirefoxDriver();
    }

    @BeforeMethod(groups = "ui_auto")
    public void initPage(){
        loginPage = new LoginPage(chromDriver);
        addPowerPlantPage = new AddPowerPlantPage(chromDriver);
    }

    @AfterClass(groups = "ui_auto")
    public void shutdownSource() throws InterruptedException {
        Thread.sleep(2000);
        chromDriver.quit();
    }

}
