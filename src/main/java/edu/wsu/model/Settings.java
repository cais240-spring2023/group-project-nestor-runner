package edu.wsu.model;

import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Garden-variety Settings Singleton.
 */
public class Settings implements Serializable {

    private static Settings settingsInstance;

    private final Map<KeyCode, UserAction> bindings;

    private double musicVolPercent, sfxVolPercent;

    private Settings() {
        musicVolPercent = .5;
        sfxVolPercent = .5;
        bindings = new HashMap<>();

        bindings.put(KeyCode.SPACE, UserAction.JUMP);
        bindings.put(KeyCode.D, UserAction.FIRE);
        bindings.put(KeyCode.ESCAPE, UserAction.PAUSE);
    }

    public static Settings getInstance() {
        if (settingsInstance == null) {
            try (FileInputStream fis = new FileInputStream("settings.dat")) {
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
                settingsInstance = (Settings) ois.readObject();
                bis.close();
                ois.close();
            }
            catch (EOFException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "settings.dat not found; using default.").showAndWait();
                settingsInstance = new Settings();
            }
        }
        return settingsInstance;
    }

    public void writeToDat() {
        try (FileOutputStream fos = new FileOutputStream("settings.dat")) {
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(settingsInstance);
            bos.close();
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
        bindings.put(key, action);
    }

    public void removeBind(KeyCode key) {
        bindings.remove(key);
    }

    /**
     * returns the UserAction bound to a key, null if none.
     * @return the UserAction bound to @param key, null if none.
     */
    public UserAction getBoundAction(KeyCode key) {
        return bindings.get(key);
    }
}
