package model;

/**
 * Interface that represents the operations offered by any transformation.
 */
public interface ITransformation {

  /**
   * Applies the transformation to the given pixel.
   *
   * @param pixel the given pixel.
   * @return the pixel after the transformation has been applied.
   */
  IPixel transformPixel(IPixel pixel);
}
