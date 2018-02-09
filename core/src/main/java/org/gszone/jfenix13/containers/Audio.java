package org.gszone.jfenix13.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.general.Config;

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
    private boolean muteMusic;
    private boolean muteSound;

    public Audio() {
        Config c = Main.getInstance().getConfig();
        currentMusic = -1;
        muteMusic = !c.isMusicActive();
        muteSound = !c.isSoundActive();
        musicVolume = c.getMusicVol();
        soundVolume = c.getSoundVol();
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

        if (muteSound) return -1;
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

        if (currentMusic == num && music != null) return;

        currentMusic = num;

        if (muteMusic) return;

        if (music != null) music.dispose();

        FileHandle fh = Gdx.files.internal(getMusicDir(num));
        if (!fh.exists()) return;

        music = Gdx.audio.newMusic(fh);
        music.setLooping(true);
        music.setVolume(musicVolume);
        music.play();

    }

    public void stopMusic() {
        // Para nuestro caso, no la para, la destruye directamente..
        if (music != null) music.dispose();
        music = null;
    }

    public Music getMusic() {
        return music;
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

    public boolean isMuteMusic() {
        return muteMusic;
    }

    public void setMuteMusic(boolean muteMusic) {
        this.muteMusic = muteMusic;
        if (muteMusic)
            stopMusic();
        else
            playMusic(currentMusic);
    }

    public boolean isMuteSound() {
        return muteSound;
    }

    public void setMuteSound(boolean muteSound) {
        this.muteSound = muteSound;
    }

    public void dispose() {
        if (music != null) music.dispose();
    }

}
