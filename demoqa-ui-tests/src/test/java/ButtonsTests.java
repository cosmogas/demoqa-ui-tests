package com.demoqa.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.DisplayName;



import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static io.qameta.allure.Allure.step;

@Epic("DemoQA")
@Feature("Buttons")
@Tag("buttons")
public class ButtonsTests {

    @BeforeEach
    void openPage() {
        open("https://demoqa.com/buttons");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Double click")
    @DisplayName("User can perform double click on button")
    @Description("Checks that a double click triggers expected confirmation message")
    void doubleClickTest() {
        step("Double click on the button", () ->
                $("#doubleClickBtn").doubleClick()
        );

        step("Verify success message", () ->
                $("#doubleClickMessage").shouldHave(text("You have done a double click"))
        );
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Right click")
    @DisplayName("User can perform right click on button")
    @Description("Checks that a right click triggers expected confirmation message")
    void rightClickTest() {
        step("Right click on the button", () ->
                $("#rightClickBtn").contextClick()
        );

        step("Verify success message", () ->
                $("#rightClickMessage").shouldHave(text("You have done a right click"))
        );
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Dynamic click")
    @DisplayName("User can perform dynamic click (left click) on third button")
    @Description("Verifies that a normal left-click works on the dynamically generated button")
    void dynamicClickTest() {
        step("Click the dynamic (third) button", () ->
                $$("button").find(text("Click Me")).click()
        );

        step("Verify success message", () ->
                $("#dynamicClickMessage").shouldHave(text("You have done a dynamic click"))
        );
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Story("Negative case: No message without click")
    @DisplayName("No success message shown without clicking")
    @Description("Ensures that no message is visible before any interaction")
    void noMessageWithoutClick() {
        step("Verify no messages are visible on page load", () -> {
            $("#doubleClickMessage").shouldNotBe(visible);
            $("#rightClickMessage").shouldNotBe(visible);
            $("#dynamicClickMessage").shouldNotBe(visible);
        });
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Failing test: Wrong message expected")
    @DisplayName("Intentionally failing test: Expect incorrect message")
    @Description("Clicks and verifies incorrect expected text to demonstrate failure handling")
    void failingTestExpectingWrongText() {
        step("Click dynamic button", () ->
                $$("button").find(text("Click Me")).click()
        );

        step("Expect wrong text intentionally", () ->
                $("#dynamicClickMessage").shouldHave(text("This is wrong text")) // FAIL
        );
    }
}

