package com.ebay.tests.tests;

import com.microsoft.playwright.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FirstTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;

    @Before
    public void setUp() {
        // Create Playwright and launch browser
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
        page = browser.newPage();
        System.out.println("Browser launched");
    }

    @Test
    public void testMockEbayPageLoads() {
        try {
            // Load mock eBay product HTML
            byte[] encoded = Files.readAllBytes(
                    Paths.get("src/test/resources/mock_ebay_product.html")
            );
            String mockHtml = new String(encoded, StandardCharsets.UTF_8);

            page.setContent(mockHtml);

            // Get page title
            String title = page.title();
            System.out.println("Page title: " + title);

            // Verify page loaded correctly
            assertTrue("Page should have a title", !title.isEmpty());
            assertTrue("Page title should contain 'Wallet'", title.contains("Wallet"));

            // Verify main product exists
            assertTrue("Main product heading should exist",
                    page.isVisible("h1"));

            // Verify related products section exists
            assertTrue("Related products section should exist",
                    page.isVisible(".related-products-container"));

            // Verify product cards exist
            assertTrue("Product cards should exist",
                    page.locator(".product-card").count() > 0);

            System.out.println("Test PASSED - Framework is working!");
            System.out.println("Mock page loaded with " +
                    page.locator(".product-card").count() + " products");

        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        // Close browser and cleanup
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        System.out.println("Browser closed");
    }
}