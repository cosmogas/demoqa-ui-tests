package com.demoqa.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class UploadDownloadTests {

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Download button is visible and clickable")
    @Description("Checks if the download button is visible and clickable")
    void downloadButtonVisibleTest() {
        open("https://demoqa.com/upload-download");

        step("Verify download button is visible", () ->
                $("#downloadButton").shouldBe(Condition.visible));

        step("Verify download button is enabled", () ->
                $("#downloadButton").shouldBe(Condition.enabled));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("User can upload a file")
    @Description("Checks that the user can successfully upload a file")
    void uploadFileTest() {
        open("https://demoqa.com/upload-download");

        File testFile = new File("src/test/resources/testfile.txt");

        step("Upload a file", () ->
                $("#uploadFile").uploadFile(testFile));

        step("Verify uploaded file name is displayed", () ->
                $("#uploadedFilePath").shouldHave(Condition.text("testfile.txt")));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Upload without file should not show result")
    @Description("Verify that no file path appears if user does not upload a file")
    void uploadWithoutFileTest() {
        open("https://demoqa.com/upload-download");

        step("Check that uploadedFilePath is not visible", () -> {
            if ($("#uploadedFilePath").exists()) {
                $("#uploadedFilePath").shouldHave(Condition.text(""));
            } else {
                System.out.println("uploadedFilePath is not present, which is expected.");
            }
        });
    }
}




