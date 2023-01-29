package model;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Interface that represents the operations offered by an image.
 */
public interface Image {

  /**
   * Brighten the image by the given increment.
   *
   * @param increment the integer value to brighten the image by
   * @throws IllegalArgumentException if the increment value is invalid
   */
  Image brightenImage(int increment);

  /**
   * Flips the image vertically.
   */
  Image flipVertical();

  /**
   * Flips the image horizontally.
   */
  Image flipHorizontal();

  /**
   * Converts the image to grey scale using the component method given.
   *
   * @param method the component to use when converting to greyscale image
   * @throws IllegalArgumentException if the method is invalid
   */
  Image convertGreyscale(String method) throws IllegalArgumentException;

  /**
   * Writes the pixels of the image into a list of int[] where each int[] contains n values,
   * one for each channel. The size of the return list is width * height and the size of each inner
   * list is n.
   *
   * @return the list of pixels with their channel values as an array of integers.
   */
  IPixel[][] writePixels();

  /**
   * Gets the width of the image.
   *
   * @return the width of the image
   */
  int getWidth();

  /**
   * Gets the height of the image.
   *
   * @return the height of the image
   */
  int getHeight();

  /**
   * Applies the given filter to every pixel in the image.
   *
   * @param filter the given filter.
   * @return the image after the filter has been applied.
   */
  Image applyFilter(IFilter filter);

  /**
   * Applies the given matrix to every pixel in the image.
   *
   * @param transformation the given matrix.
   * @return the image after each pixel in the image has been transformed.
   */
  Image applyTransformation(ITransformation transformation);

  /**
   * Creates a buffered image of this image.
   *
   * @return the buffered image
   */
  BufferedImage createBufferedImage();

  /**
   * Gets the histogram of a given color of this image.
   *
   * @param component the given color
   * @return the histogram of the color
   * @throws IllegalArgumentException if the given color is invalid
   */
  Map<Integer, Integer> getHistogram(String component) throws IllegalArgumentException;
}