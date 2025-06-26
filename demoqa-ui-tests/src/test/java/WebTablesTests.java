package com.demoqa.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;


import static io.qameta.allure.Allure.step;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@Epic("DemoQA")
@Feature("Web Tables")
@Tag("webtables")
public class WebTablesTests {

    @BeforeEach
    void openPage() {
        open("https://demoqa.com/webtables");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Add new entry to table")
    @DisplayName("User can successfully add a new person to Web Table")
    @Description("Fills all fields in registration form and checks if new record appears in the table")
    void addNewPersonToTable() {
        step("Click 'Add' button", () -> $("#addNewRecordButton").click());

        step("Fill out registration form", () -> {
            $("#firstName").setValue("John");
            $("#lastName").setValue("Doe");
            $("#userEmail").setValue("john.doe@example.com");
            $("#age").setValue("29");
            $("#salary").setValue("5000");
            $("#department").setValue("Engineering");
        });

        step("Submit the form", () -> $("#submit").click());

        step("Verify new entry appears in table", () ->
                $(".rt-tbody").shouldHave(text("John"), text("Doe"), text("29"), text("john.doe@example.com"))
        );
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Delete entry from table")
    @DisplayName("User can delete an entry from Web Table")
    @Description("Deletes first row in the table and verifies it's removed")
    void deleteEntryFromTable() {
        step("Delete first entry", () ->
                $("[title='Delete']").click()
        );

        step("Verify entry removed", () ->
                $(".rt-tbody").shouldNotHave(text("Cierra")) // default first record
        );
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Edit existing entry")
    @DisplayName("User can edit existing person in Web Table")
    @Description("Edits existing entry and verifies updated values")
    void editEntryInTable() {
        step("Click edit on first row", () ->
                $("[title='Edit']").click()
        );

        step("Change Department to 'QA'", () -> {
            SelenideElement departmentInput = $("#department");
            departmentInput.clear();
            departmentInput.setValue("QA");
        });

        step("Submit changes", () -> $("#submit").click());

        step("Verify changes are applied", () ->
                $(".rt-tbody").shouldHave(text("QA"))
        );
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Story("Negative case: Add user with empty fields")
    @DisplayName("Form submission fails when all fields are empty")
    @Description("Tries to submit an empty registration form and verifies form is not submitted")
    void submitEmptyForm() {
        step("Click 'Add' button", () -> $("#addNewRecordButton").click());

        step("Click 'Submit' without filling the form", () -> $("#submit").click());

        step("Verify modal is still visible", () ->
                $("#registration-form-modal").shouldBe(visible)
        );
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Search functionality")
    @DisplayName("User can search for existing entry")
    @Description("Enters search keyword and checks if relevant row is filtered")
    void searchInTable() {
        step("Search for 'Cierra'", () ->
                $("#searchBox").setValue("Cierra")
        );

        step("Verify table filtered by keyword", () ->
                $(".rt-tbody").shouldHave(text("Cierra"))
        );
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Failing test for demo purposes")
    @DisplayName("Intentionally failing test: Check non-existing user")
    @Description("Searches for a user that doesn't exist and expects to find it (fail expected)")
    void failingTestExpectingNonExistingUser() {
        step("Search for 'NonExistingUser123'", () ->
                $("#searchBox").setValue("NonExistingUser123")
        );

        step("Expect to find the non-existing user (this will fail)", () ->
                $(".rt-tbody").shouldHave(text("NonExistingUser123")) // will fail
        );
    }
}

