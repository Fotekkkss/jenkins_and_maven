package pl.seleniumdemo.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.seleniumdemo.pages.HotelSearchPage;
import pl.seleniumdemo.pages.ResultsPage;
import pl.seleniumdemo.utils.ExcelReader;
import pl.seleniumdemo.utils.SeleniumHelper;

import java.io.IOException;
import java.util.List;

public class HotelSearchTest extends BaseTest {

    @Test
    public void searchHotelTest() throws IOException {
        ExtentTest test = extentReports.createTest("Search Hotel Test");
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setCity("Dubai");
        test.log(Status.PASS, "Setting city done", SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.setDates("27/04/2025", "29/04/2025");
        test.log(Status.PASS, "Setting dates done",SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.setTravelers(1,2);
        test.log(Status.PASS, "Setting travelers done",SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.performSearch();
        test.log(Status.PASS, "Performing search done",SeleniumHelper.getScreenshot(driver));

        ResultsPage resultsPage = new ResultsPage(driver);
        List<String> hotelNames = resultsPage.getHotelNames();


        Assert.assertEquals("Jumeirah Beach Hotel",hotelNames.get(0));
        Assert.assertEquals("Oasis Beach Tower",hotelNames.get(1));
        Assert.assertEquals("Rose Rayhaan Rotana",hotelNames.get(2));
        Assert.assertEquals("Hyatt Regency Perth",hotelNames.get(3));

        test.log(Status.PASS, "Assertions passed",SeleniumHelper.getScreenshot(driver));
    }


    @Test(dataProvider = "data")
    public void searchHotelTestWithDataProvider(String city, String hotel) throws IOException {
        ExtentTest test = extentReports.createTest("Search Hotel Test");
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setCity(city);
        test.log(Status.PASS, "Setting "+city+" done", SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.setDates("27/04/2025", "29/04/2025");
        test.log(Status.PASS, "Setting dates done",SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.setTravelers(1,2);
        test.log(Status.PASS, "Setting travelers done",SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.performSearch();
        test.log(Status.PASS, "Performing search done",SeleniumHelper.getScreenshot(driver));

        ResultsPage resultsPage = new ResultsPage(driver);
        List<String> hotelNames = resultsPage.getHotelNames();


        Assert.assertEquals(hotelNames.get(0), hotel);
        test.log(Status.PASS, "Assertions passed",SeleniumHelper.getScreenshot(driver));
    }
    @DataProvider
    public Object[][] data() throws IOException {
        return ExcelReader.readExcel("CityHotel.xlsx");
    }
}
