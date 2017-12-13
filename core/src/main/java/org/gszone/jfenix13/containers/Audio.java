package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.Main;

import static org.gszone.jfenix13.general.FileNames.*;

/**
 * Gestiona el audio
 *
 * Los sonidos son cargados al iniciar el juego
 * La música se va cargando mientras se reproduce
 *
 * music: música actual
 * currentMusic: número de música actual
 * musicVolume: volumen general de la música
 * soundVolume: volumen general de los sonidos
 */
public class Audio {
    private Music music;
    private int currentMusic;
    private float musicVolume;
    private float soundVolume;

    public Audio() {
        currentMusic = -1;
        musicVolume = 1.0f;
        soundVolume = 1.0f;
    }

    public void playSound(int num) {
        playSound("" + num, false);
    }

    /**
     * Reproduce un sonido y devuelve su ID.
     */
    public long playSound(String name, boolean loop) {
        FileHandle fh = Gdx.files.internal(getSoundDir(name));
        if (!fh.exists()) return -1;

        Sound sound = Gdx.audio.newSound(fh);
        if (loop) return sound.loop(soundVolume);
        return sound.play(soundVolume);
    }

    /**
     * Detiene un sonido
     */
    public void stopSound(String name, long id) {
        Sound sound = Main.getInstance().getAssets().getGDXAssets().get(getSoundDir(name), Sound.class);
        sound.stop(id);
    }

    /**
     * Reproduce una música
     */
    public void playMusic(int num) {
        if (currentMusic == num) return;
        if (music != null) music.dispose();

        FileHandle fh = Gdx.files.internal(getMusicDir(num));
        if (!fh.exists()) return;

        music = Gdx.audio.newMusic(fh);
        music.setLooping(true);
        music.setVolume(musicVolume);
        music.play();
        currentMusic = num;
    }

    public float getMusicVolume() { return musicVolume; }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
        if (music != null) {
            music.setVolume(musicVolume);
        }
    }

    public float getSoundVolume() { return soundVolume; }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }

    public void dispose() {
        if (music != null) music.dispose();
    }

}
