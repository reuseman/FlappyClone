package utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *   Gestisce la riproduzione di un suono in formato wave.
 *   La classe è un basata su "Sound di Oneiros".
 *   @author Alex
 */

public class Sound {
    private Clip clip;

    /**
     * Crea un nuovo suono a partire dal path assoluto di un file WAVE.
     *
     * @param path Il path assoluto di un file WAVE.
     * @throws URISyntaxException Se il path non risulta puntare ad un corretto file
     * WAVE
     */
    public Sound(String path) throws URISyntaxException {
        this(new URI(path));
    }

    /**
     * Crea un nuovo suono a partire dal path assoluto di un file WAVE.
     *
     * @param path Il path assoluto di un file WAVE.
     */
    public Sound(URI path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                // clip.open(sound);
            } else {
                throw new RuntimeException("SOUND: File Not Found: " + path);
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("SOUND: Malformed URL: " + ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("SOUND: I/O Error: " + ex);
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
            throw new RuntimeException("SOUND: Line Unavailable Error: " + ex);
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
            throw new RuntimeException("SOUND: Audio File Not Supported: " + ex);
        }
    }

    /**
     * Riproduce il suono una volta
     */
    public void play(){
        if (this.clip != null) {
            this.clip.setFramePosition(0);
            this.clip.start();
        }
    }

    /**
     * Riproduce il suono finchè non viene invocato il metodo stop()
     */
    public void loop(){
        if (this.clip != null)
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Riproduce n volte un suono
     *
     * @param times Il numero di riproduzioni da effettuare
     */
    public void loop(int times) {
        if (this.clip != null && times >= 1)
            this.clip.loop(times - 1);
    }

    /**
     * La riproduzione del suono viene interrotta
     */
    public void stop(){
        this.clip.stop();
    }
}
