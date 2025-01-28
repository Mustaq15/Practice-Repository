package browsers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class OpenInMultipleBrowser {

	@Test
	void openChromeInBrowser() {
		WebDriver driver = new ChromeDriver();

//		Create a new instance of Chrome Driver
		driver.get("https://www.google.com");

		driver.close();

	}

	@Test
	void openInFirefoxBrowser() {
		WebDriver driver = new FirefoxDriver();

//		Create a new instance of Firefox Driver
		driver.get("https://www.google.com");

		driver.quit();

	}

}
