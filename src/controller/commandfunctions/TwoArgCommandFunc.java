package controller.commandfunctions;

import java.util.Scanner;
import java.util.function.Function;

import controller.command.Command;

/**
 * Abstract function class for any two argument command.
 */
public abstract class TwoArgCommandFunc implements Function<Scanner, Command> {

  protected abstract String getCommandErrorMsg();

  protected abstract Command createCommand(String userInput1, String userInput2);

  /**
   * Applies this function to the given argument.
   *
   * @param scanner the function argument
   * @return the function result
   */
  public Command apply(Scanner scanner) {
    try {
      String arg1 = scanner.next();
      String arg2 = scanner.next();
      return this.createCommand(arg1, arg2);
    } catch (Exception e) {
      throw new IllegalArgumentException(this.getCommandErrorMsg());
    }
  }
}
