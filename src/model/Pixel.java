package model;

import java.util.Objects;

/**
 * Class that implements the operations offered by a pixel of an image.
 * A Pixel has three channels: red, green, and blue.
 * The default maxValue is 255.
 */
public class Pixel implements IPixel {

  private int maxValue;
  private int red;
  private int green;
  private int blue;
  private int bits;
  private static double RED_LUMA_FACTOR = 0.2126;
  private static double GREEN_LUMA_FACTOR = 0.7152;
  private static double BLUE_LUMA_FACTOR = 0.0722;

  /**
   * Constructor for initialising a Pixel given the red, green, and blue values.
   * @param red channel value
   * @param green channel value
   * @param blue channel value
   * @throws IllegalArgumentException if the channel values are invalid
   */
  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    this(8, red, green, blue);
  }

  /**
   * Constructor for initialising a grey pixel (all channels are the same value).
   * @param grey value of all channels
   * @throws IllegalArgumentException if the value is invalid
   */
  public Pixel(int grey) throws IllegalArgumentException {
    this(8, grey, grey, grey);
  }

  /**
   * Constructor for initialising a grey pixel (all channels are the same value) given the
   * number of bits.
   * @param bits per channel
   * @param grey value of all channels
   * @throws IllegalArgumentException if the number of bits is not positive or the value is invalid
   */
  public Pixel(int bits, int grey) throws IllegalArgumentException {
    this(bits, grey, grey, grey);
  }

  /**
   * Constructor for initialising a Pixel given the bits per channel, red, green, and blue values.
   * @param bits per channel
   * @param red channel value
   * @param green channel value
   * @param blue channel value
   * @throws IllegalArgumentException if the number of bits is not positive or if the channel values
   *                                  are invalid.
   */
  public Pixel(int bits, int red, int green, int blue) throws IllegalArgumentException {

    if (bits <= 0) {
      throw new IllegalArgumentException("Invalid number of bits provided!");
    }

    this.maxValue = (int) (Math.pow(2, bits)) - 1;

    if (this.invalidValue(red) || this.invalidValue(green) || this.invalidValue(blue)) {
      throw new IllegalArgumentException("One of the values provided was invalid!");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.bits = bits;
  }

  /**
   * Checks if the value is invalid. A value is invalid if it is less than 0 or greater than
   * the max value.
   * @param value to check
   * @return true if the value is invalid
   */
  private boolean invalidValue(int value) {
    return (value < 0) || (value > this.maxValue);
  }

  /**
   * Gets the integer value of the given channel.
   * @param channel to get the value of
   * @return the value of the given channel
   * @throws IllegalArgumentException if the channel doesn't exist
   */
  @Override
  public int getChannelValue(String channel) throws IllegalArgumentException {

    if (channel.equals("red")) {
      return this.red;
    }

    if (channel.equals("green")) {
      return this.green;
    }

    if (channel.equals("blue")) {
      return this.blue;
    }

    throw new IllegalArgumentException("Invalid channel!");
  }

  /**
   * Gets the maximum integer value of the channels of the pixel.
   * @return the maximum integer value
   */
  @Override
  public int getValue() {
    return Math.max(this.red, Math.max(this.green, this.blue));
  }

  /**
   * Gets the average value (intensity) of the channels of the pixel.
   * @return the average integer value of the channels of the pixel
   */
  @Override
  public int getIntensity() {
    return (this.red + this.green + this.blue) / 3;
  }

  /**
   * Gets the luma value of the pixel.
   * @return the luma value of the pixel
   */
  @Override
  public int getLuma() {
    double luma = this.red * this.RED_LUMA_FACTOR + this.green * this.GREEN_LUMA_FACTOR +
            this.blue * this.BLUE_LUMA_FACTOR;
    return (int) luma;
  }

  /**
   * Brightens the image by the given increment.
   * @param increment the integer value to increment the brightness by
   * @throws IllegalArgumentException if the increment is invalid
   */
  @Override
  public IPixel setBrightness(int increment) {
    int red = this.getNewValue(this.red, increment);
    int green = this.getNewValue(this.green, increment);
    int blue = this.getNewValue(this.blue, increment);
    return new Pixel(this.bits, red, green, blue);
  }

  /**
   * Gets the max value of the pixel.
   * @return the max value of the pixel.
   */
  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

  private int getNewValue(int oldValue, int increment) {
    int newValue = oldValue + increment;
    if (newValue < 0) {
      return 0;
    } else if (newValue > this.maxValue) {
      return 255;
    } else {
      return newValue;
    }
  }

  /**
   * Checks if the given object is the same type as this class.
   *
   * @param o the given object.
   * @return true if the given object is of type Pixel.
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Pixel)) {
      return false;
    }

    Pixel pixel = (Pixel) o;
    return pixel.red == this.red
            && pixel.green == this.green
            && pixel.blue == this.blue
            && pixel.maxValue == this.maxValue;
  }

  /**
   * Overrides the hashCode for the class Pixel.
   *
   * @return the hashCode of an object of the class Pixel.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue, this.maxValue);
  }
}
