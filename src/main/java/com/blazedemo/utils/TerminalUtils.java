package com.blazedemo.utils;

import com.blazedemo.utils.logs.LogsUtil;

import java.io.IOException;

public class TerminalUtils {
    public static void executeTerminalCommand(String... commandParts) {
        try {
            Process process = Runtime.getRuntime().exec(commandParts); //allure generate -o reports --single-file --clean
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LogsUtil.error("Command failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            LogsUtil.error("Failed to execute terminal command: " + String.join(" ", commandParts), e.getMessage());
        }
    }
}
