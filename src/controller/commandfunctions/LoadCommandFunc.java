package controller.commandfunctions;

import controller.command.Command;
import controller.command.LoadCommand;

/**
 * Function class for the load command.
 */
public class LoadCommandFunc extends TwoArgCommandFunc {

  /**
   * Gets the error message for invalid arguments.
   *
   * @return the error message.
   */
  @Override
  protected String getCommandErrorMsg() {
    return "load command expected image-path and image-name but didn't receive them!";
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
    return new LoadCommand(userInput1, userInput2);
  }
}
