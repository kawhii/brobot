import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author karl
 * @version 2019-04-22
 */
public class ChormeTest {
    @Test
    public void baiduTest() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.baidu.com/");
        webDriver.quit();
    }
}
