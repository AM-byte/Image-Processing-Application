package model;

/**
 * Interface that represents the operations offered by the filter that is applied to a pixel.
 */
public interface IFilter {

  /**
   * Gets the size of the filter.
   *
   * @return the size.
   */
  int getSize();

  /**
   * Gets the value of the pixel after the filter has been applied to it.
   *
   * @param pixels the pixels to which the filter has to applied.
   * @param maxValue the maxValue that any pixel can be.
   * @return the value of the pixel after the filter has been applied to it.
   */
  int filteredValue(int[][] pixels, int maxValue) throws IllegalArgumentException;

}
