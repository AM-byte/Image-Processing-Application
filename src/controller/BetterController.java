package controller;

/**
 * This interface extends the operations offered by the pro controller.
 */
public interface BetterController extends IController {

  /**
   * Sets this readable to the given readable.
   *
   * @param in the given readable
   */
  void resetReadable(Readable in);
}
