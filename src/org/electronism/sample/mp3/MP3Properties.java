package org.electronism.sample.mp3;

import org.electronism.sample.AudioProperties;

/**
 * @author nw
 */
public class MP3Properties extends AudioProperties {

  private static final String MP3_SUFFIX = "mp3";

  private static final String MP3_MAGIC_NUMBER = "FF FA";
  private static final String MP3_ALT_MAGIC_NUMBER = "FF FB";
  private static final String MP3_ALT2_MAGIC_NUMBER = "FF F3";
  private static final String ID3_MAGIC_NUMBER = "49 44 33";

  @Override
  protected String[] getFileExtensions() {
    return new String[] { MP3_SUFFIX };
  }

  @Override
  protected String[] getMagicNumberHexStrings() {
    return new String[] { MP3_MAGIC_NUMBER, ID3_MAGIC_NUMBER, MP3_ALT_MAGIC_NUMBER, MP3_ALT2_MAGIC_NUMBER };
  }

}