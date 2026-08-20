package me.yanjobs.mmutils.utils.config;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    public String defaultConfig = "enabled=true";

    private final Path configPath = Minecraft.getMinecraft().mcDataDir.toPath()
            .resolve("config")
            .resolve("mm-utils")
            .resolve("config.properties");

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
        try (InputStream configInput = Files.newInputStream(configPath)) {
            Properties prop = new Properties();
            prop.load(configInput);
            prop.setProperty(key, value);

            try (OutputStream output = Files.newOutputStream(configPath.toFile().toPath())) {
                prop.store(output, null);
                configInput.close();
            }
        } catch (IOException io) {
            throw new RuntimeException("Could not save config file", io);
        }
    }
}