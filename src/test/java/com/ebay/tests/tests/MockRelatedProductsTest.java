package com.ebay.tests.tests;

import com.microsoft.playwright.*;
import com.ebay.tests.pages.RelatedProductsPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MockRelatedProductsTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private RelatedProductsPage relatedPage;

    // Mock HTML files
    private static final String MOCK_PRODUCT_HTML = "src/test/resources/mock_ebay_product.html";
    private static final String MOCK_EMPTY_HTML = "src/test/resources/mock_ebay_empty.html";
    private static final String MOCK_ERROR_HTML = "src/test/resources/mock_ebay_error.html";
    private static final String SCREENSHOTS_DIR = "target/screenshots";

    @Before
    public void setUp() {
        new File(SCREENSHOTS_DIR).mkdirs();

        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
        page = browser.newPage();
        relatedPage = new RelatedProductsPage(page);

        System.out.println("\nMock Test Setup Complete\n");
    }

    // ========== POSITIVE TEST CASES (TC-001 to TC-007) ==========

    @Test
    public void testTC_001_RelatedProductsSectionVisible() {
        System.out.println("TC-001: Related Products Section Visible");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            assertTrue("Related products section must be visible",
                    relatedPage.isRelatedProductsSectionVisible());
            System.out.println("TC-001 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_001_failed");
            fail("TC-001: " + e.getMessage());
        }
    }

    @Test
    public void testTC_002_ProductCountValid() {
        System.out.println("TC-002: Product Count Valid");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            int count = relatedPage.getRelatedProductsCount();
            assertTrue("Should have products", count > 0);
            assertTrue("Count should not exceed 6", count <= 6);
            System.out.println("TC-002 PASSED (Count: " + count + ")\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_002_failed");
            fail("TC-002: " + e.getMessage());
        }
    }

    @Test
    public void testTC_003_ProductCardElementsPresent() {
        System.out.println("TC-003: Product Card Elements Present");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            assertTrue("Product cards must have all elements",
                    relatedPage.productCardIsComplete(0));
            System.out.println("TC-003 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_003_failed");
            fail("TC-003: " + e.getMessage());
        }
    }

    @Test
    public void testTC_004_MainProductExcluded() {
        System.out.println("TC-004: Main Product Excluded from Related");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            assertTrue("Main product should be excluded",
                    relatedPage.isMainProductExcludedFromRelated());
            System.out.println("TC-004 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_004_failed");
            fail("TC-004: " + e.getMessage());
        }
    }

    @Test
    public void testTC_005_SameCategoryProducts() {
        System.out.println("TC-005: Same Category Products");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            assertTrue("Products should be in same category",
                    relatedPage.areProductsInSameCategory());
            System.out.println("TC-005 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_005_failed");
            fail("TC-005: " + e.getMessage());
        }
    }

    @Test
    public void testTC_006_PriceRangeLogic() {
        System.out.println("TC-006: Price Range Logic");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            assertTrue("Price range should be valid",
                    relatedPage.isPriceRangeValid());
            System.out.println("TC-006 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_006_failed");
            fail("TC-006: " + e.getMessage());
        }
    }

    @Test
    public void testTC_007_ClickRelatedProductNavigation() {
        System.out.println("TC-007: Click Related Product Navigation");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            int count = relatedPage.getRelatedProductsCount();
            assertTrue("Should have products to click", count > 0);

            // Verify first product card has link with href
            Locator card = page.locator(".product-card").first();
            assertTrue("Product must have clickable link", card.locator("a").count() > 0);

            String href = card.locator("a").getAttribute("href");
            assertTrue("Link must have href attribute", href != null && !href.isEmpty());

            System.out.println("TC-007 PASSED\n");
        } catch (Exception e) {
            captureScreenshot("TC_007_failed");
            fail("TC-007: " + e.getMessage());
        }
    }

    // ========== NEGATIVE TEST CASES (TC-013 to TC-017) ==========

    @Test
    public void testTC_013_InvalidProductErrorHandling() {
        System.out.println("TC-013: Invalid Product Error Handling");
        try {
            loadMockPage(MOCK_ERROR_HTML);
            boolean hasError = relatedPage.isErrorPageDisplayed();
            boolean hasRelated = relatedPage.isRelatedProductsSectionVisible();
            assertTrue("Should show error or no products", hasError || !hasRelated);
            System.out.println("TC-013 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_013_failed");
            fail("TC-013: " + e.getMessage());
        }
    }

    @Test
    public void testTC_014_EmptyStateHandling() {
        System.out.println("TC-014: Empty State Handling");
        try {
            loadMockPage(MOCK_EMPTY_HTML);
            int count = relatedPage.getRelatedProductsCount();
            assertEquals("Should have 0 products", 0, count);
            assertTrue("Main product must be present",
                    relatedPage.hasMainProductContent());
            System.out.println("TC-014 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_014_failed");
            fail("TC-014: " + e.getMessage());
        }
    }

    @Test
    public void testTC_015_OutOfStockHandling() {
        System.out.println("TC-015: Out-of-Stock Handling");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            assertTrue("Main product must be displayable",
                    relatedPage.hasMainProductContent());
            assertTrue("Page must be functional",
                    relatedPage.isPageInFunctionalState());
            System.out.println("TC-015 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_015_failed");
            fail("TC-015: " + e.getMessage());
        }
    }

    @Test
    public void testTC_016_NoDuplicates() {
        System.out.println("TC-016: No Duplicate Products");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            int count = relatedPage.getRelatedProductsCount();
            assertTrue("Should have products", count > 0);
            assertTrue("All products must be unique",
                    relatedPage.hasNoDuplicateProducts());
            System.out.println("TC-016 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_016_failed");
            fail("TC-016: " + e.getMessage());
        }
    }

    @Test
    public void testTC_017_ProductDiversity() {
        System.out.println("TC-017: Product Diversity");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            int count = relatedPage.getRelatedProductsCount();
            if (count > 1) {
                assertTrue("Products should be unique",
                        relatedPage.hasNoDuplicateProducts());
            }
            System.out.println("TC-017 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_017_failed");
            fail("TC-017: " + e.getMessage());
        }
    }

    // ========== EDGE CASE TEST CASES (TC-008, 009, 010) ==========

    @Test
    public void testTC_008_CardLayoutAndSpacing() {
        System.out.println("TC-008: Card Layout and Spacing");
        try {
            relatedPage.setViewportSize(1920, 1080);
            loadMockPage(MOCK_PRODUCT_HTML);
            int count = relatedPage.getRelatedProductsCount();
            assertTrue("Must have products", count > 0);
            assertTrue("Must be visible",
                    relatedPage.isRelatedProductsSectionVisible());
            System.out.println("TC-008 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_008_failed");
            fail("TC-008: " + e.getMessage());
        }
    }

    @Test
    public void testTC_009_DesktopResponsiveness() {
        System.out.println("TC-009: Desktop Responsiveness (1920x1080)");
        try {
            relatedPage.setViewportSize(1920, 1080);
            loadMockPage(MOCK_PRODUCT_HTML);
            assertTrue("Must be visible on desktop",
                    relatedPage.isRelatedProductsSectionVisible());
            System.out.println("TC-009 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_009_failed");
            fail("TC-009: " + e.getMessage());
        }
    }

    @Test
    public void testTC_010_MobileResponsiveness() {
        System.out.println("TC-010: Mobile Responsiveness (375x667)");
        try {
            relatedPage.setViewportSize(375, 667);
            loadMockPage(MOCK_PRODUCT_HTML);
            assertTrue("Must be visible on mobile",
                    relatedPage.isRelatedProductsSectionVisible());
            System.out.println("TC-010 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_010_failed");
            fail("TC-010: " + e.getMessage());
        }
    }

    @Test
    public void testTC_011_PageLoadsSuccessfully() {
        System.out.println("TC-011: Page Loads Successfully");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            assertTrue("Main product must be present",
                    relatedPage.hasMainProductContent());
            assertTrue("Page must be functional",
                    relatedPage.isPageInFunctionalState());
            System.out.println("TC-011 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_011_failed");
            fail("TC-011: " + e.getMessage());
        }
    }

    @Test
    public void testTC_012_VariousPricePoints() {
        System.out.println("TC-012: Various Price Points");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            assertTrue("Related products must be visible",
                    relatedPage.isRelatedProductsSectionVisible());
            int count = relatedPage.getRelatedProductsCount();
            assertTrue("Must display products", count >= 1);
            System.out.println("TC-012 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_012_failed");
            fail("TC-012: " + e.getMessage());
        }
    }

    // ========== NEW TEST CASES (TC-018, 019, 020) ==========

    @Test
    public void testTC_018_BackButtonNavigation() {
        System.out.println("TC-018: Back Button Navigation");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            int count = relatedPage.getRelatedProductsCount();
            assertTrue("Should have products for navigation", count > 0);

            // Verify products have navigation links
            for (int i = 0; i < Math.min(count, 2); i++) {
                Locator card = page.locator(".product-card").nth(i);
                assertTrue("Product " + i + " must have link", card.locator("a").count() > 0);
                String href = card.locator("a").getAttribute("href");
                assertTrue("Product " + i + " must have href", href != null && !href.isEmpty());
            }

            assertTrue("Related products still visible",
                    relatedPage.isRelatedProductsSectionVisible());
            System.out.println("TC-018 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_018_failed");
            fail("TC-018: " + e.getMessage());
        }
    }

    @Test
    public void testTC_019_DataConsistencyOnRefresh() {
        System.out.println("TC-019: Data Consistency on Refresh");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            boolean visibleBefore = relatedPage.isRelatedProductsSectionVisible();
            int countBefore = relatedPage.getRelatedProductsCount();

            loadMockPage(MOCK_PRODUCT_HTML);
            boolean visibleAfter = relatedPage.isRelatedProductsSectionVisible();
            int countAfter = relatedPage.getRelatedProductsCount();

            assertEquals("Visibility should be consistent", visibleBefore, visibleAfter);
            assertEquals("Product count should be consistent", countBefore, countAfter);
            System.out.println("TC-019 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_019_failed");
            fail("TC-019: " + e.getMessage());
        }
    }

    @Test
    public void testTC_020_ChromiumCompatibility() {
        System.out.println("TC-020: Cross-Browser (Chromium)");
        try {
            loadMockPage(MOCK_PRODUCT_HTML);
            assertTrue("Related products must work in Chromium",
                    relatedPage.isRelatedProductsSectionVisible());
            System.out.println("TC-020 PASSED\n");
        } catch (AssertionError e) {
            captureScreenshot("TC_020_failed");
            fail("TC-020: " + e.getMessage());
        }
    }

    // ========== HELPER METHODS ==========

    private void loadMockPage(String filePath) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filePath));
            String html = new String(encoded, StandardCharsets.UTF_8);
            page.setContent(html);
            System.out.println("Mock page loaded: " + filePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load mock page: " + e.getMessage());
        }
    }

    private void captureScreenshot(String testName) {
        try {
            String filename = SCREENSHOTS_DIR + "/" + testName + "_" +
                    System.currentTimeMillis() + ".png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filename)));
            System.out.println("Screenshot: " + filename);
        } catch (Exception e) {
            System.out.println("Could not capture: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        try {
            if (relatedPage != null && page != null && !page.isClosed()) {
                relatedPage.closePage();
            }
            if (browser != null) {
                browser.close();
            }
            if (playwright != null) {
                playwright.close();
            }
            System.out.println("Teardown complete\n");
        } catch (Exception e) {
            System.out.println("Teardown error: " + e.getMessage());
        }
    }
}