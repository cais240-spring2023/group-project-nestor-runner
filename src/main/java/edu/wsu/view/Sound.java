package edu.wsu.view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {

    private MediaPlayer deathPlayer;
    private MediaPlayer jumpPlayer;
    private MediaPlayer collectPlayer;
    private MediaPlayer backgroundPlayer;

    private static MediaPlayer generateMediaPlayer(String sound) {
        Media media = new Media(new File("src/main/resources/edu/wsu/sound/" + sound).toURI().toString());
        return new MediaPlayer(media);
    }

    public Sound() {
        deathPlayer = generateMediaPlayer("deathSound.wav");
        jumpPlayer = generateMediaPlayer("jumpSound.mp3");
        // collectPlayer = generateMediaPlayer("");
        backgroundPlayer = generateMediaPlayer("Fluffing-a-Duck.mp3");
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
        backgroundPlayer.seek(backgroundPlayer.getStartTime());
        backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundPlayer.setVolume(0.2);
        backgroundPlayer.play();
    }

    public void pauseBackGroundTrack() {
        backgroundPlayer.pause();
    }

    public void resumeBackGroundTrack() {
        backgroundPlayer.play();
    }

    public void stopBackGroundTrack() {
        backgroundPlayer.stop();
    }
}
