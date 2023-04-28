package page;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public String loginURL = "http://20.6.1.98:8081/#/login";

    private By username = By.name("username");
    private By password = By.name("password");
    private By loginBtn = By.xpath("//button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage toLoginPage(){
        webDriver.get(loginURL);
        return this;
    }

    public AddPowerPlantPage login( String username, String password ){
        clearText(this.username);
        clearText(this.password);
        writeText(this.username,username);
        writeText(this.password,password);
        click(loginBtn);
        return new AddPowerPlantPage(webDriver);
    }

}
