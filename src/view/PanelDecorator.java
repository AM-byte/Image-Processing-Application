package view;

import java.awt.Component;
import java.util.ArrayList;

/**
 * This interface represents the operations used to populate a panel.
 */
public interface PanelDecorator {

  /**
   * Adds the following components to the panel.
   *
   * @param panels the given components
   */
  void decorate(ArrayList<Component> panels);
}
