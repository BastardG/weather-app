package ru.bastard.weather.service.io;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class IOService {

    private static final String APPDATA = System.getenv("APPDATA") + "\\weather";
    private static final File CONFIG_FILE = new File(APPDATA + "\\config.json");

    public void putDefault(String defaultCity) throws IOException {
        if(!isPrepared()) {
            createPackageAndFiles();
        }
        String rawJson = FileUtils.readFileToString(CONFIG_FILE, StandardCharsets.UTF_8);
        JSONObject configRoot = new JSONObject(rawJson);
        configRoot.put("default", defaultCity);
        FileUtils.write(CONFIG_FILE, configRoot.toString(), StandardCharsets.UTF_8);
    }

    public String getDefault() throws IOException {
        if(!isPrepared()) {
            createPackageAndFiles();
        }
        String rawJson = FileUtils.readFileToString(CONFIG_FILE, StandardCharsets.UTF_8);
        JSONObject configRoot = new JSONObject(rawJson);
        return configRoot.getString("default");
    }

    private void createPackageAndFiles() throws IOException {
        FileUtils.createParentDirectories(CONFIG_FILE);
        JSONObject configRoot = new JSONObject();
        configRoot.put("default", "");
        configRoot.put("suggestions", new JSONArray());
        CONFIG_FILE.createNewFile();
        FileUtils.write(CONFIG_FILE, configRoot.toString(), StandardCharsets.UTF_8);
    }

    private boolean isPrepared() {
        return CONFIG_FILE.exists();
    }

}
