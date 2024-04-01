package me.yanjobs.mmutils.utils.config;

import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    public String defaultConfig = "enabled=true";

    public String getLunarClientFolder() {
        if (System.getProperty("os.version").contains("Windows")) {
            return "\\.lunarclient\\";
        } else {
            return "/.lunarclient/";
        }
    }

    private final Path configPath = new File(System.getProperty("user.home") + getLunarClientFolder() + "config-mm-utils.properties").toPath();
    public void createConfigFile() throws IOException {
        if (Files.notExists(configPath)) {
            Files.createFile(configPath);
            Files.write(configPath, defaultConfig.getBytes());
        }
    }
    public Properties getProperties() throws IOException {
        createConfigFile();
        FileInputStream configInput = new FileInputStream(configPath.toFile());
        Properties config = new Properties();
        config.load(configInput);
        return config;
    }

    public String getProperty(String key) throws IOException {
        try (FileInputStream configInput = new FileInputStream(configPath.toFile())) {
            Properties prop = new Properties();
            prop.load(configInput);
            configInput.close();
            return prop.getProperty(key);
        }
    }

    public void setProperty(@NotNull String key, @NotNull String value) {
        try (FileInputStream configInput = new FileInputStream(configPath.toFile())) {
            Properties prop = new Properties();
            prop.load(configInput);
            prop.setProperty(key, value);
            try (OutputStream output = new FileOutputStream(configPath.toFile())) {
                prop.store(output, null);
                configInput.close();
                output.close();
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}