# eBay Related Products Test Automation

## Overview
Comprehensive test automation for eBay's Related Products feature using Java, Playwright, and JUnit.

## Technologies
- Java 8+
- Playwright 1.40.0
- JUnit 4
- Maven 3.9.12

## Test Suite
- **FirstTest**: Framework verification (1 test)
- **MockRelatedProductsTest**: Production-grade tests (20 tests)
- **Total**: 21 tests - ALL PASSING 

## Test Coverage
- Positive scenarios (TC-001 to TC-007)
- Negative scenarios (TC-013 to TC-017)
- Edge cases (TC-008 to TC-012, TC-018 to TC-020)

## Running Tests
```bash
mvn clean test
```

## Key Features
- Page Object Model
- Mock data testing
- Screenshot capture on failures
- Professional code quality

## Project Structure
```
src/
├── main/java/com/ebay/tests/
└── test/java/com/ebay/tests/
    ├── pages/
    │   ├── BasePage.java
    │   └── RelatedProductsPage.java
    └── tests/
        ├── FirstTest.java
        ├── MockRelatedProductsTest.java
        ├── RelatedProductsTest.java
        └── FunctionalProductTest.java
```

## Status
- All 21 tests passing 
- Production-ready code
- Ready for interviews

## Author
Naduni De Silva