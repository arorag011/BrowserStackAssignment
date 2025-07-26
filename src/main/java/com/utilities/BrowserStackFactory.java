package com.utilities;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserStackFactory {
	public static WebDriver createBrowserStackDriver(String os, String osVersion, String browserName, 
			String browserVersion,
			String device, String realMobile) throws Exception {

		MutableCapabilities capabilities = new MutableCapabilities();
		Map<String, Object> bstackOptions = new HashMap<String, Object>();

		bstackOptions.put("userName", "gaurav_6fwuaT");
		bstackOptions.put("accessKey", "LgtKCkgNytdknyCwhz6c");

		// Mobile
		if (!device.isEmpty() && realMobile.equalsIgnoreCase("true")) {
			bstackOptions.put("deviceName", device);
			bstackOptions.put("realMobile", true);
			bstackOptions.put("osVersion", osVersion);  

		} else {
			// Desktop
			capabilities.setCapability("browserName", browserName);        
			capabilities.setCapability("browserVersion", browserVersion); 
			bstackOptions.put("os", os);                                   
			bstackOptions.put("osVersion", osVersion);                     
		}

		bstackOptions.put("sessionName", "Test on " + browserName);
		capabilities.setCapability("bstack:options", bstackOptions);


		return new RemoteWebDriver(new URL("https://hub-cloud.browserstack.com/wd/hub"), capabilities);
	}
}
