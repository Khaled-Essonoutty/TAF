package com.blazedemo.utils.report;


import com.blazedemo.utils.OSUtils;
import com.blazedemo.utils.TerminalUtils;
import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.logs.LogsUtil;
import org.apache.commons.io.FileUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.blazedemo.utils.FileUtils.renameFile;
import static com.blazedemo.utils.dataReader.PropertiesFileReader.getPropertyValue;
import static com.blazedemo.utils.report.AllureConstants.HISTORY_FOLDER;
import static com.blazedemo.utils.report.AllureConstants.RESULTS_HISTORY_FOLDER;


public class AllureReportGenerator {
    //Generate Allure report
    //--single-file - generate single file report
    public static void generateReports(boolean isSingleFile) {
        Path outputFolder = isSingleFile ? AllureConstants.REPORT_PATH : AllureConstants.FULL_REPORT_PATH;
        // allure generate -o reports --single-file --clean
        List<String> command = new ArrayList<>(List.of(
                AllureBinaryManager.getExecutable().toString(),
                "generate",
                AllureConstants.RESULTS_FOLDER.toString(),
                "-o", outputFolder.toString(),
                "--clean"
        ));
        if (isSingleFile) command.add("--single-file");
        com.blazedemo.utils.TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));
    }

    //rename report file to AllureReport_timestamp.html
    public static String renameReport() {
        String newFileName = AllureConstants.REPORT_PREFIX + TimeManager.getTimestamp() + AllureConstants.REPORT_EXTENSION; // AllureReport_20250720_211230.html
        renameFile(AllureConstants.REPORT_PATH.resolve(AllureConstants.INDEX_HTML).toString(), newFileName);
        return newFileName;
    }

    //open Allure report in browser
    public static void openReport(String reportFileName) {
        if (!getPropertyValue("executionType").toLowerCase().contains("local")) return;

        Path reportPath = AllureConstants.REPORT_PATH.resolve(reportFileName);
        String absolutePath = reportPath.toAbsolutePath().toString();

        switch (OSUtils.getCurrentOS()) {
            case WINDOWS -> TerminalUtils.executeTerminalCommand("cmd.exe", "/c", "start", "", "\"" + absolutePath + "\"");
            case MAC, LINUX -> TerminalUtils.executeTerminalCommand("open", absolutePath);
            default -> LogsUtil.warn("Opening Allure Report is not supported on this OS.");
        }
    }

    //copy history folder to results folder
    public static void copyHistory() {
        try {
            FileUtils.copyDirectory(HISTORY_FOLDER.toFile(), RESULTS_HISTORY_FOLDER.toFile());
        } catch (Exception e) {
            LogsUtil.error("Error copying history files", e.getMessage());
        }
    }

}
