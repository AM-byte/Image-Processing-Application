package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.command.Command;
import controller.commandfunctions.BrightenCommandFunc;
import controller.commandfunctions.ComponentCommandFunc;
import controller.commandfunctions.HorizontalFlipCommandFunc;
import controller.commandfunctions.LoadCommandFunc;
import controller.commandfunctions.SaveCommandFunc;
import controller.commandfunctions.VerticalFlipCommandFunc;
import model.Image;
import view.View;

/**
 * Started edition of the controller.
 */
public class StarterController extends SimpleController {

  /**
   * Constructor for initialising the Controller. The readable is System.in.
   *
   * @param view to use
   */
  public StarterController(View view) {
    super(view);
  }

  /**
   * Constructor for initializing the controller. The readable is assigned commands from
   * the scripts.
   *
   * @param in the readable to read inputs from
   * @param view to use
   */
  protected StarterController(Readable in, View view) {
    super(in, view);
  }

  protected StarterController(Map<String, Image> model, Appendable log)
          throws IllegalArgumentException {
    super(model, log);
  }

  /**
   * Populates the hashMap of commands with valid commands and the required inputs for each.
   */
  @Override
  protected void optimize() {
    this.commands = new HashMap<String, Function<Scanner, Command>>();
    this.commands.put("load", new LoadCommandFunc());
    this.commands.put("red-component", new ComponentCommandFunc("red"));
    this.commands.put("green-component", new ComponentCommandFunc("green"));
    this.commands.put("blue-component", new ComponentCommandFunc("blue"));
    this.commands.put("value-component", new ComponentCommandFunc("value"));
    this.commands.put("intensity-component", new ComponentCommandFunc("intensity"));
    this.commands.put("luma-component", new ComponentCommandFunc("luma"));
    this.commands.put("brighten", new BrightenCommandFunc());
    this.commands.put("vertical-flip", new VerticalFlipCommandFunc());
    this.commands.put("horizontal-flip", new HorizontalFlipCommandFunc());
    this.commands.put("save", new SaveCommandFunc());

    this.requiredInputs = new HashMap<String, Integer>();
    this.requiredInputs.put("load", 2);
    this.requiredInputs.put("red-component", 2);
    this.requiredInputs.put("green-component", 2);
    this.requiredInputs.put("blue-component", 2);
    this.requiredInputs.put("value-component", 2);
    this.requiredInputs.put("intensity-component", 2);
    this.requiredInputs.put("luma-component", 2);
    this.requiredInputs.put("brighten", 3);
    this.requiredInputs.put("vertical-flip", 3);
    this.requiredInputs.put("horizontal-flip", 2);
    this.requiredInputs.put("save", 2);
  }

  /**
   * Renders the list of commands and how to use them.
   *
   * @throws IllegalArgumentException if the message cannot be output.
   */
  protected void printMenu() throws IllegalArgumentException {
    this.writeMessage("Valid commands include:");
    this.writeMessage("q or quit to stop!");
    this.writeMessage("load _filepath_ _image-name_");
    this.writeMessage("brighten _increment_ _image-name_ _dest-image-name_");
    this.writeMessage("red-component _image-name_ _dest-image-name_");
    this.writeMessage("green-component _image-name_ _dest-image-name_");
    this.writeMessage("blue-component _image-name_ _dest-image-name_");
    this.writeMessage("value-component _image-name_ _dest-image-name_");
    this.writeMessage("intensity-component _image-name_ _dest-image-name_");
    this.writeMessage("luma-component _image-name_ _dest-image-name_");
    this.writeMessage("vertical-flip _image-name_ _dest-image-name_");
    this.writeMessage("horizontal-flip _image-name_ _dest-image-name_");
    this.writeMessage("save _filepath_ _image-name_");
  }

  /**
   * Gets the readable to read from - system.in.
   * @return the inputs as a readable
   */
  protected Readable getReadable() {
    return this.getUserInputs();
  }
}
