package browsers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VerifyHomePageTitle {

//	Test Case: Open the SauceDemo website and verify Home page Title.
//	Execute the Test in multiple browsers- Chrome, Firefox
	@Test
	void openInChrome() throws InterruptedException {
//		Create a New instance of Chrome driver
		WebDriver driver = new ChromeDriver();

//		Maximize browser window
		driver.manage().window().maximize();

		driver.get("https://www.saucedemo.com");
		String actTitle = driver.getTitle();
		Assert.assertEquals(actTitle, "Swag Labs");

		Thread.sleep(3000);
		driver.quit();

	}

	@Test
	void openInFirefox() throws InterruptedException {
//		create a New instance of Firefox driver
		WebDriver driver = new FirefoxDriver();

//		Maximize the browser window
		driver.manage().window().maximize();

		driver.get("https://www.saucedemo.com");
		String actTitle = driver.getTitle();
		Assert.assertEquals(actTitle, "Swag Labs");

		Thread.sleep(3000);
		driver.quit();
	}

	@Test
	void openInEdge() throws InterruptedException {
//		Create a New instance of Edge driver
		WebDriver driver = new EdgeDriver();

//		Maximize the browser window
		driver.manage().window().maximize();

		driver.get("https://www.saucedemo.com");
		String actTitle = driver.getTitle();
		Assert.assertEquals(actTitle, "Swag Labs");

		Thread.sleep(3000);
		driver.quit();
	}

}
