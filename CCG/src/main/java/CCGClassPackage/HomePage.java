package CCGClassPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
public class HomePage {
    static WebDriver driver;
    ExcelUtility exlu=new ExcelUtility();
    Properties localProp=new Properties();
    String Username="";
    String Password="";
    String currenturl,winHandleBefore;
    public void testFun(){
        System.setProperty("webdriver.chrome.driver","D:\\Subodh\\Software\\Driver\\chromedriver.exe");
        //System.setProperty("webdriver.chrome.driver", "C:\\executable\\location.exe");
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://admin.dev.uhpoa.com");
    }
   public void login() throws IOException, InterruptedException {
        currenturl=driver.getCurrentUrl();
       LinkedHashMap LoginCredentials=new LinkedHashMap();
        //Map LoginCredentials=new HashMap();
        localProp=exlu.configData();
        LoginCredentials= exlu.readLoginSheet("D:\\Subodh\\AutomationFWdownload","CCGDataFile.xlsx","CCGDataSheet");
        Set<String> keys = LoginCredentials.keySet();
        System.out.println(keys.size());
        int rowno=0;
        for (String loginKey : keys) {
             if(!currenturl.equals("http://admin.dev.uhpoa.com/#b")){
                HomePage hp=new HomePage();
                hp.testFun();
            }
            System.out.println("Key: " + loginKey);
            Username= loginKey; //This string variable Username is  storing data sheet value of username.
            Password=(String) LoginCredentials.get(loginKey); //This string variable Password is  storing data sheet value of password
            driver.findElement(By.xpath(localProp.getProperty("Username"))).clear();
            driver.findElement(By.xpath(localProp.getProperty("Password"))).clear();
            driver.findElement(By.xpath(localProp.getProperty("Username"))).sendKeys(Username);
            driver.findElement(By.xpath(localProp.getProperty("Password"))).sendKeys(Password);
            driver.findElement(By.xpath(localProp.getProperty("Login"))).click();
            System.out.println("size of property file :"+localProp.size());

            if(driver.getPageSource().contains("Search Customer Database")) {
                System.out.println("Username "+Username+" login pass");
                driver.findElement(By.xpath(localProp.getProperty("logoff"))).click();
                // Store the current window handle
                winHandleBefore = driver.getWindowHandle();
                // Perform the click operation that opens new window
                // Switch to new window opened
                driver.switchTo().frame("infoFrame");
                driver.findElement(By.xpath(localProp.getProperty("popupLogOff"))).click();
                // Switch back to original browser (first window)
                driver.switchTo().window(winHandleBefore);
                //String msg=driver.findElement(By.xpath(localProp.getProperty("message"))).getText();
                exlu.updateExcelSheet("D:\\Subodh\\AutomationFWdownload","CCGDataFile.xlsx","CCGDataSheet",++rowno,2,"Pass", "Login Successfully");
            }
            else if(driver.getPageSource().contains("We are sorry, but that is not a valid Username and/or Password.")) {
                System.out.println("-ve TC with Username "+Username+" login Fail");

                String msg=driver.findElement(By.xpath(localProp.getProperty("error"))).getText();
                exlu.updateExcelSheet("D:\\Subodh\\AutomationFWdownload","CCGDataFile.xlsx","CCGDataSheet",++rowno,2,"Fail", msg);
            }else {
                System.out.println("-Ve TC with Username "+Username+" login Fail");
                exlu.updateExcelSheet("D:\\Subodh\\AutomationFWdownload","CCGDataFile.xlsx","CCGDataSheet",++rowno,2,"Fail","Username or Password is incorrect");
                driver.close();
            }
        }
    driver.quit();
    }
}
