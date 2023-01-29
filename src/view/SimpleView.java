package view;

import java.io.IOException;

/**
 * Class that implements the operations offered by the view of the program.
 */
public class SimpleView implements View {
  private Appendable out;

  /**
   * Constructor for initialising a view when no appendables are given.
   * The default appendable is System.out.
   */
  public SimpleView() {
    this.out = System.out;
  }

  /**
   * Constructor for initialising a view with a given appendable.
   * @param out the appendable
   * @throws IllegalArgumentException if the appendable is null
   */
  public SimpleView(Appendable out) throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("Appendable cannot be null!");
    }

    this.out = out;
  }

  /**
   * Sends a message to user.
   * @param message to be sent to the user
   * @throws IOException if the view cannot render the message
   */
  @Override
  public void renderMessage(String message) throws IOException {
    try {
      this.out.append(message);
    } catch (IOException e) {
      throw new IOException("Unable to render message!");
    }
  }
}
