package com.demoqa.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static io.qameta.allure.Allure.step;

@Epic("DemoQA")
@Feature("Links")
@Tag("links")
public class LinksTests {

    @BeforeEach
    void openPage() {
        open("https://demoqa.com/links");
        removeAds();
    }

    private void removeAds() {
        sleep(1000); // дать iframe подгрузиться
        executeJavaScript("document.querySelectorAll('iframe, .adsbygoogle, .advert').forEach(e => e.remove());");
        sleep(500);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Check home link opens in new tab")
    @DisplayName("Clicking Home link opens correct URL in new tab")
    @Description("Verifies that clicking the Home link opens the expected DemoQA home page")
    void homeLinkOpensInNewTab() {
        step("Click on Home link", () ->
                $("#simpleLink").click()
        );

        step("Switch to new tab and verify URL", () -> {
            switchTo().window(1);
            webdriver().shouldHave(url("https://demoqa.com/"));
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Check created API response")
    @DisplayName("Verify status message for Created (201) API link")
    @Description("Clicks 'Created' link and checks that correct status is displayed")
    void createdLinkTest() {
        step("Click on Created link", () ->
                $("#created").click()
        );

        step("Verify response message appears", () ->
                $("#linkResponse").shouldBe(visible)
                        .shouldHave(text("Link has responded with staus 201 and status text Created"))
        );
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Check unauthorized API response")
    @DisplayName("Verify status message for Unauthorized (401) API link")
    @Description("Clicks 'Unauthorized' link and checks that correct status is displayed")
    void unauthorizedLinkTest() {
        step("Click on Unauthorized link", () ->
                executeJavaScript("document.getElementById('unauthorized').click();")
        );

        step("Verify response message appears", () ->
                $("#linkResponse").shouldBe(visible)
                        .shouldHave(text("Link has responded with staus 401 and status text Unauthorized"))
        );
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Check not found API response")
    @DisplayName("Verify status message for Not Found (404) API link")
    @Description("Clicks 'Not Found' link and checks that correct status is displayed")
    void notFoundLinkTest() {
        step("Click on Not Found link via JS", () ->
                executeJavaScript("document.getElementById('invalid-url').click();")
        );

        step("Verify response message appears", () ->
                $("#linkResponse").shouldBe(visible)
                        .shouldHave(text("Link has responded with staus 404 and status text Not Found"))
        );
    }
}


