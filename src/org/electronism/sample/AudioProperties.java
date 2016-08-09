package org.electronism.sample;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author nw
 */
public abstract class AudioProperties {

  protected abstract String[] getFileExtensions();

  /**
   * Returns an array of hex strings representing the respective magic numbers of this media type. E.g. the mp3 file format has 'ID3' as magic number, which is
   * '49 44 33' as a hex string. The hex string may contain empty spaces for improved readability. Furthermore it may contain wildcards in the form of 'nn'
   * which denotes any hex item, e.g. '12 34 nn 56' matches '12 34 11 56' as well as '12 34 22 56'.
   */
  protected abstract String[] getMagicNumberHexStrings();

  public boolean isOfThisType(File file) {
    for (String magicNumber : getMagicNumberHexStrings()) {
      if (fileStartsWith(file, magicNumber)) {
        return true;
      }
    }

    String lowerCaseName = file.getName().toLowerCase();
    for (String extension : getFileExtensions()) {
      if (lowerCaseName.endsWith(extension)) {
        return true;
      }
    }

    return false;
  }

  public boolean fileStartsWith(File file, String magicByteHexString) {
    byte[] magicNumberBuffer = new byte[hexStringToByteArray(magicByteHexString).length];

    try {
      FileInputStream fileInputStream = new FileInputStream(file);
      fileInputStream.read(magicNumberBuffer);
      fileInputStream.close();
    }
    catch (Exception e) {
      return false;
    }
    return compareByteArrayWithHexString(magicNumberBuffer, magicByteHexString);
  }

  private boolean compareByteArrayWithHexString(byte[] byteArray, String hexString) {
    String normalizedHexString = hexString.replaceAll(" ", "");

    for (int i = 0; i < byteArray.length; i++) {
      byte b = byteArray[i];
      String hexItem = normalizedHexString.substring(i * 2, i * 2 + 2);

      if (!hexItem.equals("nn") && b != hexStringToByteArray(hexItem)[0]) {
        return false;
      }
    }

    return true;
  }

  /**
   * Taken from http://stackoverflow.com/questions/11208479/how-do-i-initialize-a-byte-array-in-java/11208685#11208685
   */
  private byte[] hexStringToByteArray(String s) {
    s = s.replaceAll(" ", "");
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }

}