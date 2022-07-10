import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import com.google.common.util.concurrent.Uninterruptibles;

public class MainPageObj {

	WebDriver driver;

	public MainPageObj(WebDriver driver) {
		this.driver = driver;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
		PageFactory.initElements(factory, this);
	}

	@FindBy(xpath = "//input[@name='searchval']")
	private WebElement searchTextBox;

	@FindBy(xpath = "//button[@value='Search']")
	private WebElement searchBtn;

	@FindBy(xpath = "//div[@id='main']//a[@data-testid='itemDescription']")
	private List<WebElement> itemDescription;

	@FindBy(xpath = "//ul//li[@class='rc-pagination-next']")
	private WebElement nextBtn;

	@FindBy(xpath = "//ul//li[contains(@class,'rc-pagination-next')]/preceding-sibling::li[preceding-sibling::li][1]")
	private WebElement lastPage;

	@FindBy(xpath = "//input[@data-testid='itemAddCart']")
	private List<WebElement> itemAddCart;

	@FindBy(xpath = "//*[name()='svg']/following::span[text()='Cart']/../..")
	private WebElement cartBtn;
	
	@FindBy(xpath = "//button[text()='Empty Cart']")
	private WebElement emptyCart;
	
	@FindBy(xpath = "//button[text()='Empty']")
	private WebElement emptycart;
	
	public void enterSearchText(String searchTxt) {
		fluentWaitVisibility(driver, searchTextBox, 10);
		searchTextBox.clear();
		searchTextBox.sendKeys(searchTxt);
	}

	public void clickSearchBtn() {
		fluentWaitVisibility(driver, searchBtn, 10);
		searchBtn.click();
	}

	public static boolean isElementDisplayed(WebElement ele) {
		try {
			return ele.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	public static WebElement fluentWaitVisibility(WebDriver driver, WebElement ele, int secs) {
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(secs, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
			return wait.until(ExpectedConditions.visibilityOf(ele));
		} catch (Exception e) {
			return null;
		}
	}
	public static List<WebElement> fluentWaitVisibility(WebDriver driver, List<WebElement> eleList, int secs) {
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(secs, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
			return wait.until(ExpectedConditions.visibilityOfAllElements(eleList));
		} catch (Exception e) {
			return null;
		}
	}


	public List<WebElement> getItemsList() {
		fluentWaitVisibility(driver, itemDescription, 10);
		return itemDescription;
	}

	public boolean clickNext() {
		fluentWaitVisibility(driver, nextBtn, 10);
		if (isElementDisplayed(nextBtn)) {
			nextBtn.click();
			return true;
		}
		return false;
	}

	public SoftAssert checkItems() throws InterruptedException {
		SoftAssert sa = new SoftAssert();
		
		do {
			fluentWaitVisibility(driver, itemDescription, 10);
			List<WebElement> weList = getItemsList();
			for (WebElement we : weList) {
				sa.assertTrue(we.getText().contains("Table"), "Failing assertion for the item " + we.getText());
			}
		} while (clickNext());
		return sa;
	}

	public void goToLastPageAndAdd() {
		fluentWaitVisibility(driver, lastPage, 10);
		lastPage.click();
		fluentWaitVisibility(driver, itemAddCart, 10);
		List<WebElement> weList = itemAddCart;
		weList.get(weList.size() - 1).click();
	}

	public void clickCartBtn() {
		// TODO Auto-generated method stub
		fluentWaitVisibility(driver, cartBtn, 10);
		boolean bool = isElementDisplayed(cartBtn);
		cartBtn.click();
	}

	public void emptyCart() {
		// TODO Auto-generated method stub
		fluentWaitVisibility(driver, emptyCart, 10);
		emptyCart.click();
	}
	
	public void emptycart() {
		// TODO Auto-generated method stub
		fluentWaitVisibility(driver, emptycart, 10);
		emptycart.click();
	}
	
}
