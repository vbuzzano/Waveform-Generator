package org.electronism.sample.wave;

import org.electronism.sample.AudioProperties;


/**
 * @author nw
 */
public class WaveProperties extends AudioProperties {

  private final static String WAVE_SUFFIX = "wav";

  private final static String WAVE_MAGIC_NUMBER = "52 49 46 46 nn nn nn nn 57 41 56 45";

  @Override
  protected String[] getFileExtensions() {
    return new String[] { WAVE_SUFFIX };
  }

  @Override
  protected String[] getMagicNumberHexStrings() {
    return new String[] { WAVE_MAGIC_NUMBER };
  }

}