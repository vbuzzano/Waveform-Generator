package org.electronism.sample.ogg;

import org.electronism.sample.AudioProperties;

/**
 * @author nw
 */
public class OggProperties extends AudioProperties {

  private static final String OGG_SUFFIX = "ogg";

  private static final String OGG_MAGIC_NUMBER = "4F 67 67 53";

  @Override
  protected String[] getFileExtensions() {
    return new String[] { OGG_SUFFIX };
  }

  @Override
  protected String[] getMagicNumberHexStrings() {
    return new String[] { OGG_MAGIC_NUMBER };
  }
}