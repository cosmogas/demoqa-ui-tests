package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import static io.qameta.allure.Allure.step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;

@Epic("Demo QA UI Tests")
@Feature("Check Box Form")
@Owner("Stanislav Piskun")
@Tag("UI")
@Tag("regression")
@DisplayName("Check Box tests")
public class CheckBoxTest {

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void openCheckBoxPage() {
        step("Open Check Box page", () -> open("/checkbox"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Expand all nodes")
    void expandAll() {
        step("Click 'Expand all' button", () -> $(".rct-option-expand-all").click());
        step("Verify all nodes are visible", () -> $$(".rct-node").filter(visible).shouldHave(sizeGreaterThan(5)));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Collapse all nodes")
    void collapseAll() {
        step("Click 'Expand all' and then 'Collapse all'", () -> {
            $(".rct-option-expand-all").click();
            $(".rct-option-collapse-all").click();
        });
        step("Verify only main node remains visible", () -> $$(".rct-node").filter(visible).shouldHave(size(1)));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Select 'Home' checkbox")
    void selectHomeCheckbox() {
        step("Click on 'Home' checkbox", () -> $(".rct-node .rct-checkbox").click());
        step("Verify output has 'You have selected : home'", () -> $("#result").shouldHave(text("home")));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Select 'Desktop' checkbox")
    void selectDesktopCheckbox() {
        step("Expand all", () -> $(".rct-option-expand-all").click());
        step("Click on 'Desktop' checkbox", () -> $$x("//label[text()='Desktop']/../span[@class='rct-checkbox']").first().click());
        step("Verify output has 'desktop'", () -> $("#result").shouldHave(text("desktop")));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Select multiple checkboxes")
    void selectMultiple() {
        step("Expand all and select 'Documents' and 'Downloads'", () -> {
            $(".rct-option-expand-all").click();
            $$x("//label[text()='Documents']/../span[@class='rct-checkbox']").first().click();
            $$x("//label[text()='Downloads']/../span[@class='rct-checkbox']").first().click();
        });
        step("Verify output contains both", () -> {
            $("#result").shouldHave(text("documents"));
            $("#result").shouldHave(text("downloads"));
        });
    }
}


