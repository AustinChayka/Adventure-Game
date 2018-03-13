import java.io.*;
import javax.sound.sampled.*;

public class SoundClip {
    
    private Clip clip = null;
    private FloatControl gainControl;
    
    public SoundClip(String path) {
        
        try {
            
            InputStream audioSrc = SoundClip.class.getResourceAsStream(path);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(), 16, baseFormat.getChannels(), 
                baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodedFormat, ais);
            
            clip = AudioSystem.getClip();
            clip.open(dais);
            
            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            
        } catch(Exception e) { 
            e.printStackTrace();
        }
        
    }
    
    public void play() {
        
        if(clip == null) {
            return;
        }
            
        stop();
        clip.setFramePosition(0);
        while(!clip.isRunning()) {
            
            clip.start();
            
        }
        
    }
    
    public void stop() {
        
        if(clip.isRunning()) {
            clip.stop();
        }
        
    }
    
    public void close() {
        
        stop();
        clip.drain();
        clip.close();
        
    }
    
    public void loop() {
        
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        play();
        
    }
    
    public void setVolume(float v) {
        
        gainControl.setValue(v);
        
    }
    
    public boolean isRunning() {
        
        return clip.isRunning();
        
    }
    
}