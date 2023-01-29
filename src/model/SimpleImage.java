package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class that implements the operations offered by an image.
 */
public class SimpleImage implements Image {
  private IPixel[][] pixels;
  private final int width;
  private final int height;
  private final int maxValue;

  /**
   * Constructor for initialising a SimpleImage.
   *
   * @param width of the image
   * @param height of the image
   * @param pixels that make up the image
   * @throws IllegalArgumentException if the width or height are invalid or if the pixels provided
   *                                  are null.
   */
  public SimpleImage(int width, int height, IPixel[][] pixels) throws IllegalArgumentException {

    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Invalid size of image!");
    }

    if (pixels == null) {
      throw new IllegalArgumentException("Colors provided were null!");
    }

    this.width = width;
    this.height = height;
    this.pixels = pixels;
    this.maxValue = pixels[0][0].getMaxValue();
  }

  /**
   * Brighten the image by the given increment.
   *
   * @param increment the integer value to brighten the image by
   * @throws IllegalArgumentException if the increment value is invalid
   */
  @Override
  public Image brightenImage(int increment) {
    IPixel[][] newPixels = new Pixel[this.width][this.height];


    for (int r = 0; r < this.width; r++) {
      for (int c = 0; c < this.height; c++) {
        newPixels[r][c] =  this.pixels[r][c].setBrightness(increment);
      }
    }

    return new SimpleImage(this.width, this.height, newPixels);
  }

  /**
   * Flips the image horizontally.
   */
  @Override
  public Image flipHorizontal() {

    IPixel[][] flippedImage = new IPixel[this.width][this.height];

    for (int r = 0; r < this.width; r++) {
      flippedImage[r] = this.pixels[this.width - 1 - r];
    }

    return new SimpleImage(this.width, this.height, flippedImage);
  }

  /**
   * Flips the image vertically.
   */
  @Override
  public Image flipVertical() {

    IPixel[][] flippedImage = new IPixel[this.width][this.height];

    for (int r = 0; r < this.width; r++) {
      for (int c = 0; c < this.height; c++) {
        flippedImage[r][c] = this.pixels[r][this.height - 1 - c];
      }
    }

    return new SimpleImage(this.width, this.height, flippedImage);

  }

  /**
   * Converts the image to grey scale using the component method given.
   *
   * @param method the component to use when converting to greyscale image
   * @throws IllegalArgumentException if the method is invalid
   */
  @Override
  public Image convertGreyscale(String method) throws IllegalArgumentException {

    IPixel[][] greyImage = new IPixel[this.width][this.height];

    for (int r = 0; r < this.width; r++) {
      for (int c = 0; c < this.height; c++) {
        int greyValue = this.getGreyValue(method, this.pixels[r][c]);
        greyImage[r][c] = new Pixel(greyValue);
      }
    }

    return new SimpleImage(this.width, this.height, greyImage);
  }

  /**
   * Writes the pixels of the image into a list of int[] where each int[] contains 3 values,
   * one for each channel. The size of the return list is width * height and the size of each inner
   * list is 3.
   *
   * @return the list of pixels with their rgb values as an array of integers.
   */
  @Override
  public IPixel[][] writePixels() {

    IPixel[][] pixelsList = new Pixel[this.width][this.height];

    for (int r = 0; r < this.width; r++) {
      for (int c = 0; c < this.height; c++) {
        int red = this.pixels[r][c].getChannelValue("red");
        int green = this.pixels[r][c].getChannelValue("green");
        int blue = this.pixels[r][c].getChannelValue("blue");
        pixelsList[r][c] = new Pixel(red, green, blue);
      }
    }

    return pixelsList;
  }

  /**
   * Gets the width of the image.
   *
   * @return the width of the image
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the height of the image.
   *
   * @return the height of the image
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Applies the given filter to every pixel in the image.
   *
   * @param filter the given filter.
   * @return the image after the filter has been applied.
   */
  @Override
  public Image applyFilter(IFilter filter) {

    IPixel[][] newPixels = new Pixel[this.width][this.height];

    int filterSize = filter.getSize();

    for (int r = 0; r < this.width; r++) {
      for (int c = 0; c < this.height; c++) {
        IPixel pixel = this.pixels[r][c];

        int red = this.applyFilterHelper("red", r, c, filterSize, filter, pixel);
        int green = this.applyFilterHelper("green", r, c, filterSize, filter, pixel);
        int blue = this.applyFilterHelper("blue", r, c, filterSize, filter, pixel);
        newPixels[r][c] = new Pixel(red, green, blue);
      }
    }

    return new SimpleImage(this.width, this.height, newPixels);
  }

  /**
   * Applies the given matrix to every pixel in the image.
   *
   * @param transformation the given matrix.
   * @return the image after each pixel in the image has been transformed.
   */
  @Override
  public Image applyTransformation(ITransformation transformation) {

    IPixel[][] newPixels = new Pixel[this.width][this.height];

    for (int r = 0; r < this.width; r++) {
      for (int c = 0; c < this.height; c++) {
        newPixels[r][c] = transformation.transformPixel(this.pixels[r][c]);
      }
    }
    return new SimpleImage(this.width, this.height, newPixels);
  }

  /**
   * Creates a buffered image of this image.
   *
   * @return the buffered image
   */
  @Override
  public BufferedImage createBufferedImage() {

    BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

    for (int r = 0; r < this.width; r++) {
      for (int c = 0; c < this.height; c++) {
        IPixel pixel = this.pixels[r][c];
        int red = pixel.getChannelValue("red");
        int green = pixel.getChannelValue("green");
        int blue = pixel.getChannelValue("blue");
        Color color = new Color(red, green, blue);
        img.setRGB(r, c, color.getRGB());
      }
    }

    return img;
  }

  /**
   * Gets the histogram of a given color of this image.
   *
   * @param component the given color
   * @return the histogram of the color
   * @throws IllegalArgumentException if the given color is invalid
   */
  @Override
  public Map<Integer, Integer> getHistogram(String component) throws IllegalArgumentException {
    Map<Integer, Integer> histogram = new HashMap<Integer, Integer>();
    for (int i = 0; i < 256; i++) {
      histogram.put(i, 0);
    }

    if (!(component.equals("red") || component.equals("green") || component.equals("blue") ||
            component.equals("intensity"))) {
      throw new IllegalArgumentException("Invalid component provided");
    }

    for (IPixel[] row : this.pixels) {
      for (IPixel pixel : row) {
        int value = this.getHistogramValue(component, pixel);
        histogram.put(value, histogram.get(value) + 1);
      }
    }

    return histogram;
  }

  /**
   * Gets the value of the given component for the given pixel.
   *
   * @param component the given component
   * @param pixel the given pixel
   * @return the value of the component
   */
  private int getHistogramValue(String component, IPixel pixel) {
    if (component.equals("intensity")) {
      return this.getGreyValue(component, pixel);
    }

    return pixel.getChannelValue(component);
  }

  /**
   * Helper for the apply filter method.
   *
   * @param channel the channel value.
   * @param row the row of the pixel.
   * @param col the column of the pixel.
   * @param filterSize the size of the filter that is being applied.
   * @param filter the filter to be applied.
   * @param pixel the pixel that the filter is going to be applied to.
   * @return the value of the pixel after the filter has been applied to it.
   */
  private int applyFilterHelper(String channel, int row, int col, int filterSize, IFilter filter,
                                IPixel pixel) {

    int newRow = row - filterSize / 2;
    int newCol = col - filterSize / 2;

    int[][] correspondingPixels = new int[filterSize][filterSize];

    for (int i = 0; i < filterSize; i++) {
      for (int j = 0; j < filterSize; j++) {
        if (newRow < 0 || newRow >= this.width || newCol < 0 || newCol >= this.height) {
          correspondingPixels[i][j] = 0;
        } else {
          correspondingPixels[i][j] = this.pixels[newRow][newCol].getChannelValue(channel);
        }
        newCol++;
      }
      newRow++;
    }

    return filter.filteredValue(correspondingPixels, this.maxValue);
  }


  /**
   * Gets the grey value using the method given.
   *
   * @param method to use for the grey value
   * @param pixel of which to get the grey value
   * @return the grey value
   * @throws IllegalArgumentException if the method is invalid
   */
  private int getGreyValue(String method, IPixel pixel) throws IllegalArgumentException {

    if (method.equals("red") || method.equals("green") || method.equals("blue")) {
      return pixel.getChannelValue(method);
    }

    switch (method) {
      case "value":
        return pixel.getValue();
      case "intensity":
        return pixel.getIntensity();
      case "luma":
        return pixel.getLuma();
      default:
        throw new IllegalArgumentException("Invalid method provided!");
    }
  }

  /**
   * Compares any given object and checks if it's a type of this class.
   *
   * @param o the provided object.
   * @return true if the given object is of type SimpleImage.
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SimpleImage)) {
      return false;
    }

    SimpleImage image = (SimpleImage) o;
    boolean isEqual = true;
    for (int r = 0; r < this.width; r++) {
      for (int c = 0; c < this.height; c++) {
        isEqual = isEqual && image.pixels[r][c].equals(this.pixels[r][c]);
      }
    }

    return isEqual && image.width == this.width
            && image.height == this.height
            && image.maxValue == this.maxValue;
  }

  /**
   * Overrides hashCode for the class SimpleImage.
   *
   * @return the hashCode of the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.width, this.height, this.pixels, this.maxValue);
  }
}
