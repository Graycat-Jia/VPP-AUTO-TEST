package testcase;


import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import vo.GenerateVO;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AddPowerPlantCase extends BaseCase{


    @Test(description = "登录之后判断是否已有电厂")
    public void exist_power_plant(){
        loginPage.toLoginPage()
                .login("aahe6001","123456")
                .existPlant();
    }

    @Test(description = "添加电厂")
    public void add_power_plant(){
        int a = loginPage.toLoginPage()
                    .login("aahe6001","123456")
                    .toAddPowerPlantPage()
                    .writePowerPlantFrom("Auto ui add test name","中国","福建省","仓山","Victoria",
                            "GrayName","13012345678","test@qq.com","123456","this is a test remark.CI create.")
                    .submit();
        System.out.println( "add result: " + a );
    }

    @Test(description = "请求验证码(仅测试环境)")
    public String verification() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://20.6.1.65:9601/system/register/sendPhoneValidCode");
        // 设置请求参数
        List<BasicNameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("phone", "13012345678"));
        post.setEntity(new UrlEncodedFormEntity(parameters));
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity);
        GenerateVO generateVO = JSON.parseObject(responseText,GenerateVO.class);
        System.out.println(generateVO.getData());
        return generateVO.getData();
    }


    @Test(description = "添加设备_手动勾选")
    public void add_device_manual(){
        Assert.assertEquals(false,false);
    }
    @Test(description = "添加设备_excel导入")
    public void add_device_excel(){
        Assert.assertEquals(true,true);

    }

}
