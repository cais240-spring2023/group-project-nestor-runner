package edu.wsu.view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class Sound {

    private final MediaPlayer deathPlayer;
    private final MediaPlayer jumpPlayer;
    private MediaPlayer collectPlayer;
    private MediaPlayer explosionPlayer;
    private final MediaPlayer backGroundPlayer;
    private final MediaPlayer doomPlayer;

    // used by gameView to decide what player to play when unPausing.
    public boolean doomIsActive;

    private MediaPlayer generateMediaPlayer(String sound) {
        String soundFileURL = "/edu/wsu/sound/" + sound;
        Media media = new Media(
                Objects.requireNonNull(getClass().getResource(soundFileURL)).toString());
        return new MediaPlayer(media);
    }

    public Sound(double musicVolume, double sfxVolume) {
        doomIsActive = true;

        deathPlayer = generateMediaPlayer("deathSound.wav");
        deathPlayer.setVolume(sfxVolume);

        jumpPlayer = generateMediaPlayer("jumpSound.mp3");
        jumpPlayer.setVolume(sfxVolume);

        // collectPlayer = generateMediaPlayer("collectSound.xyz");
        // collectPlayer.setVolume(sfxVolume);

        // explosionPlayer = generateMediaPlayer("explosionSound.xyz");
        // explosionPlayer.setVolume(sfxVolume);

        backGroundPlayer = generateMediaPlayer("Fluffing-a-Duck.mp3");
        backGroundPlayer.setVolume(musicVolume);

        doomPlayer = generateMediaPlayer("Rip-and-Tear-Doom-OST.mp3");
        doomPlayer.setVolume(musicVolume);
    }
    public void playDeathSound() {
        deathPlayer.seek(deathPlayer.getStartTime());
        deathPlayer.play();
    }

    public void playJumpSound() {
        jumpPlayer.seek(jumpPlayer.getStartTime());
        jumpPlayer.play();
    }

    public void playCollectSound() {
        collectPlayer.seek(collectPlayer.getStartTime());
        collectPlayer.play();
    }

    public void playExplosionSound() {
        explosionPlayer.seek(explosionPlayer.getStartTime());
        explosionPlayer.play();
    }

    public void startBackGroundTrack() {
        backGroundPlayer.seek(backGroundPlayer.getStartTime());
        backGroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backGroundPlayer.play();
    }

    public void pauseBackGroundTrack() {
        backGroundPlayer.pause();
    }

    public void resumeBackGroundTrack() {
        backGroundPlayer.play();
    }

    public void stopBackGroundTrack() {
        backGroundPlayer.stop();
    }

    public void startDoomSoundTrack() {
        doomPlayer.seek(doomPlayer.getStartTime());
        doomPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        doomPlayer.play();
    }

    public void pauseDoomSoundTrack() {
        doomPlayer.pause();
    }

    public void resumeDoomSoundTrack() {
        doomPlayer.play();
    }

    public void stopDoomSoundTrack() {
        doomPlayer.stop();
    }

    public boolean backGroundTrackIsPlaying() {
        return backGroundPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public boolean doomSoundTrackIsPlaying() {
        return doomPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
}
