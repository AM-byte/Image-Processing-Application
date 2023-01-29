package controller;

import java.util.Map;

import controller.commandfunctions.BlurCommandFunc;
import controller.commandfunctions.GreyscaleCommandFunc;
import controller.commandfunctions.SepiaCommandFunc;
import controller.commandfunctions.SharpenCommandFunc;
import model.Image;
import view.View;

/**
 * Pro edition of the controller.
 */
public class ProController extends StarterController {

  private final boolean isSynchronous;

  /**
   * Constructor for initialising the Controller. The readable is System.in.
   *
   * @param view to use
   */
  public ProController(View view) {
    super(view);
    this.isSynchronous = true;
  }

  /**
   * Constructor for initializing the controller. The readable is assigned commands from the
   * scripts.
   *
   * @param in the readable to read inputs from
   * @param view to use
   */
  public ProController(Readable in, View view) {
    super(in, view);
    this.isSynchronous = false;
  }

  protected ProController(Map<String, Image> model, Appendable log)
          throws IllegalArgumentException {
    super(model, log);
    this.isSynchronous = false;
  }

  /**
   * Populates the hashMap of commands with valid commands and the required inputs for each.
   */
  @Override
  protected void optimize() {
    super.optimize();
    this.commands.put("blur", new BlurCommandFunc());
    this.commands.put("sharpen", new SharpenCommandFunc());
    this.commands.put("sepia", new SepiaCommandFunc());
    this.commands.put("greyscale", new GreyscaleCommandFunc());

    this.requiredInputs.put("blur", 2);
    this.requiredInputs.put("sharpen", 2);
    this.requiredInputs.put("sepia", 2);
    this.requiredInputs.put("greyscale", 2);
  }

  /**
   * Renders the starting message to the view.
   * @throws IllegalArgumentException if the message cannot be rendered
   */
  @Override
  protected void startMessage() throws IllegalArgumentException {
    this.writeMessage("Starting program...");
    if (this.isSynchronous) {
      this.printMenu();
    }
  }

  /**
   * Renders the list of commands and how to use them.
   *
   * @throws IllegalArgumentException if the message cannot be output.
   */
  @Override
  protected void printMenu() {
    super.printMenu();
    this.writeMessage("blur _image-name_ _dest-image-name_");
    this.writeMessage("sharpen _image-name_ _dest-image-name_");
    this.writeMessage("greyscale _image-name_ _dest-image-name_");
    this.writeMessage("sepia _image-name_ _dest-image-name_");
  }

  /**
   * Gets the readable to read from (could be system.in if isSynchronous or a script based input).
   * @return the inputs as a readable
   */
  protected Readable getReadable() {
    if (this.isSynchronous) {
      return this.getUserInputs();
    } else {
      return this.in;
    }
  }
}
