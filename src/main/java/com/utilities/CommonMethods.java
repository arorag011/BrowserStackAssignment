package com.utilities;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class CommonMethods {
	protected static WebDriver driver;
	String baseUrl = "https://elpais.com";
	String opinionUrl = "https://elpais.com/opinion/";
	public static final String IMAGE_DIR = System.getProperty("user.dir") + File.separator + "images";

	@Parameters({"os", "osVersion", "browser", "browserVersion",
        "device", "realMobile"})
	@BeforeSuite
	public void setupEnvironment(@Optional("Windows")String os, 
			@Optional("11")String osVersion, 
			@Optional("Chrome")String browser, 
			@Optional("latest")String browserVersion,
			@Optional("") String device, 
			@Optional("false")String realMobile) throws Exception     	{
		/*ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);*/
		driver = BrowserStackFactory.createBrowserStackDriver(os, osVersion, browser,browserVersion, device, realMobile);
		driver.manage().window().maximize();
		driver.get(opinionUrl);

	}
	
	public static void resetDirectory() throws IOException {
		 File dir = new File(IMAGE_DIR);
        if (dir.exists()) {
            File[] contents = dir.listFiles();
            if (contents != null) {
                for (File file : contents) {
                        file.delete(); 
                    
                }
            }
            dir.delete(); 
        }
        Files.createDirectories(Paths.get(IMAGE_DIR));
    }

	
	
	/*
	 * public static void createDirectory(String path) throws IOException { //Files
	 * dir = new Files(System.getProperty("user.dir")+path);
	 * Files.createDirectories(Paths.get(path)); }
	 */
	public void downloadImage(String imageUrl, String fileName) throws IOException {
		
        InputStream in = new URL(imageUrl).openStream();
        Files.copy(in, Paths.get("images/" + fileName), StandardCopyOption.REPLACE_EXISTING);
    }

}
