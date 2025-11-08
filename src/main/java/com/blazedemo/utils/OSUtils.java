package com.blazedemo.utils;

public class OSUtils {
    public enum OS {WINDOWS, LINUX, MAC ,OTHER};

    public static OS getCurrentOS()
    {
        String os = System.getProperty("os.name");
        System.out.println(os);
        if(os.contains("Windows")){return OS.WINDOWS;}
        else if (os.contains("nix") || os.contains("nux")) {return OS.LINUX;}
        else if (os.contains("mac")) {return OS.MAC;}
        else {return OS.OTHER;}
    }
}
