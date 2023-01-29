package controller.commandfunctions;

import controller.command.BlurCommand;
import controller.command.Command;

/**
 * Function class for the blur command.
 */
public class BlurCommandFunc extends TwoArgCommandFunc {

  /**
   * Gets the error message for invalid arguments.
   *
   * @return the error message.
   */
  @Override
  protected String getCommandErrorMsg() {
    return "blur command expected image-name and dest-image-name as the arguments but didn't " +
            "receive them!";
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
    return new BlurCommand(userInput1, userInput2);
  }
}
