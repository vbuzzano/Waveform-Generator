package org.electronism.sample;
 
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
 
public interface AudioDecoder { 
 
  AudioInputStream decode(File inputFile) throws UnsupportedAudioFileException, IOException; 
  
  boolean isAbleToDecode(File file); 
}