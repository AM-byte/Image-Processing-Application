package controller;

import java.util.Map;

import model.Image;

/**
 * This class implements the operations offered by the better controller.
 */
public class BetterControllerImpl extends ProController implements BetterController {

  /**
   * Initializes the better controller with the given model and log.
   *
   * @param model the given model
   * @param log the given log
   * @throws IllegalArgumentException if the given model or log are null
   */
  public BetterControllerImpl(Map<String, Image> model, Appendable log)
          throws IllegalArgumentException {
    super(model, log);
  }

  /**
   * Sets this readable to the given readable.
   *
   * @param in the given readable
   */
  @Override
  public void resetReadable(Readable in) {
    this.in = in;
  }

}
