package controller.command;

import java.util.Map;

import model.Image;

/**
 * Class for the convert to greyscale command.
 */
public class ComponentCommand implements Command {
  private String method;
  private String imageName;
  private String destImageName;

  /**
   * Constructor for initialising the ComponentCommand.
   *
   * @param method by which to convert the image to greyscale
   * @param imageName of the image to convert to greyscale
   * @param destImageName of the greyscale image to save to
   */
  public ComponentCommand(String method, String imageName, String destImageName) {
    this.method = method;
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  /**
   * Executes the convert to greyscale command given the model.
   *
   * @param data the model
   * @throws IllegalArgumentException if the model does not contain the image name
   */
  @Override
  public void execute(Map<String, Image> data) throws IllegalArgumentException {

    if (!data.containsKey(this.imageName)) {
      throw new IllegalArgumentException("Image with name " + this.imageName + " doesn't exist!");
    }

    Image newImage = data.get(this.imageName).convertGreyscale(this.method);
    data.put(this.destImageName, newImage);
  }

  /**
   * Gets the name of the command.
   *
   * @return the name of the command
   */
  @Override
  public String name() {
    return this.method + "-component";
  }
}
