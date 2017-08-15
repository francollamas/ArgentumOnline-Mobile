package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.general.Main;

import static org.gszone.jfenix13.general.FileNames.*;


/**
 * Gestiona el audio
 *
 * Los sonidos son cargados al iniciar el juego
 * La música se va cargando mientras se reproduce
 */
public class Audio {
    private Music music;
    private float musicVolume;
    private float soundVolume;

    /**
     * Devuelve la lista de sonidos (el Path de cada uno)
     */
    public static String[] getSoundDirs() {
        FileHandle[] files = Gdx.files.internal(DIR_SOUNDS).list();

        String[] names = new String[files.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = files[i].path();
        }
        return names;
    }

    public Audio() {
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
        Sound sound = Main.getInstance().getAssets().getGDXAssets().get(getSoundDir(name), Sound.class);
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



    public void playMusic(int num) {
        if (music != null) music.dispose();

        FileHandle fh = Gdx.files.internal(getMusicDir(num));
        if (!fh.exists()) return;

        music = Gdx.audio.newMusic(fh);
        music.setLooping(true);
        music.setVolume(musicVolume);
        music.play(); //TODO: descomentar esta línea
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
