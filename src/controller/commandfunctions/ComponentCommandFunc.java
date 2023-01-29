package controller.commandfunctions;

import controller.command.Command;
import controller.command.ComponentCommand;

/**
 * Function class for the component command.
 */
public class ComponentCommandFunc extends TwoArgCommandFunc {

  private String method;

  /**
   * Constructor to initialize the method of greyscale for the image.
   *
   * @param method the method of greyscale.
   */
  public ComponentCommandFunc(String method) {
    super();
    this.method = method;
  }

  /**
   * Gets the error message for invalid arguments.
   *
   * @return the error message.
   */
  @Override
  protected String getCommandErrorMsg() {
    return this.method + "-component command expected image-name and dest-image-name as " +
            "the second and third arguments but didn't receive them!";
  }

  /**
   * Creates the appropriate command.
   *
   * @param userInput1 first argument of the command.
   * @param userInput2 second argument of the command.
   * @return the command.
   */
  @Override
  protected Command createCommand(String userInput1, String userInput2) {
    return new ComponentCommand(this.method, userInput1, userInput2);
  }

}
