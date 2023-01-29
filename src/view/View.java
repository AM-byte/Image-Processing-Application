package view;

import java.io.IOException;

/**
 * Interface that represents the operations offered by the view of the program.
 */
public interface View {

  /**
   * Sends a message to user.
   * @param message to be sent to the user
   * @throws IOException if the view cannot render the message
   */
  void renderMessage(String message) throws IOException;
}
