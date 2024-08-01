package utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.BaseTest;

public class ElementFetch {
	
	public WebElement getWebElement(String identifierType, String identifireValue)
	{
		switch(identifierType) {
		
		case "XPATH" :
		return BaseTest.driver.findElement(By.cssSelector(identifireValue));
		case "CSS" :
			return BaseTest.driver.findElement(By.cssSelector(identifireValue));
		case "ID" :
			return BaseTest.driver.findElement(By.cssSelector(identifireValue));
			
	default:
		return null;
		}

	}


	public List<WebElement> getWebElements(String identifierType, String identifireValue)
	{
		switch(identifierType) {
		
		case "XPATH" :
		return BaseTest.driver.findElements(By.cssSelector(identifireValue));
		case "CSS" :
			return BaseTest.driver.findElements(By.cssSelector(identifireValue));
		case "ID" :
			return BaseTest.driver.findElements(By.cssSelector(identifireValue));
			
	default:
		return null;
		}

}
}