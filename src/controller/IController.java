package controller;

/**
 * Interface that represents the operations offered by a controller of the program.
 */
public interface IController {

  /**
   * Processes the inputs and executes the commands.
   *
   * @throws IllegalArgumentException if the controller cannot read inputs or write outputs
   */
  void process() throws IllegalArgumentException;
}
