package com.selenium.script;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.utilities.ApiUtility;
import com.utilities.CommonMethods;

public class SpanishOutletTest extends CommonMethods{

	private Map<String, String> headerAndContent;
	
	public Map<String, String> getHeadersAndContent(){
		return headerAndContent;
	}
	

	@Test(priority=1)
	public void fetchArticlesHeadersAndContentsAndDownloadImage() throws IOException  {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		resetDirectory();
		this.headerAndContent = new LinkedHashMap<String, String>();		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("didomi-notice-agree-button")));
		try {
		//Accepting the cookies
		driver.findElement(By.id("didomi-notice-agree-button")).click();
		}catch(Exception n) {
			
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//article[contains(@class, 'c-d c--m')]")));
		List<WebElement> articles = new ArrayList<WebElement>();
		articles= driver.findElements(By.xpath("//article[contains(@class, 'c-d c--m')]"));
		for(int i=0;i<5;i++) {
			String header="";
			String content="";
			WebElement article = articles.get(i);
			header= article.findElement(By.xpath(".//header//h2/a")).getText().replaceAll("[’‘]", "");
			try {
			content= article.findElement(By.xpath(".//p")).getText();
			}catch(NoSuchElementException e) {
				content = "No content available";				
			}
			this.headerAndContent.put(header, content);
			System.out.println("Original header--> "+header);
			System.out.println("It's content--> "+content);
			try {
				// Downloading available images
				downloadImage(article.findElement(By.xpath(".//figure//img")).getAttribute("src"), 
						header+".avif");
			}catch (NoSuchElementException e) {
				
			}
		}
		
	}	
	@Test(priority=2)
	public void translateAndIdentifyRepeatations() {
		ApiUtility api = new ApiUtility();
		String combinedTranslatedHeaders = "";
		for(String h: this.headerAndContent.keySet()) {
			String translatedHeader="";
			String apiResponse = api.translateAPIResponse(h);
			JSONObject obj = new JSONObject(apiResponse);
			JSONArray arr = obj.getJSONArray("translatedTexts");
			translatedHeader= arr.getString(0);
			System.out.println("Translated Header--> "+translatedHeader);
			combinedTranslatedHeaders=combinedTranslatedHeaders+" "+translatedHeader;
		}
		String[] words = combinedTranslatedHeaders.replaceAll("[^A-Za-z\\s]", "").split(" ");
		Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
		for(String s: words) {
			frequencyMap.put(s, frequencyMap.getOrDefault(s, 0)+1);
		}
		System.out.println("Translated headers combined--> "+combinedTranslatedHeaders);
		boolean flag = false;
		for(String key: frequencyMap.keySet()) {
			if(frequencyMap.get(key)>2) {
				flag=true;
				System.out.println("\""+key+"\" is occuring "+frequencyMap.get(key)+" times");
			}
		}
		if(!flag) {
			System.out.println("No Word is occuring more than twice");
		}
	}
	

}
