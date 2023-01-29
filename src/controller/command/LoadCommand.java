package controller.command;

import java.util.Map;

import model.Image;
import model.utility.ImageUtil;

/**
 * Class for the load command.
 */
public class LoadCommand implements Command {
  private String filepath;
  private String imageName;

  /**
   * Constructor for initialising a LoadCommand.
   *
   * @param filepath of the image to load
   * @param imageName of the image
   */
  public LoadCommand(String filepath, String imageName) {
    this.filepath = filepath;
    this.imageName = imageName;
  }

  /**
   * Executes the load command given the model.
   *
   * @param data the model
   * @throws IllegalArgumentException if the image could not be loaded
   */
  @Override
  public void execute(Map<String, Image> data) throws IllegalArgumentException {
    ImageUtil imageUtil = new ImageUtil();
    try {
      data.put(this.imageName, imageUtil.loadImage(this.filepath));
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
    return "load";
  }
}
