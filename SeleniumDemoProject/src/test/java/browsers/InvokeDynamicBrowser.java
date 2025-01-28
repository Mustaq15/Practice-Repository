package browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class InvokeDynamicBrowser {

//	Test Case: Open the SauceDemo website and verify Home page Title.
//	Execute the Test in multiple browsers- Chrome, Firefox

	public WebDriver driver = null;

	@Parameters("browser")
	@BeforeMethod
	public void openBrowser(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
//		Create a New instance of Chrome driver
			driver = new ChromeDriver();
//		Maximize browser window
			driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("firefox")) {
//		Create a New instance of Firefox driver
			driver = new FirefoxDriver();
//		Maximize browser window
			driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("edge")) {
//		Create a New instance of Edge driver
			driver = new EdgeDriver();
//		Maximize browser window
			driver.manage().window().maximize();
		} else {
//		Create a New instance of Chrome driver
			driver = new ChromeDriver();
//		Maximize browser window
			driver.manage().window().maximize();
		}

	}

	@AfterMethod
	public void quitBrowser() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();
	}

	@Test
	public void verifyHomePageTitle() {
//		Open saucedemo home page
		driver.get("https://www.saucedemo.com");

//		Verify saucedemo home page title
		String actTitle = driver.getTitle();
		Assert.assertEquals(actTitle, "Swag Labs");

	}

}
