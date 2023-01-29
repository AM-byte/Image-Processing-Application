package controller.command;

import java.util.Map;

import model.Image;

/**
 * Class for the vertical-flip command.
 */
public class VerticalFlipCommand implements Command {
  private String imageName;
  private String destImageName;

  /**
   * Constructor for initialising a VerticalFlipCommand.
   *
   * @param imageName of the image to flip vertically
   * @param destImageName to save the flipped image to
   */
  public VerticalFlipCommand(String imageName, String destImageName) {
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  /**
   * Executes the vertical-flip command given the model.
   *
   * @param data the model
   * @throws IllegalArgumentException if the model does not contain the image name
   */
  @Override
  public void execute(Map<String, Image> data) throws IllegalArgumentException {

    if (!data.containsKey(this.imageName)) {
      throw new IllegalArgumentException("Image with name " + this.imageName + " doesn't exist!");
    }

    Image newImage = data.get(this.imageName).flipVertical();
    data.put(this.destImageName, newImage);
  }

  /**
   * Gets the name of the command.
   *
   * @return the name of the command
   */
  @Override
  public String name() {
    return "vertical-flip";
  }
}
