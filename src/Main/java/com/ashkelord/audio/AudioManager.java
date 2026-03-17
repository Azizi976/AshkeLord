package com.ashkelord.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton AudioManager using javax.sound.sampled.
 * Handles looping BGM (one at a time) and fire-and-forget SFX.
 * Gracefully no-ops if audio files are missing.
 */
public class AudioManager {

    private static AudioManager instance;

    private Map<String, String> soundRegistry; // id → classpath resource
    private Clip currentMusic;
    private String currentMusicId;
    private float musicVolume = 0.8f;
    private float sfxVolume = 1.0f;

    private AudioManager() {
        soundRegistry = new HashMap<>();
    }

    public static synchronized AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    /** Register a sound with an ID and classpath resource path. */
    public void registerSound(String id, String resourcePath) {
        soundRegistry.put(id, resourcePath);
    }

    /** Play a one-shot SFX. Does nothing if the sound isn't registered or file missing. */
    public void playSound(String id) {
        String path = soundRegistry.get(id);
        if (path == null) return;

        try {
            URL url = getClass().getResource(path);
            if (url == null) return; // File not found — silent no-op

            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            // Set volume
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl vol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                vol.setValue(volumeToDecibels(sfxVolume));
            }

            // Auto-close when done
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            clip.start();
        } catch (Exception e) {
            // Silently fail — no audio is not a crash
            System.err.println("Audio error (SFX '" + id + "'): " + e.getMessage());
        }
    }

    /** Play looping background music. Stops any currently playing BGM. */
    public synchronized void playMusic(String id) {
        if (id.equals(currentMusicId)) return; // Already playing

        stopMusic();

        String path = soundRegistry.get(id);
        if (path == null) return;

        try {
            URL url = getClass().getResource(path);
            if (url == null) return;

            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            currentMusic = AudioSystem.getClip();
            currentMusic.open(ais);

            if (currentMusic.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl vol = (FloatControl) currentMusic.getControl(FloatControl.Type.MASTER_GAIN);
                vol.setValue(volumeToDecibels(musicVolume));
            }

            currentMusic.loop(Clip.LOOP_CONTINUOUSLY);
            currentMusicId = id;
        } catch (Exception e) {
            System.err.println("Audio error (BGM '" + id + "'): " + e.getMessage());
        }
    }

    /** Stop the current background music. */
    public synchronized void stopMusic() {
        if (currentMusic != null && currentMusic.isRunning()) {
            currentMusic.stop();
            currentMusic.close();
        }
        currentMusic = null;
        currentMusicId = null;
    }

    /** Stop all audio. */
    public synchronized void stopAll() {
        stopMusic();
        // SFX clips auto-close on completion, no tracking needed
    }

    public void setMusicVolume(float vol) {
        this.musicVolume = Math.max(0f, Math.min(1f, vol));
        if (currentMusic != null && currentMusic.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl fc = (FloatControl) currentMusic.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(volumeToDecibels(musicVolume));
        }
    }

    public void setSFXVolume(float vol) {
        this.sfxVolume = Math.max(0f, Math.min(1f, vol));
    }

    /** Convert 0.0–1.0 linear volume to decibel scale. */
    private float volumeToDecibels(float volume) {
        if (volume <= 0f) return -80f; // Effectively mute
        return 20f * (float) Math.log10(volume);
    }
}
