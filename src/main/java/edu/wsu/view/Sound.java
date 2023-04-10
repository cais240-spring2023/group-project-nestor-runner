package edu.wsu.view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {

    private MediaPlayer deathPlayer;
    private MediaPlayer jumpPlayer;
    private MediaPlayer collectPlayer;
    private MediaPlayer backGroundPlayer;
    private MediaPlayer doomPlayer;

    private static MediaPlayer generateMediaPlayer(String sound) {
        Media media = new Media(new File("src/main/resources/edu/wsu/sound/" + sound).toURI().toString());
        return new MediaPlayer(media);
    }

    public Sound() {
        deathPlayer = generateMediaPlayer("deathSound.wav");
        jumpPlayer = generateMediaPlayer("jumpSound.mp3");
        // collectPlayer = generateMediaPlayer("");
        backGroundPlayer = generateMediaPlayer("Fluffing-a-Duck.mp3");
        doomPlayer = generateMediaPlayer("Rip-and-Tear-Doom-OST.mp3");
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

    public void startBackGroundTrack() {
        backGroundPlayer.seek(backGroundPlayer.getStartTime());
        backGroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backGroundPlayer.setVolume(0.2);
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
        doomPlayer.setVolume(0.2);
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
