package org.electronism.sample;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
 
public interface AudioStandardizer { 
 
  public static final float SAMPLE_RATE = 44100.0F; 
  public static final AudioFormat.Encoding TARGET_ENCODING = AudioFormat.Encoding.PCM_SIGNED; 
  public static final AudioFormat TARGET_AUDIO_FORMAT = new AudioFormat(TARGET_ENCODING, SAMPLE_RATE, 16, 2, 4, SAMPLE_RATE, false); 
 
  AudioInputStream standardize(AudioInputStream audioInputStream); 
}