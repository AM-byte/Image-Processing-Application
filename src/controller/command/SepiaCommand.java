package controller.command;

import java.util.Map;

import model.ITransformation;
import model.Image;
import model.Transformation;

/**
 * Class for the sepia command.
 */
public class SepiaCommand implements Command {

  private String imageName;
  private String destImageName;

  /**
   * Constructor for initialising a HorizontalFlipCommand.
   *
   * @param imageName of the image to flip horizontally
   * @param destImageName to save the flipped image to
   */
  public SepiaCommand(String imageName, String destImageName) {
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
    matrix[0] = new double[]{0.393, 0.769, 0.189};
    matrix[1] = new double[]{0.349, 0.686, 0.168};
    matrix[2] = new double[]{0.272, 0.534, 0.131};

    ITransformation sepiaTransformation = new Transformation(matrix);
    Image sepiaImage = img.applyTransformation(sepiaTransformation);
    data.put(this.destImageName, sepiaImage);

  }

  /**
   * Gets the name of the command.
   *
   * @return the name of the command
   */
  @Override
  public String name() {
    return "sepia";
  }
}
