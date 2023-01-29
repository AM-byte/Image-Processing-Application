package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

/**
 * This class implements the operations used to populate a image.
 */
public class ImageDecorator implements PanelDecorator {
  private PanelDecorator panelDecorator;
  private String imageName;
  private BufferedImage image;

  /**
   * Initializes the image decorator.
   *
   * @param panelDecorator the given panel decorator
   * @param imageName the name of the image
   * @param image the given image
   */
  public ImageDecorator(PanelDecorator panelDecorator, String imageName, BufferedImage image) {
    this.panelDecorator = panelDecorator;
    this.imageName = imageName;
    this.image = image;
  }

  /**
   * Adds the following components to the panel.
   *
   * @param panels the given components
   */
  @Override
  public void decorate(ArrayList<Component> panels) {
    JPanel panel = new JPanel();
    panel.setBorder(new TitledBorder(this.imageName));
    JScrollPane scrollPane = new JScrollPane(new JLabel(new ImageIcon(this.image)));
    scrollPane.setPreferredSize(new Dimension(400, 400));
    panel.add(scrollPane);
    panels.add(panel);
    this.panelDecorator.decorate(panels);
  }
}
