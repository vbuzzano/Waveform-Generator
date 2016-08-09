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
        
        imageFile = new File("testfiles/mono_8bit.png");
        generator.generate(new File("testfiles/mono_8bit.wav"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
        
        imageFile = new File("testfiles/mono_16bit.png");
        generator.generate(new File("testfiles/mono_16bit.wav"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

        imageFile = new File("testfiles/mono_32bit_float.png");
        generator.generate(new File("testfiles/mono_32bit_float.wav"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

        imageFile = new File("testfiles/stereo_16bit.png");
        generator.generate(new File("testfiles/stereo_16bit.wav"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

    }

    public void testGenerateForMP3File() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        
        imageFile = new File("testfiles/mp320.png");
        generator.generate(new File("testfiles/mp320.mp3"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
        
        imageFile = new File("testfiles/mp192.png");
        generator.generate(new File("testfiles/mp192.mp3"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

        imageFile = new File("testfiles/mp128_average_stereo.png");
        generator.generate(new File("testfiles/mp128_average_stereo.mp3"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
    }

    public void testGenerateForOggFile() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        
        imageFile = new File("testfiles/sound.ogg.png");
        generator.generate(new File("testfiles/sound.ogg"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
    }

    public void testGenerateForFlacFile() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        
        imageFile = new File("testfiles/flac16bit.png");
        generator.generate(new File("testfiles/flac16bit.flac"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

//        imageFile = new File("flac24bit.png");
//        generator.generate(new File("flac24bit.flac"), new WaveformOption(), imageFile);
//        assertTrue(imageFile.exists());
    }

    public void testGenerateForAacFile() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        
        imageFile = new File("testfiles/sound.m4a.png");
        generator.generate(new File("testfiles/sound.m4a"), new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
    }
}
