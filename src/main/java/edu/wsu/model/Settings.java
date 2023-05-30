package edu.wsu.model;

public class Settings {

    private static Settings settingsInstance;

    private double musicVolPercent;

    private double sfxVolPercent;

    private Settings() {
        musicVolPercent = 1.0;
        sfxVolPercent = 1.0;
    }

    public static Settings getInstance() {
        if (settingsInstance == null) {
            settingsInstance = new Settings();
        }
        return settingsInstance;
    }

    public double getMusicVolPercent() {
        return musicVolPercent;
    }

    public void setMusicVolPercent(double newMusicVolPercent) {
        musicVolPercent = newMusicVolPercent;
    }

    public double getSfxVolPercent() {
        return sfxVolPercent;
    }

    public void setSfxVolPercent(double newSfxVolPercent) {
        sfxVolPercent = newSfxVolPercent;
    }
}
