package model;

/**
 * Interface that represents the operations offered by the filter that is applied to a pixel.
 */
public class Filter implements IFilter {

  private final int size;
  private final double[][] kernel;

  /**
   * Constructor that initializes the filter with the given kernel.
   *
   * @param kernel the given kernel.
   * @throws IllegalArgumentException if the filter is not an odd sized square.
   */
  public Filter(double[][] kernel) throws IllegalArgumentException {
    if (kernel.length % 2 == 0 || kernel[0].length % 2 == 0) {
      throw new IllegalArgumentException("Kernels must have an odd size!");
    }

    if (kernel.length != kernel[0].length) {
      throw new IllegalArgumentException("Kernels must be square!");
    }

    this.size = kernel.length;
    this.kernel = kernel;
  }

  /**
   * Gets the size of the filter.
   *
   * @return the size.
   */
  @Override
  public int getSize() {
    return this.size;
  }

  /**
   * Gets the value of the pixel after the filter has been applied to it.
   *
   * @param pixels the pixels to which the filter has to applied.
   * @param maxValue the maxValue that any pixel can be.
   * @return the value of the pixel after the filter has been applied to it.
   */
  @Override
  public int filteredValue(int[][] pixels, int maxValue) throws IllegalArgumentException {

    if (!(pixels.length == this.kernel.length && pixels[0].length == this.kernel[0].length)) {
      throw new IllegalArgumentException("Values array provided is not the same size as the " +
              "kernel!");
    }

    double value = 0;
    for (int i = 0; i < this.size; i++) {
      for (int j = 0; j < this.size; j++) {
        value += this.kernel[i][j] * (double) pixels[i][j];
      }
    }
    if (value <= 0) {
      return 0;
    } else if (value > maxValue) {
      return maxValue;
    } else {
      return (int) value;
    }
  }
}
