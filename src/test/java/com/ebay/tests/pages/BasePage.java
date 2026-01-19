package com.ebay.tests.pages;

import com.microsoft.playwright.Page;

/**
 * BasePage.java
 * Base class for all page objects
 * Contains common methods used across all tests
 */
public class BasePage {

    protected Page page;

    // Constructor
    public BasePage(Page page) {
        this.page = page;
    }

    // Navigate to URL
    public void navigateTo(String url) {
        page.navigate(url);
        page.waitForLoadState();
        System.out.println("Navigated to: " + url);
    }

    // Get page title
    public String getPageTitle() {
        return page.title();
    }

    // Get current URL
    public String getCurrentUrl() {
        return page.url();
    }

    // Check if element visible
    public boolean isElementVisible(String selector) {
        try {
            return page.isVisible(selector);
        } catch (Exception e) {
            return false;
        }
    }

    // Click element
    public void clickElement(String selector) {
        page.click(selector);
        System.out.println("Clicked: " + selector);
    }

    // Get element text
    public String getElementText(String selector) {
        String text = page.textContent(selector);
        return text != null ? text.trim() : "";
    }

    // Get element count
    public int getElementCount(String selector) {
        try {
            return page.locator(selector).count();
        } catch (Exception e) {
            return 0;
        }
    }

    // Wait for page load
    public void waitForPageLoad() {
        page.waitForLoadState();
    }

    // Go back
    public void goBack() {
        page.goBack();
        page.waitForLoadState();
    }

    // Reload page
    public void reloadPage() {
        page.reload();
        page.waitForLoadState();
    }

    // Set viewport size (for responsive testing)
    public void setViewportSize(int width, int height) {
        page.setViewportSize(width, height);
        System.out.println("Viewport: " + width + "x" + height);
    }

    // Measure page load time
    public long getPageLoadTime(String url) {
        long startTime = System.currentTimeMillis();
        page.navigate(url);
        page.waitForLoadState();
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        System.out.println("Load time: " + loadTime + "ms");
        return loadTime;
    }

    // Close page
    public void closePage() {
        if (page != null && !page.isClosed()) {
            page.close();
            System.out.println("Page closed");
        }
    }
}