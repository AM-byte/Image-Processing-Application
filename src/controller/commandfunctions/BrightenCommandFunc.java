package controller.commandfunctions;

import java.util.Scanner;
import java.util.function.Function;

import controller.command.BrightenCommand;
import controller.command.Command;

/**
 * Function class for the brighten command.
 */
public class BrightenCommandFunc implements Function<Scanner, Command> {

  /**
   * Applies this function to the given argument.
   *
   * @param scanner the function argument
   * @return the function result
   */
  @Override
  public Command apply(Scanner scanner) {

    try {
      int increment = scanner.nextInt();
      String imageName = scanner.next();
      String destImageName = scanner.next();
      return new BrightenCommand(increment, imageName, destImageName);
    } catch (Exception e) {
      throw new IllegalArgumentException("brighten command expected increment, image-name," +
              " and dest-image-name as the arguments but didn't receive them!");
    }
  }

}
