

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.asserts.SoftAssert;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;


public class WebstaurantStore {

	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ChromeOptions chromeOptions = new ChromeOptions();
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		try {
			runTestCase(driver);
			driver.quit();
		} catch (Exception e) {
			throw e;
		} finally {
			driver.quit();
		}
	}

	private static void runTestCase(WebDriver driver) throws InterruptedException {
		
		// Step 1 Navigate to the webstaurantstore website.
		driver.get("https://www.webstaurantstore.com");
		MainPageObj mpo = new MainPageObj(driver);
		
		// step 2 Search for 'stainless steel table.
		mpo.enterSearchText("stainless steel table");
		mpo.clickSearchBtn();
		
		// step 3 Check the result ensuring every product has the word 'Table' in its title . Using softassart, ignore hard stop when 'Table' isn't available.
		SoftAssert sa = mpo.checkItems();
		
		// step 4 Going to Last page of the 'stainless steel table' result and adding an item which has table in its title  and has 'Add to Cart' option of the last item.
		mpo.goToLastPageAndAdd();
		
		// wait 10 sec to close overlay automatically.
		Thread.sleep(10000);
		
		// step 5 - Empty the cart.
		mpo.clickCartBtn();
		mpo.emptyCart();
		
		// Clicking on confirmation to empty cart.
		mpo.emptycart();
		
		// It will show whether cart is empty
		mpo.clickCartBtn();
		
		// Failed assertion - If the content 'Table' isn't available in title, it will be shown in console which is part of step 3.
		sa.assertAll();
	}

}
