package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.command.Command;
import model.Image;
import view.SimpleView;
import view.View;

/**
 * Class that implements the operations offered by the controller of the program.
 */
public abstract class SimpleController implements IController {
  private Map<String, Image> data;
  protected Map<String, Function<Scanner, Command>> commands;
  protected Map<String, Integer> requiredInputs;
  protected Readable in;
  private View view;

  /**
   * Constructor for initialising the Controller. The readable is System.in.
   *
   * @param view to use
   */
  protected SimpleController(View view) {
    this(new InputStreamReader(System.in), view);
  }

  /**
   * Constructor for initializing the controller. The readable is assigned commands from
   * the scripts.
   *
   * @param in the readable to read inputs from
   * @param view to use
   * @throws IllegalArgumentException if the arguments provided are null
   */
  protected SimpleController(Readable in, View view) throws IllegalArgumentException {

    if (in == null || view == null) {
      throw new IllegalArgumentException("inputs provided were null!");
    }

    this.in = in;
    this.data = new HashMap<String, Image>();
    this.view = view;

    this.optimize();
  }

  protected SimpleController(Map<String, Image> model, Appendable log)
          throws IllegalArgumentException {
    if (log == null || model == null) {
      throw new IllegalArgumentException("inputs provided were null!");
    }

    this.data = model;
    this.view = new SimpleView(log);
    this.optimize();
  }

  /**
   * Populates the hashMap of commands with valid commands and the required inputs for each.
   */
  protected abstract void optimize();

  /**
   * Starts the program.
   *
   * @throws IllegalArgumentException if the command cannot be applied.
   */
  @Override
  public void process() throws IllegalArgumentException {

    this.startMessage();

    List<Command> executables = this.getExecutables();

    for (Command executable : executables) {
      this.writeMessage("Executing command: " + executable.name());
      try {
        executable.execute(this.data);
      } catch (IllegalArgumentException e) {
        this.writeMessage("Could not execute command | " + e.getMessage());
        break;
      }

    }

    this.endMessage();
  }

  /**
   * Gets the readable to read from (could be system.in or a script based input).
   * @return the inputs as a readable
   */
  protected abstract Readable getReadable();

  /**
   * Gets the commands inputted as a list of command objects to execute.
   * @return the list of Command executable objects
   * @throws IllegalArgumentException if the command cannot be loaded
   */
  protected List<Command> getExecutables() throws IllegalArgumentException {
    List<Command> executables = new ArrayList<Command>();

    Scanner sc = new Scanner(this.getReadable());

    boolean invalidCommand = false;

    while (sc.hasNext() && !invalidCommand) {
      String command = sc.next();

      if (command.equalsIgnoreCase("q") || command.equalsIgnoreCase("quit")) {
        invalidCommand = true;
        this.writeMessage("quitting...");
        break;
      }

      Function<Scanner, Command> cmd = this.commands.getOrDefault(command, null);

      if (cmd == null) {

        this.writeMessage("Invalid command provided: " + command);
        invalidCommand = true;
        break;

      } else {
        try {
          executables.add(cmd.apply(sc));
        } catch (IllegalArgumentException e) {
          this.writeMessage("Could not read command | " + e.getMessage());
          invalidCommand = true;
          break;
        }
      }
    }

    if (invalidCommand) {
      return new ArrayList<Command>();
    } else {
      return executables;
    }
  }

  /**
   * Renders the starting message to the view.
   * @throws IllegalArgumentException if the message cannot be rendered
   */
  protected void startMessage() throws IllegalArgumentException {
    this.writeMessage("Starting program...");
    this.printMenu();
  }

  /**
   * Renders the list of commands and how to use them.
   *
   * @throws IllegalArgumentException if the message cannot be output.
   */
  protected abstract void printMenu();

  /**
   * Renders the end message to the view.
   * @throws IllegalArgumentException if the message cannot be rendered
   */
  private void endMessage() throws IllegalArgumentException {
    this.writeMessage("Ending the program...");
    this.writeMessage("Thank you!");
  }

  /**
   * Renders the given message to the view.
   * @param message to be rendered
   * @throws IllegalArgumentException if the message cannot be rendered
   */
  protected void writeMessage(String message) throws IllegalArgumentException {
    try {
      view.renderMessage(message);
      view.renderMessage("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  // gets the user inputs and validates them.
  protected StringReader getUserInputs() {
    StringBuilder userInputs = new StringBuilder();

    boolean quit = false;
    Scanner sc = new Scanner(this.in);

    while (!quit) {

      this.writeMessage("Enter a command: ");

      String command = sc.next();

      if (command.equalsIgnoreCase("q") || command.equalsIgnoreCase("quit")) {
        this.writeMessage("quitting...");
        quit = true;
        break;
      }

      while (!this.commands.containsKey(command)) {
        this.writeMessage(command + " was an invalid command, please choose a valid one!");
        command = sc.next();
      }

      userInputs.append(command + " ");

      this.writeMessage("Please provide " + this.requiredInputs.get(command) + " arguments: ");

      for (int i = 0; i < this.requiredInputs.get(command); i++) {
        String arg = sc.next();
        if (arg.equalsIgnoreCase("q") || arg.equalsIgnoreCase("quit")) {
          this.writeMessage("quitting...");
          quit = true;
          break;
        }
        userInputs.append(arg + " ");
      }
    }

    return new StringReader(userInputs.toString());
  }
}
