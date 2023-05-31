package edu.wsu.model;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Garden-variety Settings Singleton.
 */
public class Settings {

    private static Settings settingsInstance;

    private double musicVolPercent;

    private double sfxVolPercent;

    private Map<KeyCode, UserAction> keyToAction;

    private Settings() {
        musicVolPercent = 1.0;
        sfxVolPercent = 1.0;
        keyToAction = new HashMap<>();

        keyToAction.put(KeyCode.SPACE, UserAction.JUMP);
        keyToAction.put(KeyCode.D, UserAction.FIRE);
        keyToAction.put(KeyCode.ESCAPE, UserAction.PAUSE);
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

    public void addBind(KeyCode key, UserAction action) {
        keyToAction.put(key, action);
    }

    public void removeBind(KeyCode key) {
        keyToAction.remove(key);
    }

    public UserAction getBoundAction(KeyCode key) {
        return keyToAction.get(key);
    }
}
