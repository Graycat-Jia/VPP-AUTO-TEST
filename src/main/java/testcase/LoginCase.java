package testcase;

import org.testng.annotations.Test;

public class LoginCase extends BaseCase{


    @Test(description="Normal login test.")
    public void login_normal(  ){
        loginPage.toLoginPage()
                .login("aapm3001","pwd123456");
    }




}
