package model;

/**
 * Interface that represents the operations offered by a pixel of an image.
 */
public interface IPixel {

  /**
   * Gets the integer value of the given channel.
   * @param channel to get the value of
   * @return the value of the given channel
   * @throws IllegalArgumentException if the channel doesn't exist
   */
  int getChannelValue(String channel) throws IllegalArgumentException;

  /**
   * Gets the maximum integer value of the channels of the pixel.
   * @return the maximum integer value
   */
  int getValue();

  /**
   * Gets the average value (intensity) of the channels of the pixel.
   * @return the average integer value of the channels of the pixel
   */
  int getIntensity();

  /**
   * Gets the luma value of the pixel.
   * @return the luma value of the pixel
   */
  int getLuma();

  /**
   * Brightens the image by the given increment.
   * @param increment the integer value to increment the brightness by
   * @throws IllegalArgumentException if the increment is invalid
   */
  IPixel setBrightness(int increment);

  /**
   * Gets the max value of the pixel.
   * @return the max value of the pixel.
   */
  int getMaxValue();
}