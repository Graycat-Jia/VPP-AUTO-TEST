package page;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddPowerPlantPage extends BasePage {

    private String addPowerPlantURL = "http://20.6.1.98:8081/#/addPlant";

    // 左侧导航栏菜单，用于账号下是否有电厂判断
    private By asideMenu = By.xpath("//aside");
    // 无电厂时添加电厂按钮
    private By addPowerPlantBtn = By.xpath("//button");


    private By powerPlantName = By.name("powerPlantName");      // 电厂名称输入框
    private By countryInput = By.xpath("//input[@type='text']");    // 下拉选择框--国家，下标为1
    private By cityInput = By.xpath("//input[@type='text']");       // 下拉选择框--城市，下标为2
    private By powerGrid = By.xpath("//input[@type='text']");     // 电网--下标为3
    private By formAllInput = By.xpath("//input[@type='text']");    // 表单各个元素框，从上倒下依次为0~9
    private By remarkInput = By.xpath("//textarea");        // 评论输入框
    private By buttonInPage = By.xpath("//button");    // 页面所有按钮（0发送验证码，1~2电厂状态，3~4电厂模式，5备注）
    public AddPowerPlantPage(WebDriver driver) {
        super(driver);
    }

    // 判断电厂是否存在
    public boolean existPlant() {
        try {
            if (waitVisibility(asideMenu) != null) {
                System.out.println("找到菜单栏，判断为此账号存在电厂");
                return true;
            }
        } catch (TimeoutException e) {
            System.out.println("10s内没有找到菜单栏，判断为此账号没有电厂");
            return false;
        }
        return true;
    }

    // 添加电厂，填充表单
    public AddPowerPlantPage writePowerPlantFrom(String plantName,String country,String city,String cityDown,String gridName,
                                                 String name,String phone,String email,String code,String remark) {
        writeText(powerPlantName, plantName);
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(countryInput)).get(1).click();
        // 选择国家，国家列表中的某一结点
        By countryNode = By.xpath("//div[contains(@class,'el-scrollbar')]//span[text()='"+country+"']");
        if (webDriver.findElements(countryNode).size()!=0){
//            System.out.println("选中国家");
            click(countryNode);
        }else   System.out.println("无此国家");

        // 选择城市与其下一关联选择框
        waitVisibilityMulti(cityInput).get(2).click();
        By cityNode = By.xpath("//div[contains(@class,'el-cascader-panel')]//span[text()='"+city+"']");
        if(webDriver.findElements(cityNode).size()!=0){
            click(cityNode);
        }else System.out.println("该国家中无此城市");
        By cityDownNode = By.xpath("//div[contains(@class,'el-cascader-panel')]//span[text()='"+cityDown+"']");
        if(webDriver.findElements(cityDownNode).size()!=0){
            click(cityDownNode);
        }else System.out.println("该城市中没有该城镇");
        // 选择所属电网
        waitVisibilityMulti(powerGrid).get(3).click();
        By gridNode = By.xpath("//div[contains(@class,'el-scrollbar')]//span[text()='"+gridName+"']");
        click(gridNode);
        // 输入联系人姓名
        waitVisibilityMulti(formAllInput).get(5).sendKeys(name);
        waitVisibilityMulti(formAllInput).get(6).sendKeys(phone);
        waitVisibilityMulti(formAllInput).get(7).sendKeys(email);
        waitVisibilityMulti(formAllInput).get(8).sendKeys(code);
        // 输入评论
        writeText(remarkInput,remark);
        return this;
    }

    public int submit(){
        waitVisibilityMulti(buttonInPage).get(5).click();

        WebElement messageElement =  waitVisibility(By.className("el-message"));
        String messageText = messageElement.getText();
        System.out.println("Message text is: " + messageText);
        String classValue = messageElement.getAttribute("class");   // 获取 class 属性值
        // 判断消息类型
        if (classValue.contains("el-message--success")) {
            // 消息类型为 success
            return 1;
        } else if (classValue.contains("el-message--warning")) {
            // 消息类型为 warning
            return 2;
        } else if (classValue.contains("el-message--info")) {
            // 消息类型为 info
            return 3;
        } else if (classValue.contains("el-message--error")) {
            // 消息类型为 error
            return 0;
        } else {
            // 没有匹配的类型
            return -1;
        }
    }

    public AddPowerPlantPage toAddPowerPlantPage(){
        webDriver.get(addPowerPlantURL);
        return this;
    }


}
 