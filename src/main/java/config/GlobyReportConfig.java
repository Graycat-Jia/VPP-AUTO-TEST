package config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * TestNG Listener�е�IReporter�ӿ�Ϊ�����ṩ�������Զ��屨���ý�飨���Զ���TestNG���ɵı��棩��
 * �ýӿڰ���һ����ΪgenerateReport()�ķ������������׼�������ʱ���ǻ���ø÷������÷�����������������
 xmlSuite�����Ǵ����� XML �ļ����Թ�ִ�е��׼��б�
 suites������һ���������а����йز���ִ�к��׼������Ϣ��������Ϣ������Ϣ���ܰ��������ơ������ơ����Է������ƺͲ���ִ�н����
 outputDirectory�����������ɱ�����õ�����ļ��е�·����
 */
public class GlobyReportConfig implements IReporter{


    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
                               String outputDirectory) {
        // ָ���������λ��
        ExtentReports reports = new ExtentReports();
        ExtentSparkReporter report = new ExtentSparkReporter("target/Report/AllReport.html");
        report.config().setEncoding("GBK");
        reports.attachReporter(report);

        // ��������
        //Iterate over every suite assigned for execution
        for (ISuite suite : suites) {
            String suiteName = suite.getName();
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult sr : suiteResults.values()) {
                ITestContext tc = sr.getTestContext();
                ExtentTest menu_root_test = reports.createTest(tc.getName());

                buildTestNode(tc.getPassedTests(), Status.PASS, menu_root_test);
                buildTestNode(tc.getFailedTests(), Status.FAIL, menu_root_test);
                buildTestNode(tc.getSkippedTests(), Status.SKIP, menu_root_test);

                System.out.println("Passed tests for suite '" + suiteName +
                        "' is:" + tc.getPassedTests().getAllResults().size());
                System.out.println("Failed tests for suite '" + suiteName +
                        "' is:" + tc.getFailedTests().getAllResults().size());
                System.out.println("Skipped tests for suite '" + suiteName +
                        "' is:" + tc.getSkippedTests().getAllResults().size());
            }
        }
        reports.flush();
    }

    private void buildTestNode(IResultMap tests, Status status, ExtentTest father) {
        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                System.out.println("itestResult��" + result);
                ExtentTest child = father.createNode(result.getMethod().getMethodName())
                        .log(status,result.getMethod().getDescription())
                        .log(status,"Method location path�� "+result.getMethod().toString());
                for (String group : result.getMethod().getGroups())
                    child.assignCategory(group);

                if (result.getThrowable() != null) {
                    child.log(status, result.getThrowable());
                }
                else {
                    child.log(status, "Test " + status.toString().toLowerCase() + "ed");
                }
                child.getModel().setStartTime(getTime(result.getStartMillis()));
                child.getModel().setEndTime(getTime(result.getEndMillis()));
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

}