package ru.bastard.weather.service.io;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

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

    public void putLastSearch(String lastSearch) throws IOException {
        if(!isPrepared()) {
            createPackageAndFiles();
        }
        String rawJson = FileUtils.readFileToString(CONFIG_FILE, StandardCharsets.UTF_8);
        JSONObject configRoot = new JSONObject(rawJson);
        List<String> lastSearchesList = lastSearchesToList();
        ArrayDeque<String> queue = new ArrayDeque<>(lastSearchesList);
        if(queue.size() >= 3) {
            queue.removeLast();
        }
        queue.addFirst(URLDecoder.decode(lastSearch, StandardCharsets.UTF_8));
        JSONArray jsonArray = new JSONArray(queue);
        configRoot.put("suggestions", jsonArray);
        FileUtils.write(CONFIG_FILE, configRoot.toString(), StandardCharsets.UTF_8);
    }

    public String[] getLastSearches() throws IOException {
        if (!isPrepared()) {
            createPackageAndFiles();
        }
        return lastSearchesToList().toArray(new String[]{});
    }

    private List<String> lastSearchesToList() throws IOException {
        JSONObject configRoot = new JSONObject(FileUtils.readFileToString(CONFIG_FILE, StandardCharsets.UTF_8));
        JSONArray lastSearches = configRoot.getJSONArray("suggestions");
        List<String> list = new ArrayList<>();
        for(Object o : lastSearches) {
            if(o != null) {
                list.add(o.toString());
            }
        }
        return list;
    }

    public String getLang() {
        try {
            String jsonFromFile = FileUtils.readFileToString(CONFIG_FILE, StandardCharsets.UTF_8);
            JSONObject configRoot = new JSONObject(jsonFromFile);
            String langCode = configRoot.getString("lang");
            return langCode;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void setLang(String langCode) throws IOException {
        String jsonFromFile = FileUtils.readFileToString(CONFIG_FILE, StandardCharsets.UTF_8);
        JSONObject configRoot = new JSONObject(jsonFromFile);
        configRoot.put("lang", langCode);
        FileUtils.write(CONFIG_FILE, configRoot.toString(), StandardCharsets.UTF_8);
    }

    private void createPackageAndFiles() throws IOException {
        FileUtils.createParentDirectories(CONFIG_FILE);
        JSONObject configRoot = new JSONObject();
        configRoot.put("default", "");
        configRoot.put("suggestions", new JSONArray());
        configRoot.put("lang", "ru");
        CONFIG_FILE.createNewFile();
        FileUtils.write(CONFIG_FILE, configRoot.toString(), StandardCharsets.UTF_8);
    }

    private boolean isPrepared() {
        return CONFIG_FILE.exists();
    }

}
