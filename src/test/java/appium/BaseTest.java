package appium;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseTest {

	AppiumDriverLocalService service;
	AndroidDriver driver;
	
	@BeforeClass
	public void configureAppium() throws MalformedURLException, URISyntaxException {
		// Start the Server
		service = new AppiumServiceBuilder()
				.withAppiumJS(new File(
						"C:\\Users\\hasanhemalatha\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
				.withIPAddress("127.0.0.1").usingPort(4723).build();
		service.start();

		// appium configurarions
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("sdk_gphone16k_x86_64");
		options.setApp(
				"C:\\Users\\hasanhemalatha\\practise.Restassured\\appium\\src\\test\\java\\resources\\ApiDemos-debug.apk");
		driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
		service.stop();
	}
}
