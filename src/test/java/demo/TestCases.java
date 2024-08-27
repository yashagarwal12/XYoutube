package demo;

import org.apache.xmlbeans.XmlFactoryHook.ThreadContext;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.*;
import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import dev.failsafe.internal.util.Durations;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @Test()
        public void testCase01() throws InterruptedException {

                Wrappers wrap = new Wrappers();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

                driver.get("https://www.youtube.com/");

                WebElement about = driver.findElement(By.xpath(
                                "//div[@id='contentContainer']/div[@id='guide-wrapper']//div[@id='guide-content']/div[@id='guide-inner-content']/ytd-guide-renderer/div[@id='footer']//a[text()='About']"));
                wrap.moveToEle(about, driver);
                Thread.sleep(2000);
                wrap.clk(about);
                wait.until(ExpectedConditions.urlContains("about"));

                WebElement about_text=driver.findElement(By.xpath("//section[@class='ytabout__content']/h1"));
                WebElement content1 = driver.findElement(By.xpath("//section[@class='ytabout__content']/p[1]"));
                WebElement content2 = driver.findElement(By.xpath("//section[@class='ytabout__content']/p[2]"));
                wrap.moveToEle(about_text, driver);
                Thread.sleep(2000);
                System.out.println(about_text+"\n"+content1.getText() + " " + content2.getText());

        }

        @Test()
        public void testCase02() throws InterruptedException {
                Wrappers wrap = new Wrappers();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                driver.get("https://www.youtube.com/");

                WebElement movies = driver.findElement(By.xpath(
                                "//div[@id='contentContainer']/div[@id='guide-wrapper']//div[@id='guide-content']/div[@id='guide-inner-content']//div[@id='sections']//a[@id='endpoint' and  @title='Movies']"));

                wrap.moveToEle(movies, driver);
                wrap.clk(movies);

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                                "//div[@id='page-header']//img[@src='https://www.gstatic.com/youtube/img/tvfilm/clapperboard_profile.png']")));

                WebElement top_selling_btn = driver.findElement(By.xpath(
                                "//a[@title='Top selling']//following::div[@id='contents'][1]//div[@id='right-arrow']//button"));

                wrap.moveToEle(top_selling_btn, driver);

                while (top_selling_btn.isDisplayed()) {
                        wrap.clk(top_selling_btn);
                        Thread.sleep(500);
                }
                Thread.sleep(2000);

                int top_selling_count = driver.findElements(By.xpath(
                                "//a[@title='Top selling']//following::div[@id='contents'][1]/yt-horizontal-list-renderer//div[@id='scroll-outer-container']//div[@id='items']/ytd-grid-movie-renderer"))
                                .size();
                System.out.println(top_selling_count);
                WebElement top_selling_last = driver.findElement(By.xpath(
                                "//a[@title='Top selling']//following::div[@id='contents'][1]/yt-horizontal-list-renderer//div[@id='scroll-outer-container']//div[@id='items']/ytd-grid-movie-renderer["
                                                + (top_selling_count) + "]"));

                WebElement adultOrNot = driver.findElement(By.xpath(
                                "//a[@title='Top selling']//following::div[@id='contents'][1]/yt-horizontal-list-renderer//div[@id='scroll-outer-container']//div[@id='items']/ytd-grid-movie-renderer["
                                                + (top_selling_count)
                                                + "]//ytd-badge-supported-renderer/div[contains(@class,'badge-style-type-simple')]/p"));
                System.out.println("adult or not- " + adultOrNot.getText());
                SoftAssert sa = new SoftAssert();
                sa.assertEquals(adultOrNot.getText(), "A");

                WebElement genre = driver.findElement(By.xpath(
                                "//a[@title='Top selling']//following::div[@id='contents'][1]/yt-horizontal-list-renderer//div[@id='scroll-outer-container']//div[@id='items']/ytd-grid-movie-renderer["
                                                + (top_selling_count) + "]//a/span"));
                String genre_str = genre.getText().replaceAll("[^a-zA-Z]", "");
                System.out.println("Genre String- " + genre_str);

                sa.assertTrue(!genre_str.isEmpty(), "No Genre is present");

        }

        @Test()
        public void testCase03() throws InterruptedException {

                Wrappers wrap = new Wrappers();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                driver.get("https://www.youtube.com/");

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                                "//div[@id='contentContainer']/div[@id='guide-wrapper']//div[@id='guide-content']/div[@id='guide-inner-content']//div[@id='sections']//a[@id='endpoint' and  @title='Music']")));
                WebElement music = driver.findElement(By.xpath(
                                "//div[@id='contentContainer']/div[@id='guide-wrapper']//div[@id='guide-content']/div[@id='guide-inner-content']//div[@id='sections']//a[@id='endpoint' and  @title='Music']"));

                wrap.moveToEle(music, driver);
                wrap.clk(music);

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                                "//div[@id='header']//img[contains(@src,'//yt3.googleusercontent.com/')]")));

                WebElement right_btn = driver.findElement(By.xpath(
                                "//ytd-two-column-browse-results-renderer[@page-subtype='channels']/div[@id='primary']//div[@id='contents']/ytd-item-section-renderer[1]//yt-horizontal-list-renderer/div[@id='right-arrow']//button"));

                while (right_btn.isDisplayed()) {
                        wrap.clk(right_btn);
                        Thread.sleep(500);
                }
                Thread.sleep(2000);

                int count = driver.findElements(By.xpath(
                                "//span[@id=\"title\" and text()=\"India's Biggest Hits\"]//following::div[@id=\"contents\"][1]/yt-horizontal-list-renderer//div[@id=\"scroll-outer-container\"]//div[@id=\"items\"]/ytd-compact-station-renderer"))
                                .size();
                System.out.println(count);
                WebElement count_last = driver.findElement(By.xpath(
                                "//span[@id=\"title\" and text()=\"India's Biggest Hits\"]//following::div[@id=\"contents\"][1]/yt-horizontal-list-renderer//div[@id=\"scroll-outer-container\"]//div[@id=\"items\"]/ytd-compact-station-renderer["
                                                + (count) + "]"));
                int tracks = Integer.parseInt(driver.findElement(By.xpath(
                                "//span[@id=\"title\" and text()=\"India's Biggest Hits\"]//following::div[@id=\"contents\"][1]/yt-horizontal-list-renderer//div[@id=\"scroll-outer-container\"]//div[@id=\"items\"]/ytd-compact-station-renderer["
                                                + (count) + "]/div/a/p"))
                                .getText().replaceAll("[^0-9]", ""));

                SoftAssert sa = new SoftAssert();
                sa.assertTrue(tracks <= 50);

        }

        @Test()
        public void testCase04() throws InterruptedException {
                Wrappers wrap = new Wrappers();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                driver.get("https://www.youtube.com/");

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                                "//div[@id='contentContainer']/div[@id='guide-wrapper']//div[@id='guide-content']/div[@id='guide-inner-content']//div[@id='sections']//a[@id='endpoint' and  @title='News']")));
                WebElement news = driver.findElement(By.xpath(
                                "//div[@id='contentContainer']/div[@id='guide-wrapper']//div[@id='guide-content']/div[@id='guide-inner-content']//div[@id='sections']//a[@id='endpoint' and  @title='News']"));

                wrap.moveToEle(news, driver);
                wrap.clk(news);

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                                "//div[@id='page-header']//span[text()='News']")));

                int like_sum = 0;
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                                "//span[@id='title' and text()='Latest news posts']//following::div[@id='contents'][1]")));
                WebElement news_content = driver.findElement(By.xpath(
                                "//span[@id='title' and text()='Latest news posts']//following::div[@id='contents'][1]"));

                wrap.moveToEle(news_content, driver);
                for (int i = 1; i <= 3; i++) {
                        String header = driver.findElement(By.xpath(
                                        "//span[@id='title' and text()='Latest news posts']//following::div[@id='contents'][1]/ytd-rich-item-renderer["
                                                        + (i)
                                                        + "]//div[@id='dismissible']/div[@id='header']//a[@id='author-text']/span"))
                                        .getText();
                        System.out.println("Header: " + header);
                        Thread.sleep(1000);

                        int body_eles = driver.findElements(By.xpath(
                                        "//span[@id='title' and text()='Latest news posts']//following::div[@id='contents'][1]/ytd-rich-item-renderer["
                                                        + (i)
                                                        + "]//div[@id='dismissible']/div[@id='body']//yt-formatted-string[@id='home-content-text']//descendant::*[contains(@class,'style-scope yt-formatted-string')]"))
                                        .size();
                        Thread.sleep(1000);
                        for (int j = 1; j <= body_eles; j++) {
                                String temp = driver.findElement(By.xpath(
                                                "//span[@id='title' and text()='Latest news posts']//following::div[@id='contents'][1]/ytd-rich-item-renderer["
                                                                + (i)
                                                                + "]//div[@id='dismissible']/div[@id='body']//yt-formatted-string[@id='home-content-text']//descendant::*[contains(@class,'style-scope yt-formatted-string')]["
                                                                + (j) + "]"))
                                                .getText();
                                Thread.sleep(1000);
                                System.out.print(temp);
                        }

                        System.out.println();
                        int likes = 0;
                        try {
                                likes = Integer.parseInt(driver.findElement(By.xpath(
                                                "//span[@id='title' and text()='Latest news posts']//following::div[@id='contents'][1]/ytd-rich-item-renderer["
                                                                + (i)
                                                                + "]//div[@id='dismissible']/div[@id='toolbar']/ytd-comment-action-buttons-renderer//span[@id='vote-count-middle']"))
                                                .getText().trim().replaceAll("[^0-9]", ""));
                        } catch (Exception E) {
                                System.out.println("Most probably votes count is 0");
                                likes = 0;
                        }
                        like_sum += likes;
                        Thread.sleep(1000);
                }
                System.out.println("Sum of upvotes: " + like_sum);

        }

        @Test(dataProvider = "excelData", dataProviderClass = demo.utils.ExcelDataProvider.class)
        public void testCase05(String search) throws InterruptedException {
                Wrappers wrap = new Wrappers();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                driver.get("https://www.youtube.com/");

                WebElement search_topic = driver.findElement(By.cssSelector("input#search"));
                wrap.sendData(search_topic, search);
                wait.until(ExpectedConditions.presenceOfElementLocated(By
                                .xpath("//yt-formatted-string[@title='Videos']//parent::yt-chip-cloud-chip-renderer")));
                WebElement videos = driver.findElement(By
                                .xpath("//yt-formatted-string[@title='Videos']//parent::yt-chip-cloud-chip-renderer"));
                wrap.clk(videos);
                Thread.sleep(2000);

                List<WebElement> list = driver.findElements(By.xpath(
                                "//ytd-two-column-search-results-renderer//div[@id='primary']/ytd-section-list-renderer/div[@id='contents']/ytd-item-section-renderer//ytd-video-renderer//ytd-video-meta-block//div[@id='metadata-line']/span[1]"));

                double total = 0;
                for (WebElement ele : list) {
                        String views = ele.getText();

                        double views_in_nums = wrap.countViews(views);
                        total += views_in_nums;

                        if (total >= 100000000) {
                                break;
                        } else {
                                wrap.scrollToView(ele, driver);
                        }
                }

        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}