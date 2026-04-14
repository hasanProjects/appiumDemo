package appium;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class mdaAPP {
	AppiumDriverLocalService service;
	AndroidDriver driver;

	@Test
	public void AppiumTest() throws MalformedURLException, URISyntaxException, InterruptedException {

		service = new AppiumServiceBuilder()
				.withAppiumJS(new File(
						"C:\\Users\\hasanhemalatha\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
				.withIPAddress("127.0.0.1").usingPort(4723).withArgument(() -> "--allow-cors").build();
		System.out.println(service.getUrl() + ">>>>>>>>>>>>>>>>>");
		service.start();

		// appium configurarions
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("sdk_gphone16k_x86_64");
		options.setAppPackage("com.saucelabs.mydemoapp.android");
		options.setPlatformName("Android");
		options.setAutomationName("UiAutomator2");
		options.setPlatformVersion("13");
		options.setAppActivity("com.saucelabs.mydemoapp.android.view.activities.SplashActivity");
		options.setApp(
				"C:\\Users\\hasanhemalatha\\practise.Restassured\\appium\\src\\test\\java\\resources\\mda-2.2.0-25.apk");
		//driver inistialization
		driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		//clicking on a product
		driver.findElement(By.xpath(
				"//androidx.recyclerview.widget.RecyclerView[@content-desc='Displays all products of catalog']/android.view.ViewGroup[1]"))
				.click();

		// Wait until element is clickable
		WebElement cartElement = wait
				.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Tap to add product to cart")));

		// adding the product to cart
		cartElement.click();
		
		//proceeding to CART PAGE
		driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/cartIV")).click();

		WebElement checkoutButton = wait.until(
				ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Confirms products for checkout")));
		//proceeding to CHECKOUT PAGE
		checkoutButton.click();
		
		//login into account 
		WebElement userNamefield = wait.until(
				ExpectedConditions.elementToBeClickable(By.id("com.saucelabs.mydemoapp.android:id/nameET")));
		
		userNamefield.sendKeys("bod@example.com");
		driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/passwordET")).sendKeys("10203040");
		driver.findElement(AppiumBy.accessibilityId("Tap to login with given credentials")).click();

		//Proceeds to address form
		WebElement addressname = wait.until(
				ExpectedConditions.elementToBeClickable(By.id("com.saucelabs.mydemoapp.android:id/fullNameET")));
		addressname.sendKeys("TestName");
		driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/address1ET")).sendKeys("TestAddress");
		driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/cityET")).sendKeys("Testcity");
		driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/zipET")).sendKeys("56820");
		driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/countryET")).sendKeys("India");
		driver.findElement(AppiumBy.accessibilityId("Saves user info for checkout")).click();

		//Payement page
		WebElement paymenetName = wait.until(
				ExpectedConditions.elementToBeClickable(By.id("com.saucelabs.mydemoapp.android:id/nameET")));
		paymenetName.sendKeys("Test User");
		driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/cardNumberET")).sendKeys("1234112222445678");
		driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/expirationDateET")).sendKeys("03/27");
		driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/securityCodeET")).sendKeys("123");
		driver.findElement(AppiumBy.accessibilityId("Saves payment info and launches screen to review checkout data")).click();
		
		//Placing the order
		WebElement placeOrderButton = wait.until(
				ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Completes the process of checkout")));
		placeOrderButton.click();
		
		//confirmation page validation
		wait.until(
				ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Tap to open catalog")));
		String confirmationTest = driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/orderTV")).getText();
		System.out.println(confirmationTest);
		Assert.assertEquals("Your order has been dispatched and will arrive as fast as the pony gallops!", confirmationTest);
		Assert.assertTrue(driver.findElement(AppiumBy.accessibilityId("Tap to open catalog")).isDisplayed());
		
		service.stop();

	}

}
