package view;

/**
 * This interface represents the operations offered by the gui view for the program.
 */
public interface GuiFrameView {

  /**
   * Initializes the frame in the gui window.
   */
  void initialiseFrame();

  /**
   * Renders the view of the gui window.
   */
  void renderView();

  /**
   * Assigns the extension to this image.
   *
   * @return the extension type
   * @throws IllegalArgumentException if the operation is cancelled
   */
  String chooseImagePath() throws IllegalStateException;

  /**
   * Gets the name of the image from the user.
   * @return the name of the image
   */
  String inputImageName();

  /**
   * Gets the value to brighten the image from the user.
   * @return the value to brighten image by
   */
  int inputBrightenIncrement();

  /**
   * Displays the given message.
   *
   * @param message the given message
   */
  void displayMessage(String message);

  /**
   * Sets this image to be the given imageName.
   *
   * @param imageName the name of the image to set as this image
   */
  void setCurrImage(String imageName);

  /**
   * Selects the radio button clicked on by the user and deselects all others.
   *
   * @param selected the name of the selected radio button
   */
  void selectRadioButton(String selected);

  /**
   * Chooses the appropriate file path to save this image.
   *
   * @return the extension
   * @throws IllegalStateException if the operation is cancelled
   */
  String chooseFilePath() throws IllegalStateException;

  /**
   * Asks the user to input the name of the file to save it.
   *
   * @return the name of the file
   * @throws IllegalStateException if the name of file already exists or doesn't fit the naming
   *                               criteria
   */
  String inputFileName() throws IllegalStateException;

  /**
   * Displays a message once the image has been saved.
   *
   * @param message the given message to display
   */
  void displaySuccessMessage(String message);
}
