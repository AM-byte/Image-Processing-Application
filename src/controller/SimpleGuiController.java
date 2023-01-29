package controller;

import model.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;

import view.GuiFrameView;
import view.GuiFrameViewImpl;

/**
 * This interface represents the operations offered by the gui controller.
 */
public class SimpleGuiController implements GuiController, ActionListener {

  private BetterController controller;
  private GuiFrameView view;
  private Map<String, Image> model;
  private ArrayList<String> commandNames;
  private Map<String, String> commandNameMapping;
  private StringBuilder log;
  private String currCommand;
  private String currImageSelected;
  private int brightenIncrement;

  /**
   * Initializes the gui controller.
   */
  public SimpleGuiController() {
    this.commandNameMapping = new HashMap<String, String>();
    this.commandNameMapping.put("brighten", "brighten");
    this.commandNameMapping.put("red component", "red-component");
    this.commandNameMapping.put("green component", "green-component");
    this.commandNameMapping.put("blue component", "blue-component");
    this.commandNameMapping.put("value component", "value-component");
    this.commandNameMapping.put("intensity component", "intensity-component");
    this.commandNameMapping.put("luma component", "luma-component");
    this.commandNameMapping.put("vertical flip", "vertical-flip");
    this.commandNameMapping.put("horizontal flip", "horizontal-flip");
    this.commandNameMapping.put("blur", "blur");
    this.commandNameMapping.put("sharpen", "sharpen");
    this.commandNameMapping.put("sepia", "sepia");
    this.commandNameMapping.put("greyscale", "greyscale");
    this.commandNameMapping.put("none", "none");

    this.model = new HashMap<String, Image>();
    this.commandNames = new ArrayList<String>(Arrays.asList("brighten",
            "red component", "green component", "blue component", "value component",
            "intensity component", "luma component", "vertical flip", "horizontal flip", "blur",
            "sharpen", "sepia", "greyscale"));
    this.view = new GuiFrameViewImpl(this.model, this.commandNames, this);

    this.log = new StringBuilder("");
    this.controller = new BetterControllerImpl(this.model, this.log);
    this.currCommand = "none";
    this.currImageSelected = "";
    this.brightenIncrement = 0;
  }

  /**
   * Constructor used only for testing purposes.
   *
   * @param model the given model
   * @param currImageName the current image selected
   * @param command the current command selected
   */
  public SimpleGuiController(Map<String, Image> model, String currImageName, String command) {
    this.commandNameMapping = new HashMap<String, String>();
    this.commandNameMapping.put("brighten", "brighten");
    this.commandNameMapping.put("red component", "red-component");
    this.commandNameMapping.put("green component", "green-component");
    this.commandNameMapping.put("blue component", "blue-component");
    this.commandNameMapping.put("value component", "value-component");
    this.commandNameMapping.put("intensity component", "intensity-component");
    this.commandNameMapping.put("luma component", "luma-component");
    this.commandNameMapping.put("vertical flip", "vertical-flip");
    this.commandNameMapping.put("horizontal flip", "horizontal-flip");
    this.commandNameMapping.put("blur", "blur");
    this.commandNameMapping.put("sharpen", "sharpen");
    this.commandNameMapping.put("sepia", "sepia");
    this.commandNameMapping.put("greyscale", "greyscale");
    this.commandNameMapping.put("none", "none");

    this.model = model;
    this.commandNames = new ArrayList<String>(Arrays.asList("brighten",
            "red component", "green component", "blue component", "value component",
            "intensity component", "luma component", "vertical flip", "horizontal flip", "blur",
            "sharpen", "sepia", "greyscale"));
    this.view = new GuiFrameViewImpl(this.model, this.commandNames, this);

    this.log = new StringBuilder("");
    this.controller = new BetterControllerImpl(this.model, this.log);
    this.currCommand = command;
    this.currImageSelected = currImageName;
    this.brightenIncrement = 0;
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();

    if (this.model.containsKey(command)) {
      this.view.selectRadioButton(command);
      this.view.setCurrImage(command);
      this.currImageSelected = command;
      return;
    }

    switch (command) {
      case "Open image": {
        try {
          String path = this.view.chooseImagePath();
          String name = this.view.inputImageName();
          if (name == null) {
            break;
          }
          this.controller.resetReadable(new StringReader("load " + path + " " + name));
          this.controller.process();
          this.view.renderView();
          this.currImageSelected = name;
        } catch (Exception err) {
          try {
            this.log.append(err.getMessage() + "\n");
          } catch (Exception error) {
            throw new IllegalStateException("Unable to write to log");
          }
          break;
        }
      }
      break;
      case "Transform image": {
        if (this.currCommand.equals("none")) {
          this.view.displayMessage("Please select a command transformation!");
          break;
        }

        if (!this.model.containsKey(this.currImageSelected)) {
          this.view.displayMessage("Please select an image to transform!");
          break;
        }

        if (this.currCommand.equals("brighten")) {
          int increment = this.view.inputBrightenIncrement();
          if (increment == 0) {
            break;
          }
          this.brightenIncrement = increment;
        }

        if (this.currCommand.equals("brighten") && this.brightenIncrement == 0) {
          this.view.displayMessage("Please enter a valid integer brighten increment!");
          break;
        }

        String destImageName = this.view.inputImageName();

        if (destImageName == null) {
          break;
        }

        String finalCommand = this.currCommand + " ";
        if (this.currCommand.equals("brighten")) {
          finalCommand = finalCommand + this.brightenIncrement + " ";
        }
        finalCommand = finalCommand + this.currImageSelected + " " + destImageName;

        this.controller.resetReadable(new StringReader(finalCommand));
        this.controller.process();
        this.view.renderView();
        this.currImageSelected = destImageName;
        this.brightenIncrement = 0;
      }
      break;
      case "Command chosen": {
        if (e.getSource() instanceof JComboBox) {
          JComboBox<String> box = (JComboBox<String>) e.getSource();
          String cmd = (String) box.getSelectedItem();
          if (this.commandNameMapping.containsKey(cmd)) {
            this.currCommand = commandNameMapping.get(cmd);
          }
        }
      }
      break;
      case "Save image": {
        String path = "";
        try {
          String directory = this.view.chooseFilePath();
          if (directory == null) {
            this.view.displayMessage("Please choose a folder within the current directory!");
            break;
          }
          String filename = this.view.inputFileName();
          if (filename == null) {
            break;
          }
          path = directory + "/" + filename;
          if (!this.model.containsKey(this.currImageSelected)) {
            this.view.displayMessage("Please select an image to save!");
            break;
          }
          this.controller.resetReadable(new StringReader("save " + path + " " +
                  this.currImageSelected));
          this.controller.process();
          this.view.renderView();
          this.view.displaySuccessMessage("Image " + path + " was successfully saved!");
        } catch (Exception err) {
          this.log.append(err.getMessage() + "\n");
          break;
        }
      }
      break;
      default:
    }
  }

  /**
   * Runs the program.
   */
  @Override
  public void runApplication() {
    this.view.initialiseFrame();
  }
}
