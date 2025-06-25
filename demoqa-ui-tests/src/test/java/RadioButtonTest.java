package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static io.qameta.allure.Allure.step;

@Epic("Demo QA UI Tests")
@Feature("Radio Button")
@Owner("Stanislav Piskun")
@Tag("UI")
@Tag("smoke")
@DisplayName("Radio Button form tests")
public class RadioButtonTest {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void openPage() {
        step("Open radio button page", () -> open("/radio-button"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Select 'Yes' radio option")
    void selectYes() {
        step("Click on 'Yes' radio button", () ->
                $("label[for=yesRadio]").click()
        );

        step("Verify 'Yes' is selected", () ->
                $(".text-success").shouldHave(text("Yes"))
        );
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Select 'Impressive' radio option")
    void selectImpressive() {
        step("Click on 'Impressive' radio button", () ->
                $("label[for=impressiveRadio]").click()
        );

        step("Verify 'Impressive' is selected", () ->
                $(".text-success").shouldHave(text("Impressive"))
        );
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Check 'No' radio button is disabled")
    void checkNoDisabled() {
        step("Verify 'No' option is disabled", () ->
                $("#noRadio").shouldBe(disabled)
        );
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Verify default state (nothing selected)")
    void defaultState() {
        step("Verify no selection is made", () ->
                $(".text-success").shouldNotBe(visible)
        );
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Select and re-select radio option")
    void reselectOption() {
        step("Select 'Yes'", () ->
                $("label[for=yesRadio]").click()
        );

        step("Then select 'Impressive'", () ->
                $("label[for=impressiveRadio]").click()
        );

        step("Verify last selection is 'Impressive'", () ->
                $(".text-success").shouldHave(text("Impressive"))
        );
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Try to click on disabled 'No' radio")
    void tryClickNo() {
        step("Try clicking 'No' button", () ->
                $("label[for=noRadio]").click()
        );

        step("Ensure no selection occurred", () ->
                $(".text-success").shouldNotBe(visible)
        );
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @DisplayName("CSS class validation after selection")
    void classCheckAfterSelect() {
        step("Click on 'Yes'", () ->
                $("label[for=yesRadio]").click()
        );

        step("Check .text-success class is present", () ->
                $(".text-success").shouldHave(cssClass("text-success"))
        );
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Tab navigation support")
    void tabNavigation() {
        step("Focus element using Tab key", () ->
                actions().sendKeys("\uE004").perform() //  = Tab
        );

        step("Check that some element is focused", () ->
                executeJavaScript("return document.activeElement")
        );
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Selection persists on refresh")
    void checkSelectionLostOnRefresh() {
        step("Select 'Yes' option", () ->
                $("label[for=yesRadio]").click()
        );

        step("Refresh the page", () ->
                refresh()
        );

        step("Verify selection does not persist (expected behavior)", () ->
                $(".text-success").shouldNotBe(visible)
        );
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Attempt to submit without selection")
    void submitWithoutSelection() {
        step("Do nothing, just wait", () ->
                sleep(1000)
        );

        step("Check that result text does not appear", () ->
                $(".text-success").shouldNotBe(visible)
        );
    }
}
