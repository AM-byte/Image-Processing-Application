package controller.command;

import java.util.Map;

import model.Image;

/**
 * Class for the horizontal-flip command.
 */
public class HorizontalFlipCommand implements Command {
  private String imageName;
  private String destImageName;

  /**
   * Constructor for initialising a HorizontalFlipCommand.
   *
   * @param imageName of the image to flip horizontally
   * @param destImageName to save the flipped image to
   */
  public HorizontalFlipCommand(String imageName, String destImageName) {
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  /**
   * Executes the horizontal-flip command given the model.
   *
   * @param data the model
   * @throws IllegalArgumentException if the model does not contain the image name
   */
  @Override
  public void execute(Map<String, Image> data) throws IllegalArgumentException {

    if (!data.containsKey(this.imageName)) {
      throw new IllegalArgumentException("Image with name " + this.imageName + " doesn't exist!");
    }

    Image newImage = data.get(this.imageName).flipHorizontal();
    data.put(this.destImageName, newImage);
  }

  /**
   * Gets the name of the command.
   *
   * @return the name of the command
   */
  @Override
  public String name() {
    return "horizontal-flip";
  }
}
