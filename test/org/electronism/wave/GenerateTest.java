package org.electronism.wave;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.UnsupportedAudioFileException;

import junit.framework.TestCase;

import org.electronism.sample.Generator;
import org.electronism.sample.WaveformOption;


public class GenerateTest extends TestCase {

    public void testGenerateForWaveFile() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        File soundFile = null;
        AudioFormat format = null;

        imageFile = new File("testfiles/mono_8bit.png");
        soundFile = new File("testfiles/mono_8bit.wav");
        format = generator.getFormat(soundFile);
        System.out.println(format);
        System.out.println(format.properties());

        generator.generate(soundFile, new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
        
        imageFile = new File("testfiles/mono_16bit.png");
        soundFile = new File("testfiles/mono_16bit.wav");
        format = generator.getFormat(soundFile);
        System.out.println(format);
        System.out.println(format.properties());
        generator.generate(soundFile, new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

        imageFile = new File("testfiles/mono_32bit_float.png");
        soundFile = new File("testfiles/mono_32bit_float.wav");
        format = generator.getFormat(soundFile);
        System.out.println(format);
        System.out.println(format.properties());
        generator.generate(soundFile, new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

        imageFile = new File("testfiles/stereo_16bit.png");
        soundFile = new File("testfiles/stereo_16bit.wav");
        format = generator.getFormat(soundFile);
        System.out.println(format);
        System.out.println(format.properties());
        generator.generate(soundFile, new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

    }

    public void testGenerateForMP3File() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        File soundFile = null;
        AudioFormat format = null;

        imageFile = new File("testfiles/mp320.png");
        soundFile = new File("testfiles/mp320.mp3");
        format = generator.getFormat(soundFile);
        System.out.println(format);
        System.out.println(format.properties());
        generator.generate(soundFile, new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
        
        imageFile = new File("testfiles/mp192.png");
        soundFile = new File("testfiles/mp192.mp3");
        format = generator.getFormat(soundFile);
        System.out.println(format);
        System.out.println(format.properties());
        generator.generate(soundFile, new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

        imageFile = new File("testfiles/mp128_average_stereo.png");
        soundFile = new File("testfiles/mp128_average_stereo.mp3");
        format = generator.getFormat(soundFile);
        System.out.println(format);
        System.out.println(format.properties());
        generator.generate(soundFile, new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
    }

    public void testGenerateForOggFile() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        File soundFile = null;
        AudioFormat format = null;

        imageFile = new File("testfiles/sound.ogg.png");
        soundFile = new File("testfiles/sound.ogg");
        format = generator.getFormat(soundFile);
        System.out.println(format);
        System.out.println(format.properties());

        generator.generate(soundFile, new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
    }

    public void testGenerateForFlacFile() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        File soundFile = null;
        AudioFormat format = null;
        
        imageFile = new File("testfiles/flac16bit.png");
        soundFile = new File("testfiles/flac16bit.flac");
        format = generator.getFormat(soundFile);
        System.out.println(format);
        System.out.println(format.properties());

        generator.generate(soundFile, new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());

//        imageFile = new File("flac24bit.png");
//        generator.generate(new File("flac24bit.flac"), new WaveformOption(), imageFile);
//        assertTrue(imageFile.exists());
    }

    public void testGenerateForAacFile() throws UnsupportedAudioFileException, IOException {
        Generator generator = new Generator();
        File imageFile = null;
        File soundFile = null;
        AudioFormat format = null;
        
        imageFile = new File("testfiles/sound.m4a.png");
        soundFile = new File("testfiles/sound.m4a");
        format = generator.getFormat(soundFile);
        System.out.println(format);
        System.out.println(format.properties());

        generator.generate(soundFile, new WaveformOption(), imageFile);
        assertTrue(imageFile.exists());
    }
}
