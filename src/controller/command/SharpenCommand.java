package controller.command;

import java.util.Map;

import model.Filter;
import model.IFilter;
import model.Image;

/**
 * Class for the sharpen command.
 */
public class SharpenCommand implements Command {

  private String imageName;
  private String destImageName;

  /**
   * Constructor for initialising a HorizontalFlipCommand.
   *
   * @param imageName of the image to flip horizontally
   * @param destImageName to save the flipped image to
   */
  public SharpenCommand(String imageName, String destImageName) {
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

    double[][] kernel = new double[5][5];
    kernel[0] = new double[]{-0.125, -0.125, -0.125, -0.125, -0.125};
    kernel[1] = new double[]{-0.125, 0.25, 0.25, 0.25, -0.125};
    kernel[2] = new double[]{-0.125, 0.25, 1, 0.25, -0.125};
    kernel[3] = new double[]{-0.125, 0.25, 0.25, 0.25, -0.125};
    kernel[4] = new double[]{-0.125, -0.125, -0.125, -0.125, -0.125};

    IFilter sharpenFilter = new Filter(kernel);
    Image sharpened = img.applyFilter(sharpenFilter);
    data.put(this.destImageName, sharpened);

  }

  /**
   * Gets the name of the command.
   *
   * @return the name of the command
   */
  @Override
  public String name() {
    return "sharpen";
  }
}
