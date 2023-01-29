import controller.GuiController;
import controller.ProController;
import controller.SimpleGuiController;
import model.utility.FileUtil;
import controller.IController;
import view.SimpleView;

/**
 * Class for running the main program.
 */
public class MainProgram {

  /**
   * Runs the program. If one of two arguments are provided then the text mode is used.
   * It requires the filepath of the text file with the commands as the
   * second argument (if needed). If one argument is provided then the Readable used is System.in.
   * If no arguments are provided then the gui view is used.
   * @param args the filepath of the commands text file or no arguments.
   */
  public static void main(String[] args) {

    if (args.length == 0) {
      GuiController guiController = new SimpleGuiController();
      guiController.runApplication();
      return;
    }

    IController controller = null;
    if (args.length == 1) {
      if (!args[0].equals("-text")) {
        System.out.println("Expected argument was '-text' but got " + args[0]);
        System.out.println("Exiting program... Thank you!");
        return;
      }
      controller = new ProController(new SimpleView());
    }

    if (args.length == 2) {
      if (!args[0].equals("-file")) {
        System.out.println("The first expected argument was '-file' but got " + args[0]);
        System.out.println("Exiting program... Thank you!");
        return;
      }
      String filename = args[1];
      Readable in = null;
      try {
        in = new FileUtil().readScript(filename);
      } catch (IllegalArgumentException e) {
        System.out.println("Unable to read file | " + e.getMessage());
        System.out.println("Exiting program... Thank you!");
        return;
      }
      controller = new ProController(in, new SimpleView());
    }

    if (args.length > 2) {
      System.out.println("Expected 0, 1, or 2 arguments but got " + args.length + " arguments");
      System.out.println("Exiting program... Thank you!");
      return;
    }

    if (controller != null) {
      controller.process();
    }

    System.out.println("Exiting program... Thank you!");
  }
}
