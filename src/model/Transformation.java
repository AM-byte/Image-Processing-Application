package model;

/**
 * A class for a transformation.
 */
public class Transformation implements ITransformation {

  private final double[][] matrix;

  /**
   * Constructor that initializes that matrix transformation.
   *
   * @param matrix the given matrix.
   * @throws IllegalArgumentException if the matrix is not a 3x3 matrix.
   */
  public Transformation(double[][] matrix) throws IllegalArgumentException {
    if (matrix.length != 3 || matrix[0].length != 3) {
      throw new IllegalArgumentException("Invalid matrix size!");
    }

    this.matrix = matrix;
  }

  /**
   * Applies the transformation to the given pixel.
   *
   * @param pixel the given pixel.
   * @return the pixel after the transformation has been applied.
   */
  @Override
  public IPixel transformPixel(IPixel pixel) {

    double red = pixel.getChannelValue("red") * this.matrix[0][0]
            + pixel.getChannelValue("green") * this.matrix[0][1]
            + pixel.getChannelValue("blue") * this.matrix[0][2];

    double green = pixel.getChannelValue("red") * this.matrix[1][0]
            + pixel.getChannelValue("green") * this.matrix[1][1]
            + pixel.getChannelValue("blue") * this.matrix[1][2];

    double blue = pixel.getChannelValue("red") * this.matrix[2][0]
            + pixel.getChannelValue("green") * this.matrix[2][1]
            + pixel.getChannelValue("blue") * this.matrix[2][2];

    if (red < 0) {
      red = 0;
    } else if (red > pixel.getMaxValue()) {
      red = pixel.getMaxValue();
    }

    if (green < 0) {
      green = 0;
    } else if (green > pixel.getMaxValue()) {
      green = pixel.getMaxValue();
    }

    if (blue < 0) {
      blue = 0;
    } else if (blue > pixel.getMaxValue()) {
      blue = pixel.getMaxValue();
    }

    return new Pixel((int) red, (int) green, (int) blue);
  }
}
