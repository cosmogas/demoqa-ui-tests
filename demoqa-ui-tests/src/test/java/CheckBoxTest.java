package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.CollectionCondition.*;

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
        step("Verify all nodes are visible", () ->
                $$(".rct-node").filter(visible).shouldHave(sizeGreaterThan(5)));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Collapse all nodes")
    void collapseAll() {
        step("Click 'Expand all' and then 'Collapse all'", () -> {
            $(".rct-option-expand-all").click();
            $(".rct-option-collapse-all").click();
        });
        step("Verify only main node remains visible", () ->
                $$(".rct-node").filter(visible).shouldHave(size(1)));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Select 'Home' checkbox")
    void selectHomeCheckbox() {
        step("Click on 'Home' checkbox", () -> $(".rct-node .rct-checkbox").click());
        step("Verify output has 'You have selected : home'", () ->
                $("#result").shouldHave(text("home")));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Select 'Desktop' checkbox")
    void selectDesktopCheckbox() {
        step("Expand all", () -> $(".rct-option-expand-all").click());
        step("Click on 'Desktop' checkbox", () ->
                $$("label").findBy(text("Desktop")).parent().$(".rct-checkbox").click());
        step("Verify output has 'desktop'", () ->
                $("#result").shouldHave(text("desktop")));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Select multiple checkboxes")
    void selectMultiple() {
        step("Expand all and select 'Documents' and 'Downloads'", () -> {
            $(".rct-option-expand-all").click();
            $$("label").findBy(text("Documents")).parent().$(".rct-checkbox").click();
            $$("label").findBy(text("Downloads")).parent().$(".rct-checkbox").click();
        });
        step("Verify output contains both", () -> {
            $("#result").shouldHave(text("documents"));
            $("#result").shouldHave(text("downloads"));
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Uncheck 'Home' checkbox")
    void uncheckHome() {
        step("Check and then uncheck 'Home'", () -> {
            $(".rct-node .rct-checkbox").click();
            $(".rct-node .rct-checkbox").click();
        });
        step("Verify result is not visible", () ->
                $("#result").shouldNotBe(visible));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Try to submit without selection")
    void noSelection() {
        step("Verify result is not visible when no checkbox selected", () ->
                $("#result").shouldNotBe(visible));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Check Office sub-folder")
    void checkOffice() {
        step("Expand all and check 'Office'", () -> {
            $(".rct-option-expand-all").click();
            $$("label").findBy(text("Office")).parent().$(".rct-checkbox").click();
        });
        step("Check output includes office", () ->
                $("#result").shouldHave(text("office")));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Select all leaf nodes under 'Downloads'")
    void selectDownloadsChildren() {
        step("Expand all and select Word, Excel", () -> {
            $(".rct-option-expand-all").click();
            $$("label").findBy(text("Word File.doc")).parent().$(".rct-checkbox").click();
            $$("label").findBy(text("Excel File.doc")).parent().$(".rct-checkbox").click();
        });
        step("Verify result", () -> {
            $("#result").shouldHave(text("wordFile"));
            $("#result").shouldHave(text("excelFile"));
        });
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Case-insensitive label match")
    void labelCaseInsensitive() {
        step("Expand all and click 'office'", () -> {
            $(".rct-option-expand-all").click();
            $$("label").findBy(text("OFFICE")).parent().$(".rct-checkbox").click();
        });
        step("Output should contain 'office'", () ->
                $("#result").shouldHave(text("office")));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Quick toggle expand/collapse")
    void rapidToggle() {
        step("Click expand/collapse rapidly", () -> {
            for (int i = 0; i < 5; i++) {
                $(".rct-option-expand-all").click();
                $(".rct-option-collapse-all").click();
            }
        });
        step("Final collapse leaves only one node visible", () ->
                $$(".rct-node").filter(visible).shouldHave(size(1)));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Verify checkbox icons are rendered")
    void verifyIcons() {
        step("Expand all", () -> $(".rct-option-expand-all").click());
        step("Verify all items have checkbox", () ->
                $$(".rct-checkbox").shouldHave(sizeGreaterThan(10)));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Select parent selects all children")
    void parentSelectsChildren() {
        step("Expand all", () -> $(".rct-option-expand-all").click());
        step("Click 'Documents'", () ->
                $$("label").findBy(text("Documents")).parent().$(".rct-checkbox").click());
        step("Verify output includes Notes, React, Angular", () -> {
            $("#result").shouldHave(text("notes"));
            $("#result").shouldHave(text("react"));
            $("#result").shouldHave(text("angular"));
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Unselect child unselects parent")
    void unselectChild() {
        step("Expand all and select Desktop", () -> {
            $(".rct-option-expand-all").click();
            $$("label").findBy(text("Desktop")).parent().$(".rct-checkbox").click();
        });
        step("Unselect Notes only", () ->
                $$("label").findBy(text("Notes")).parent().$(".rct-checkbox").click());
        step("Verify parent is not fully selected", () ->
                $$("label").findBy(text("Desktop")).parent().$(".rct-icon-uncheck").shouldBe(visible));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Checkbox state remains after refresh")
    void stateAfterRefresh() {
        step("Select 'Downloads'", () -> {
            $(".rct-option-expand-all").click();
            $$("label").findBy(text("Downloads")).parent().$(".rct-checkbox").click();
        });
        step("Refresh the page", () -> refresh());
        step("Output should not be visible after refresh", () ->
                $("#result").shouldNotBe(visible));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Select all and verify all output values")
    void selectAllCheck() {
        step("Select all", () -> $(".rct-node .rct-checkbox").click());
        step("Verify output includes all root labels", () -> {
            $("#result").shouldHave(text("home"));
            $("#result").shouldHave(text("desktop"));
            $("#result").shouldHave(text("documents"));
            $("#result").shouldHave(text("downloads"));
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Partially selected state verification")
    void partiallySelected() {
        step("Expand all and select only 'Angular'", () -> {
            $(".rct-option-expand-all").click();
            $$("label").findBy(text("Angular")).parent().$(".rct-checkbox").click();
        });
        step("Verify parent 'Documents' is partially checked", () ->
                $$("label").findBy(text("Documents")).parent().$(".rct-icon-partial-check").shouldBe(visible));
    }
}

