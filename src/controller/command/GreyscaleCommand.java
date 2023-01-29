package controller.command;

import java.util.Map;

import model.ITransformation;
import model.Image;
import model.Transformation;

/**
 * Class for the brighten command.
 */
public class GreyscaleCommand implements Command {
  private String imageName;
  private String destImageName;

  /**
   * Constructor for initialising a HorizontalFlipCommand.
   *
   * @param imageName of the image to flip horizontally
   * @param destImageName to save the flipped image to
   */
  public GreyscaleCommand(String imageName, String destImageName) {
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  /**
   * Executes the command given the model.
   *
   * @param data the model
   * @throws IllegalArgumentException if the command failed to execute
   */
  @Override
  public void execute(Map<String, Image> data) throws IllegalArgumentException {

    if (!data.containsKey(this.imageName)) {
      throw new IllegalArgumentException("Image with name " + this.imageName + " doesn't exist!");
    }

    Image img = data.get(this.imageName);

    double[][] matrix = new double[3][3];
    matrix[0] = new double[]{0.2126, 0.7152, 0.0772};
    matrix[1] = new double[]{0.2126, 0.7152, 0.0772};
    matrix[2] = new double[]{0.2126, 0.7152, 0.0772};

    ITransformation greyScaleTransformation = new Transformation(matrix);
    Image greyImage = img.applyTransformation(greyScaleTransformation);
    data.put(this.destImageName, greyImage);

  }

  /**
   * Gets the name of the command.
   *
   * @return the name of the command
   */
  @Override
  public String name() {
    return "greyscale";
  }
}
