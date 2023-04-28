package page;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddPowerPlantPage extends BasePage {

    private String addPowerPlantURL = "http://20.6.1.98:8081/#/addPlant";

    // ��ർ�����˵��������˺����Ƿ��е糧�ж�
    private By asideMenu = By.xpath("//aside");
    // �޵糧ʱ��ӵ糧��ť
    private By addPowerPlantBtn = By.xpath("//button");


    private By powerPlantName = By.name("powerPlantName");      // �糧���������
    private By countryInput = By.xpath("//input[@type='text']");    // ����ѡ���--���ң��±�Ϊ1
    private By cityInput = By.xpath("//input[@type='text']");       // ����ѡ���--���У��±�Ϊ2
    private By powerGrid = By.xpath("//input[@type='text']");     // ����--�±�Ϊ3
    private By formAllInput = By.xpath("//input[@type='text']");    // ������Ԫ�ؿ򣬴��ϵ�������Ϊ0~9
    private By remarkInput = By.xpath("//textarea");        // ���������
    private By buttonInPage = By.xpath("//button");    // ҳ�����а�ť��0������֤�룬1~2�糧״̬��3~4�糧ģʽ��5��ע��
    public AddPowerPlantPage(WebDriver driver) {
        super(driver);
    }

    // �жϵ糧�Ƿ����
    public boolean existPlant() {
        try {
            if (waitVisibility(asideMenu) != null) {
                System.out.println("�ҵ��˵������ж�Ϊ���˺Ŵ��ڵ糧");
                return true;
            }
        } catch (TimeoutException e) {
            System.out.println("10s��û���ҵ��˵������ж�Ϊ���˺�û�е糧");
            return false;
        }
        return true;
    }

    // ��ӵ糧������
    public AddPowerPlantPage writePowerPlantFrom(String plantName,String country,String city,String cityDown,String gridName,
                                                 String name,String phone,String email,String code,String remark) {
        writeText(powerPlantName, plantName);
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(countryInput)).get(1).click();
        // ѡ����ң������б��е�ĳһ���
        By countryNode = By.xpath("//div[contains(@class,'el-scrollbar')]//span[text()='"+country+"']");
        if (webDriver.findElements(countryNode).size()!=0){
//            System.out.println("ѡ�й���");
            click(countryNode);
        }else   System.out.println("�޴˹���");

        // ѡ�����������һ����ѡ���
        waitVisibilityMulti(cityInput).get(2).click();
        By cityNode = By.xpath("//div[contains(@class,'el-cascader-panel')]//span[text()='"+city+"']");
        if(webDriver.findElements(cityNode).size()!=0){
            click(cityNode);
        }else System.out.println("�ù������޴˳���");
        By cityDownNode = By.xpath("//div[contains(@class,'el-cascader-panel')]//span[text()='"+cityDown+"']");
        if(webDriver.findElements(cityDownNode).size()!=0){
            click(cityDownNode);
        }else System.out.println("�ó�����û�иó���");
        // ѡ����������
        waitVisibilityMulti(powerGrid).get(3).click();
        By gridNode = By.xpath("//div[contains(@class,'el-scrollbar')]//span[text()='"+gridName+"']");
        click(gridNode);
        // ������ϵ������
        waitVisibilityMulti(formAllInput).get(5).sendKeys(name);
        waitVisibilityMulti(formAllInput).get(6).sendKeys(phone);
        waitVisibilityMulti(formAllInput).get(7).sendKeys(email);
        waitVisibilityMulti(formAllInput).get(8).sendKeys(code);
        // ��������
        writeText(remarkInput,remark);
        return this;
    }

    public int submit(){
        waitVisibilityMulti(buttonInPage).get(5).click();

        WebElement messageElement =  waitVisibility(By.className("el-message"));
        String messageText = messageElement.getText();
        System.out.println("Message text is: " + messageText);
        String classValue = messageElement.getAttribute("class");   // ��ȡ class ����ֵ
        // �ж���Ϣ����
        if (classValue.contains("el-message--success")) {
            // ��Ϣ����Ϊ success
            return 1;
        } else if (classValue.contains("el-message--warning")) {
            // ��Ϣ����Ϊ warning
            return 2;
        } else if (classValue.contains("el-message--info")) {
            // ��Ϣ����Ϊ info
            return 3;
        } else if (classValue.contains("el-message--error")) {
            // ��Ϣ����Ϊ error
            return 0;
        } else {
            // û��ƥ�������
            return -1;
        }
    }

    public AddPowerPlantPage toAddPowerPlantPage(){
        webDriver.get(addPowerPlantURL);
        return this;
    }


}
 