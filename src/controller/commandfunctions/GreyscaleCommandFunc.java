package controller.commandfunctions;

import controller.command.Command;
import controller.command.GreyscaleCommand;

/**
 * Function class for the greyscale command.
 */
public class GreyscaleCommandFunc extends TwoArgCommandFunc {

  /**
   * Gets the error message for invalid arguments.
   *
   * @return the error message.
   */
  @Override
  protected String getCommandErrorMsg() {
    return "greyscale command expected image-name, and dest-image-name as the arguments " +
            "but didn't receive them!";
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
    return new GreyscaleCommand(userInput1, userInput2);
  }
}
