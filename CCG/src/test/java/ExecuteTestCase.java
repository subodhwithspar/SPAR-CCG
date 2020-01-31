import CCGClassPackage.HomePage;
import org.testng.annotations.Test;

import java.io.IOException;

//import org.junit.Test;

public class ExecuteTestCase{
      @Test
     public void runTC() throws IOException, InterruptedException {
              HomePage hp=new HomePage();
              hp.testFun();
              Thread.sleep(2000);
              hp.login();
           System.out.println("Test Completed");

     }


}
 
