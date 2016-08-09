package org.electronism.wave;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.electronism.sample.Generator;
import org.electronism.sample.WaveformOption;

import junit.framework.TestCase;


public class GenerateTest extends TestCase {

    public void testGenerateForWaveFile() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        
        imageFile = new File("mono_8bit.png");
        generator.generate(new File("mono_8bit.wav"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
        
        imageFile = new File("mono_16bit.png");
        generator.generate(new File("mono_16bit.wav"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

        imageFile = new File("mono_32bit_float.png");
        generator.generate(new File("mono_32bit_float.wav"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

        imageFile = new File("stereo_16bit.png");
        generator.generate(new File("stereo_16bit.wav"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

    }

    public void testGenerateForMP3File() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        
        imageFile = new File("mp320.png");
        generator.generate(new File("mp320.mp3"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
        
        imageFile = new File("mp192.png");
        generator.generate(new File("mp192.mp3"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

        imageFile = new File("mp128_average_stereo.png");
        generator.generate(new File("mp128_average_stereo.mp3"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
    }

    public void testGenerateForOggFile() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        
        imageFile = new File("sound.ogg.png");
        generator.generate(new File("sound.ogg"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
    }

    public void testGenerateForFlacFile() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        
        imageFile = new File("flac16bit.png");
        generator.generate(new File("flac16bit.flac"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

//        imageFile = new File("flac24bit.png");
//        generator.generate(new File("flac24bit.flac"), new WaveformOption(), imageFile);
//        assertTrue(imageFile.exists());
    }

    public void testGenerateForAacFile() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        
        imageFile = new File("sound.m4a.png");
        generator.generate(new File("sound.m4a"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
    }
}
