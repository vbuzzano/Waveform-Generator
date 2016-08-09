package org.electronism.sample.flac;

import org.electronism.sample.AudioProperties;


/**
 * @author nw
 */
public class FlacProperties extends AudioProperties {

  private final static String FLAC_SUFFIX = "flac";

  private final static String FLAC_MAGIC_NUMBER = "66 4C 61 43";

  @Override
  protected String[] getFileExtensions() {
    return new String[] { FLAC_SUFFIX };
  }

  @Override
  protected String[] getMagicNumberHexStrings() {
    return new String[] { FLAC_MAGIC_NUMBER };
  }
}