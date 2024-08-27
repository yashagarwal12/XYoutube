package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    public void clk(WebElement ele) {
        try {
            ele.click();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public void sendData(WebElement ele, String data) {

        try {
            ele.clear();
            ele.sendKeys(data);
            ele.sendKeys(Keys.ENTER);

        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public void moveToEle(WebElement ele, WebDriver driver) {

        try {
            Actions actions = new Actions(driver);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", ele);
            actions.moveToElement(ele).build().perform();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public void scrollToView(WebElement ele, WebDriver driver) {
        try {
           
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", ele);
            
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public double countViews(String views){
        String temp=views.replace("views", "").trim();
                String degree=temp.substring(temp.length()-1, temp.length());
                float nums=Float.parseFloat(temp.substring(0, temp.length()-1));
                double views_in_nums=0;
                
                switch (degree) {
                        case "M":
                                views_in_nums=nums*100000;
                                break;
                        case "K":
                                views_in_nums=nums*1000;
                                break;
                        
                        case "B":
                                views_in_nums=nums*10000000;
                                break;
                        default:
                                views_in_nums=0;
                                break;
                }
                return views_in_nums;
                
    }
}
