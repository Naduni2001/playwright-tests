# eBay Related Products Test Automation

## Overview
This project contains comprehensive test automation for eBay's Related Products feature using Java, Playwright, and JUnit.

## Project Structure
```
src/
├── main/java/
│   └── com/ebay/tests/
└── test/
    ├── java/
    │   └── com/ebay/tests/
    │       ├── pages/
    │       │   ├── BasePage.java
    │       │   └── RelatedProductsPage.java
    │       └── tests/
    │           ├── FirstTest.java
    │           ├── MockRelatedProductsTest.java
    │           ├── RelatedProductsTest.java
    │           └── FunctionalTests.java
    └── resources/
        ├── mock_ebay_product.html
        ├── mock_ebay_empty.html
        └── mock_ebay_error.html
```

## Technologies Used
- **Language**: Java 8+
- **Test Framework**: JUnit 4
- **Browser Automation**: Playwright 1.40.0
- **Build Tool**: Maven 3.9.12
- **IDE**: IntelliJ IDEA

## Test Suite

### FirstTest (1 test)
- Verifies Playwright framework is working
- Tests mock eBay product page

### MockRelatedProductsTest (20 tests)
Production-grade mock-based tests:

**Positive Tests (TC-001 to TC-007)**
- TC-001: Related products section visibility
- TC-002: Product count validation
- TC-003: Product card elements
- TC-004: Main product exclusion
- TC-005: Same category verification
- TC-006: Price range logic
- TC-007: Click navigation

**Negative Tests (TC-013 to TC-017)**
- TC-013: Error handling
- TC-014: Empty state handling
- TC-015: Out-of-stock handling
- TC-016: No duplicate products
- TC-017: Product diversity

**Edge Cases (TC-008 to TC-012, TC-018 to TC-020)**
- TC-008: Card layout and spacing
- TC-009: Desktop responsiveness (1920x1080)
- TC-010: Mobile responsiveness (375x667)
- TC-011: Page load functionality
- TC-012: Various price points
- TC-018: Back button navigation
- TC-019: Data consistency on refresh
- TC-020: Cross-browser compatibility

## Key Features

### Page Object Model
- All selectors encapsulated in RelatedProductsPage.java
- Single point of change for selector updates
- Business logic methods

### Mock Data Testing
- Uses mock HTML instead of live websites
- No network dependency
- Consistent, reliable results
- No geo-blocking issues

### Professional Code Quality
- Screenshot capture on failures
- Comprehensive error handling
- Clear test descriptions
- Proper setup and teardown

## Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Mock Tests Only
```bash
mvn clean test -Dtest=MockRelatedProductsTest
```

### Run FirstTest
```bash
mvn clean test -Dtest=FirstTest
```

### Run Specific Test
```bash
mvn clean test -Dtest=MockRelatedProductsTest#testTC_001_RelatedProductsSectionVisible
```

## Architecture

### Encapsulation
- All Playwright interactions hidden in page objects
- Tests call business logic methods
- Easy to maintain

### Robustness
- Multiple selector fallbacks
- Error detection and handling
- Automatic screenshot capture

### Maintainability
- Clear naming conventions
- Comprehensive comments
- Single responsibility

## Mock Data

### mock_ebay_product.html
- Main product: Premium Leather Bifold Wallet ($29.99)
- 6 related products with varying prices

### mock_ebay_empty.html
- Product with no related products
- Tests empty state

### mock_ebay_error.html
- Error page for invalid products
- Tests error detection

## Best Practices

1. **Page Object Model**: Encapsulation of selectors
2. **Mock Testing**: No external dependencies
3. **DRY Principle**: Reusable methods
4. **Clear Assertions**: Descriptive messages
5. **Screenshot Capture**: Visual evidence
6. **Proper Cleanup**: Resource management

## System Requirements

- Java 8 or higher
- Maven 3.6+
- Windows, Mac, or Linux
- Chrome/Chromium browser

## How to Setup

1. Clone the repository
2. Navigate to project directory
3. Run: `mvn clean test`
4. All 21 tests will pass

## Notes

- All tests use mock data
- Screenshots saved to target/screenshots/
- Tests run offline
- No setup required

## Author
Naduni De Silva

## Status
- All 21 tests passing
- Production-ready code
- Ready for use