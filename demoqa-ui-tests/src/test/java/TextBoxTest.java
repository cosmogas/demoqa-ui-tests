package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Epic("Demo QA UI Tests")
@Feature("Text Box Form")
@Owner("Stanislav Piskun")
@Tag("UI")
@Tag("regression")
@DisplayName("Text Box form tests")
public class TextBoxTest {

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Successful form submit")
    void successfulSubmit() {
        step("Open page", () -> open("/text-box"));
        step("Fill and submit form", () -> {
            $("#userName").setValue("Stanislav QA");
            $("#userEmail").setValue("stanislav@example.com");
            $("#currentAddress").setValue("Street 1");
            $("#permanentAddress").setValue("Street 2");
            $("#submit").scrollTo().click();
        });
        step("Check output", () -> {
            $("#output #name").shouldHave(text("Stanislav QA"));
            $("#output #email").shouldHave(text("stanislav@example.com"));
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Empty submit")
    void emptyFormSubmit() {
        open("/text-box");
        $("#submit").scrollTo().click();
        $("#output").shouldNotBe(visible);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Invalid email")
    void invalidEmail() {
        open("/text-box");
        $("#userName").setValue("Test");
        $("#userEmail").setValue("not-an-email");
        $("#submit").scrollTo().click();
        $("#userEmail").shouldHave(cssClass("field-error"));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Long text input")
    void longTextInput() {
        String text = "Very Long Address ".repeat(10);
        open("/text-box");
        $("#userName").setValue("User");
        $("#userEmail").setValue("user@example.com");
        $("#currentAddress").setValue(text);
        $("#permanentAddress").setValue(text);
        $("#submit").scrollTo().click();
        $("#output").shouldBe(visible);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Broken test")
    void brokenTest() {
        open("/text-box");
        $("#output").shouldHave(text("Nothing to see here"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Special characters")
    void specialCharacters() {
        open("/text-box");
        $("#userName").setValue("!@#$%^&*");
        $("#userEmail").setValue("test@domain.com");
        $("#submit").scrollTo().click();
        $("#output #name").shouldHave(text("!@#$%^&*"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Form reset")
    void formReset() {
        open("/text-box");
        $("#userName").setValue("ToBeCleared");
        $("#userName").clear();
        $("#userName").shouldHave(value(""));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Email case insensitivity")
    void emailCaseInsensitive() {
        open("/text-box");
        $("#userName").setValue("User");
        $("#userEmail").setValue("TEST@DOMAIN.COM");
        $("#submit").scrollTo().click();
        $("#output #email").shouldHave(text("TEST@DOMAIN.COM"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Whitespace trimming")
    void whitespaceTrimming() {
        open("/text-box");
        $("#userName").setValue("   User   ");
        $("#userEmail").setValue("email@demo.com");
        $("#submit").scrollTo().click();
        $("#output #name").shouldHave(text("User"));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Only name filled")
    void onlyName() {
        open("/text-box");
        $("#userName").setValue("User Only");
        $("#submit").scrollTo().click();
        $("#output #name").shouldHave(text("User Only"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("HTML input")
    void htmlInInput() {
        open("/text-box");
        $("#userName").setValue("<b>bold</b>");
        $("#userEmail").setValue("email@html.com");
        $("#submit").scrollTo().click();
        $("#output #name").shouldHave(text("<b>bold</b>"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Clear all fields")
    void clearFields() {
        open("/text-box");
        $("#userName").setValue("abc").clear();
        $("#userEmail").setValue("a@b.com").clear();
        $("#currentAddress").setValue("abc").clear();
        $("#permanentAddress").setValue("abc").clear();
        $("#userName").shouldHave(value(""));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Address only")
    void addressOnly() {
        open("/text-box");
        $("#currentAddress").setValue("Address1");
        $("#submit").scrollTo().click();
        $("#output #currentAddress").shouldHave(text("Address1"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Empty email error")
    void emptyEmail() {
        open("/text-box");
        $("#userName").setValue("User");
        $("#userEmail").setValue("");
        $("#submit").scrollTo().click();
        $("#userEmail").shouldHave(cssClass("field-error"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Multiple submits")
    void multipleSubmits() {
        open("/text-box");
        $("#userName").setValue("MultiUser");
        $("#userEmail").setValue("multi@demo.com");
        $("#submit").scrollTo().click();
        $("#submit").click();
        $("#output #name").shouldHave(text("MultiUser"));
    }
}

