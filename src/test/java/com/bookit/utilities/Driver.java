package com.bookit.utilities;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Driver {
    //same for everyone
    private static final ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();
    //adding these codes from browserStack :
    public static final String USERNAME = "sofiyanuryyeva1";
    public static final String AUTOMATE_KEY = "VK5f5DRMvSs8rpTwHnqg";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub"; //this is basic authentication


    //so no one can create object of Driver class
    //everyone should call static getter method instead
    private Driver() {

    }

    /**
     * synchronized makes method thread safe. It ensures that only 1 thread can use it at the time.
     * <p>
     * Thread safety reduces performance but it makes everything safe.
     *
     * @return
     */
    public synchronized static WebDriver getDriver() {
        //adding selenium grid server that  has docker
        String GRID_URL = "http://35.175.172.115:4444/wd/hub"; //should be specified in properties.file (for simplicity it is in here)
        //if webdriver object doesn't exist
        //create it
        if (driverPool.get() == null) {
            //specify browser type in configuration.properties file
            String browser = ConfigurationReader.getProperty("browser").toLowerCase();
            // -Dbrowser=firefox
            if (System.getProperty("browser") != null) {
                browser = System.getProperty("browser");
            }

            if (System.getProperty("grid_url") != null) {
                GRID_URL = System.getProperty("grid_url");
            }

            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    driverPool.set(new ChromeDriver(chromeOptions));
                    break;
                case "chromeheadless":
                    //to run chrome without interface (headless mode)
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.setHeadless(true);
                    driverPool.set(new ChromeDriver(options));
                    break;
                case "chrome-remote":
                    try {
                        //we create object of URL and specify
                        //selenium grid hub as a parameter
                        //make sure it ends with /wd/hub
                        URL url = new URL(GRID_URL);
                        //desiredCapabilities used to specify what kind of node
                        //is required for testing
                        //such as: OS type, browser, version, etc...
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName(BrowserType.CHROME);
                        desiredCapabilities.setPlatform(Platform.ANY); //any platform : for android for example etc.
        //remove webdriver is parent of webdriver›
                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities)); //url is address of hub
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "firefox-remote":
                    try {
                        //we create object of URL and specify
                        //selenium grid hub as a parameter
                        //make sure it ends with /wd/hub
                        URL url = new URL(GRID_URL);
                        //desiredCapabilities used to specify what kind of node
                        //is required for testing
                        //such as: OS type, browser, version, etc...
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName(BrowserType.FIREFOX);
                        desiredCapabilities.setPlatform(Platform.ANY);
                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver());
                    break;
                case "browser-stack-chrome":
                    DesiredCapabilities caps = new DesiredCapabilities();
                    caps.setCapability("browser", "Chrome");
                    caps.setCapability("browser_version", "83.0");
                    caps.setCapability("os", "Windows");
                    caps.setCapability("os_version", "10");
                    caps.setCapability("resolution", "1920x1080");
                    caps.setCapability("name", "BookIT Automation");
                    try {
                        driverPool.set(new RemoteWebDriver(new URL(URL), caps) );
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "browser-stack-ios":
                    DesiredCapabilities capsIOS = new DesiredCapabilities();
                    capsIOS.setCapability("browserName", "iPhone");
                    capsIOS.setCapability("device", "iPhone 8 Plus");
                    capsIOS.setCapability("realMobile", "true");
                    capsIOS.setCapability("os_version", "11");
                    capsIOS.setCapability("name", "Bstack-[Java] Sample Test");

                    try {
                        driverPool.set(new RemoteWebDriver(new URL(URL), capsIOS ));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }


                    break;

                case "browser-stack-android":

                    DesiredCapabilities capsAndroid = new DesiredCapabilities();
                    capsAndroid.setCapability("browserName", "android");
                    capsAndroid.setCapability("device", "Samsung Galaxy Note 10");
                    capsAndroid.setCapability("realMobile", "true");
                    capsAndroid.setCapability("os_version", "9.0");
                    capsAndroid.setCapability("name", "BookIt Android Test");

                    try {
                        driverPool.set(new RemoteWebDriver(new URL(URL), capsAndroid) );
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    throw new RuntimeException("Wrong browser name!");
            }
        }
        return driverPool.get();
    }


    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}
