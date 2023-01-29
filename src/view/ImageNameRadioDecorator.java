package view;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JRadioButton;

/**
 * This class implements the operations used to populate a radio button.
 */
public class ImageNameRadioDecorator implements PanelDecorator {
  private PanelDecorator panelDecorator;
  private String imageName;
  private ActionListener actionListener;

  /**
   * Initializes the decorator with the given arguments.
   *
   * @param panelDecorator the given panel decorator
   * @param imageName the given name of the image
   * @param actionListener the given action listener
   */
  public ImageNameRadioDecorator(PanelDecorator panelDecorator, String imageName,
                                    ActionListener actionListener) {
    this.panelDecorator = panelDecorator;
    this.imageName = imageName;
    this.actionListener = actionListener;
  }

  /**
   * Adds the following components to the panel.
   *
   * @param panels the given components
   */
  @Override
  public void decorate(ArrayList<Component> panels) {
    JRadioButton radioButton = new JRadioButton(this.imageName);
    radioButton.setSelected(false);
    radioButton.setActionCommand(this.imageName);
    radioButton.addActionListener(this.actionListener);
    panels.add(radioButton);
    this.panelDecorator.decorate(panels);
  }
}
