package controller.command;

import java.util.Map;

import model.Image;

/**
 * Interface for a Command.
 */
public interface Command {

  /**
   * Executes the command given the model.
   *
   * @param data the model
   * @throws IllegalArgumentException if the command failed to execute
   */
  void execute(Map<String, Image> data) throws IllegalArgumentException;

  /**
   * Gets the name of the command.
   * @return the name of the command
   */
  String name();
}