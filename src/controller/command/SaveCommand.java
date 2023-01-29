package controller.command;

import java.util.Map;

import model.Image;
import model.utility.ImageUtil;

/**
 * Class for the save command.
 */
public class SaveCommand implements Command {
  private String filepath;
  private String imageName;

  /**
   * Constructor for initialising the SaveCommand.
   *
   * @param filepath of the image to be saved
   * @param imageName of the image to save
   */
  public SaveCommand(String filepath, String imageName) {
    this.filepath = filepath;
    this.imageName = imageName;
  }

  /**
   * Executes the save command given the model.
   *
   * @param data the model
   * @throws IllegalArgumentException if the image could not be saved
   */
  @Override
  public void execute(Map<String, Image> data) throws IllegalArgumentException {

    if (!data.containsKey(this.imageName)) {
      throw new IllegalArgumentException("Image with name " + this.imageName + " doesn't exist!");
    }

    ImageUtil imageUtil = new ImageUtil();
    Image image = data.get(this.imageName);
    try {
      imageUtil.saveImage(this.filepath, image.getWidth(), image.getHeight(), image.writePixels());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

  }

  /**
   * Gets the name of the command.
   *
   * @return the name of the command
   */
  @Override
  public String name() {
    return "save";
  }
}
