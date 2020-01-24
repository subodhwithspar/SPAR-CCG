import CCGClassPackage.HomePage;
import org.junit.Test;

import java.io.IOException;

public class ExecuteTestCase{
    @Test
    public void runTC() throws IOException, InterruptedException {
        HomePage hp=new HomePage();
        hp.testFun();
        Thread.sleep(2000);
        hp.login();

     }
}
