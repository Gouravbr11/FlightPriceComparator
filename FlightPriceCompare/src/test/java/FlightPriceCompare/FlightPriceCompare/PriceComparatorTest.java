package FlightPriceCompare.FlightPriceCompare;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PriceComparatorTest {
	
	@Test
	public void comparePrice() throws InterruptedException, IOException {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		WebDriverWait w=new WebDriverWait(driver, Duration.ofSeconds(3));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		Actions a=new Actions(driver);
		String travelDate="2023/10/26";
		String monthYear=Calendar.getMonthYear(travelDate);
		String date=Calendar.getDate(travelDate);
		

		//cleartrip flight entering data 
		driver.get("https://www.cleartrip.com/flights");
		js.executeScript("window.scrollBy(0,350)");
		driver.findElement(By.cssSelector("input[placeholder='Where from?']")).sendKeys("blr");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='dropdown p-absolute t-13 ln-1 w-100p'][1]")).click();
		driver.findElement(By.cssSelector("input[placeholder='Where to?']")).sendKeys("del");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='dropdown p-absolute t-13 ln-1 w-100p']/ul[@class='airportList'][1]")).click();
		Thread.sleep(4000);
		driver.findElement(By.cssSelector("[class='flex flex-middle p-relative homeCalender'] button[class*='flex flex-middle']:first-child")).click();
		
		//calendar date selection-cleartrip
		while(!driver.findElement(By.xpath("(//div[@class='DayPicker-Month'])[1]//div[@class='DayPicker-Caption']/div")).getText().equalsIgnoreCase(monthYear))
		{
			
			driver.findElement(By.xpath("//div[@class='flex-1 ta-right']//*[name()='svg' and contains(@data-testid,'rightArrow')]")).click();
			Thread.sleep(1000);
		} 
		
		js.executeScript("window.scrollBy(0,100)");
		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='DayPicker-Month'])[1]//div[@class='DayPicker-Caption']/div[.='"+monthYear+"']")));
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//div[@class='DayPicker-Month'])[1]//div[contains(@class,'DayPicker-Day')]/div[contains(@class,'Day-grid')]/div[text()='"+date+"']")).click();
		driver.findElement(By.xpath("//span[text()='Search flights']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//p[text()='Non-stop']")).click();
		
		WebElement flightList=driver.findElement(By.xpath("//div[@data-testid='airlineBlock']"));
		int size=driver.findElements(By.xpath("//div[@data-testid='airlineBlock']")).size();
		
		//paytm flight entering data 
		driver.switchTo().newWindow(WindowType.TAB);
		driver.get("https://tickets.paytm.com/flights/");
		a.moveToElement(driver.findElement(By.id("srcCode"))).doubleClick().sendKeys("bengaluru ").build().perform();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//div[@class='lM769'])[1]")).click();
		a.moveToElement(driver.findElement(By.id("destCode"))).doubleClick().sendKeys("delhi "+" ").build().perform();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//div[@class='lM769'])[1]")).click();
		
		//calendar date selection-paytm
		driver.findElement(By.id("departureDate")).click();
		boolean flag =true;
		int j=1;
		while(flag)
			{
			String st=driver.findElement(By.xpath("(//td[@class='calendar__month _18o18'])["+j+"]")).getText();
			if(driver.findElement(By.xpath("(//td[@class='calendar__month _18o18'])["+j+"]")).getText().equalsIgnoreCase(monthYear))
			{
				driver.findElement(By.xpath("(//table[@class='calendar'])["+j+"]//div[@class='calendar__day']/div[.='"+date+"']")).click();
				flag=false;
				
				
				
			}
			
			else if(driver.findElement(By.xpath("(//td[@class='calendar__month _18o18'])["+(j+1)+"]")).getText().equalsIgnoreCase(monthYear)) {
				
				String st2= driver.findElement(By.xpath("(//td[@class='calendar__month _18o18'])["+(j+1)+"]")).getText();
				driver.findElement(By.xpath("(//table[@class='calendar'])["+(j+1)+"]//div[@class='calendar__day']/div[.='"+date+"']")).click();
				flag=false;
				
			}
			
			else  {
			   driver.findElement(By.xpath("//div[@class='_5wV4O']//i[@class='gA7KZ _3nECU']")).click();
			   flag=true;
			   j=j+2;
			}
			} 
		
		//w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='DayPicker-Month'])[1]//div[@class='DayPicker-Caption']/div[.='"+monthYear+"']")));
		
		
		driver.findElement(By.id("flightSearch")).click();
		Thread.sleep(4000);
		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[.='Filters'])[1]")));
		js.executeScript("window.scrollBy(0,300)");
		driver.findElement(By.xpath("(//i[@class='ar-xN'])[1]")).click();
			
		
		
		
			CsvData.heading();
			Set<String> windows=driver.getWindowHandles();
			Iterator<String> it=windows.iterator();
			String clearTripWin=it.next();
			String paytmWin=it.next();
			for(int i=1;i<=size;i++) 
			{
			
			//Paytm flight price retrival
			String paytmPrice= driver.findElement(By.xpath("(//section[@id='flightsList']//div[@class='_2MkSl'])["+i+"]")).getText();
			//cleartrip flight data retrival
			driver.switchTo().window(clearTripWin);
			Thread.sleep(2000);
			String flightOp=flightList.findElement(By.xpath("(//div[@data-testid='airlineBlock']//div/p[@class='fw-500 fs-2 c-neutral-900'])["+i+"]")).getText();
			String flightNum=flightList.findElement(By.xpath("(//div[@data-testid='airlineBlock']//div/p[@class='fs-1 c-neutral-400 pt-1'])["+i+"]")).getText();
			String ClearTripPrice=flightList.findElement(By.xpath("(//div[@data-testid='airlineBlock']//div/p[@class='m-0 fs-5 fw-700 c-neutral-900 false'])["+i+"]")).getText();
			driver.switchTo().window(paytmWin);
			Thread.sleep(2000);
			
			CsvData.data(flightOp,flightNum,ClearTripPrice,paytmPrice);
			}
			
		
	}

}
