package com.blazedemo.utils;

import com.blazedemo.utils.logs.LogsUtil;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.copyFile;

public class FileUtils {

    private static final String USER_DIR = System.getProperty("user.dir") + File.separator;

    public static void cleanDirectory(File path) {
        try {
            org.apache.commons.io.FileUtils.deleteQuietly(path);
        } catch (Exception e) {
            LogsUtil.error("Couldn't delete directory, Error Message:", e.getMessage());
        }
    }

    public static void createDirectory(String path) {
        try {
            File file = new File(USER_DIR + path);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            LogsUtil.error("Couldn't create directory");
        }
    }

    public static void forceDelete(File file) {
        try {
            org.apache.commons.io.FileUtils.forceDeleteOnExit(file);
            LogsUtil.info("File deleted: " + file.getAbsolutePath());
        } catch (IOException e) {
            LogsUtil.error("Failed to delete file: " + file.getAbsolutePath(), e.getMessage());
        }
    }


    public static void renameFile(String oldFileName, String newFileName) {
        try {
            var targetFile = new File(oldFileName);
            String targetDirectory = targetFile.getParentFile().getAbsolutePath();
            File newFile = new File(targetDirectory + File.separator + newFileName);
            if (!targetFile.getPath().equals(newFile.getPath())) {
                copyFile(targetFile, newFile);
                org.apache.commons.io.FileUtils.deleteQuietly(targetFile);
                LogsUtil.info("File renamed successfully");
            }
        } catch (IOException e) {
            LogsUtil.error("Failed to rename the file, Error Message: ", e.getMessage());
        }

    }

    public static boolean isFileDownloaded(String fileName) {
        String userDir = System.getProperty("user.dir");
        File file = new File(userDir + "/src/test/resources/downloads" + File.separator + fileName);
        return file.exists();
    }
}
