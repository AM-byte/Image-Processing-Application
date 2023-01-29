package controller.command;

import java.util.Map;

import model.Image;

/**
 * Class for the brighten command.
 */
public class BrightenCommand implements Command {
  private int increment;
  private String imageName;
  private String destImageName;

  /**
   * Constructor for initialising the BrightenCommand.
   *
   * @param increment to brighten by
   * @param imageName to brighten
   * @param destImageName to save the brightened image to
   */
  public BrightenCommand(int increment, String imageName, String destImageName) {
    this.increment = increment;
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  /**
   * Executes the brighten command given the model.
   *
   * @param data the model
   * @throws IllegalArgumentException if the model does not contain the image name
   */
  @Override
  public void execute(Map<String, Image> data) throws IllegalArgumentException {

    if (!data.containsKey(this.imageName)) {
      throw new IllegalArgumentException("Image with name " + this.imageName + " doesn't exist!");
    }

    Image newImage = data.get(this.imageName).brightenImage(this.increment);
    data.put(this.destImageName, newImage);
  }

  /**
   * Gets the name of the command.
   *
   * @return the name of the command
   */
  @Override
  public String name() {
    return "brighten";
  }
}
