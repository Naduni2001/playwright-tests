package com.ebay.tests.tests;

import com.microsoft.playwright.*;
import com.ebay.tests.pages.RelatedProductsPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * RelatedProductsTest.java
 * Test Cases: TC-001 to TC-007 (Basic Tests)
 * Tests: Visibility, Count, Elements, Navigation, Price Range
 */
public class RelatedProductsTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private RelatedProductsPage relatedPage;

    private static final String WALLET_PRODUCT_URL = "https://www.ebay.com/itm/314710838801";

    @Before
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        page = browser.newPage();
        relatedPage = new RelatedProductsPage(page);
        System.out.println("\nSetup complete\n");
    }

    // TC-001: Verify Related Products Section Displays
    @Test
    public void testTC_001_RelatedProductsSectionVisible() {
        System.out.println("TC-001: Related Products Section Visible");

        relatedPage.navigateTo(WALLET_PRODUCT_URL);
        boolean visible = relatedPage.isRelatedProductsSectionVisible();

        assertTrue("Related products section should be visible", visible);
        System.out.println("TC-001 PASSED\n");
    }

    // TC-002: Verify Product Count (Max 6)
    @Test
    public void testTC_002_ProductCountValid() {
        System.out.println("TC-002: Product Count Valid (Max 6)");

        relatedPage.navigateTo(WALLET_PRODUCT_URL);
        int count = relatedPage.getRelatedProductsCount();

        assertTrue("Should have at least 1 product", count >= 1);
        assertTrue("Should have max 6 products", count <= 6);
        System.out.println("TC-002 PASSED\n");
    }

    // TC-003: Verify Product Card Elements (Image, Title, Price)
    @Test
    public void testTC_003_ProductCardElementsPresent() {
        System.out.println("TC-003: Product Card Elements Present");

        relatedPage.navigateTo(WALLET_PRODUCT_URL);
        int count = relatedPage.getRelatedProductsCount();

        for (int i = 0; i < count; i++) {
            assertTrue("Product " + (i + 1) + " should have all elements",
                    relatedPage.productCardIsComplete(i));
        }
        System.out.println("TC-003 PASSED\n");
    }

    // TC-004: Verify Main Product NOT in Related List
    @Test
    public void testTC_004_MainProductExcluded() {
        System.out.println("TC-004: Main Product Excluded");

        relatedPage.navigateTo(WALLET_PRODUCT_URL);
        boolean excluded = relatedPage.isMainProductExcludedFromRelated();

        assertTrue("Main product should be excluded", excluded);
        System.out.println("TC-004 PASSED\n");
    }

    // TC-005: Verify Same Category Products
    @Test
    public void testTC_005_SameCategoryProducts() {
        System.out.println("TC-005: Same Category Products");

        relatedPage.navigateTo(WALLET_PRODUCT_URL);
        boolean sameCat = relatedPage.areProductsInSameCategory();

        assertTrue("Products should be in same category", sameCat);
        System.out.println("TC-005 PASSED\n");
    }

    // TC-006: Verify Price Range Logic (±20%)
    @Test
    public void testTC_006_PriceRangeLogic() {
        System.out.println("TC-006: Price Range Logic");

        relatedPage.navigateTo(WALLET_PRODUCT_URL);
        boolean priceValid = relatedPage.isPriceRangeValid();

        assertTrue("Price range logic should be valid", priceValid);
        System.out.println("TC-006 PASSED\n");
    }

    // TC-007: Click Related Product Navigation
    @Test
    public void testTC_007_ClickRelatedProductNavigation() {
        System.out.println("TC-007: Click Related Product Navigation");

        relatedPage.navigateTo(WALLET_PRODUCT_URL);
        String originalUrl = relatedPage.getCurrentUrl();

        if (relatedPage.getRelatedProductsCount() > 0) {
            relatedPage.clickRelatedProduct(0);
            String newUrl = relatedPage.getCurrentUrl();

            assertNotEquals("URL should change after click", originalUrl, newUrl);
            assertTrue("Should have product content", relatedPage.isElementVisible("h1"));
        }
        System.out.println("TC-007 PASSED\n");
    }

    @After
    public void tearDown() {
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        System.out.println("Teardown complete\n");
    }
}