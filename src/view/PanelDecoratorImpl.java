package view;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * This class implements the operations used to populate a panel.
 */
public class PanelDecoratorImpl implements PanelDecorator {

  private JPanel panel;

  /**
   * Initializes the panel decorater.
   *
   * @param panel the given panel
   */
  public PanelDecoratorImpl(JPanel panel) {
    this.panel = panel;
  }

  /**
   * Adds the following components to the panel.
   *
   * @param panels the given components
   */
  @Override
  public void decorate(ArrayList<Component> panels) {
    for (Component panel : panels) {
      this.panel.add(panel);
    }
  }
}
