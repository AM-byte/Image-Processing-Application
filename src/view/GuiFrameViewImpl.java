package view;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Image;

/**
 * This class implements the operations offered by the gui view for the program.
 */
public class GuiFrameViewImpl implements GuiFrameView {
  private Map<String, Image> model;
  private ArrayList<String> commandNames;
  private ActionListener actionListener;
  private Deque<String> imageNamesToBeAdded;
  private Deque<String> imageNamesAdded;
  private Deque<BufferedImage> imagesToBeAdded;
  private JFrame frame;
  private JPanel mainPanel;
  private JPanel controlPanel;
  private JPanel imageCheckBoxesPanel;
  private JPanel currImagePanel;
  private JScrollPane currImageScrollPane;
  private ImageIcon currImageIcon;
  private ImageIcon currHistoIcon;
  private String currImageName;
  private JPanel imagesPanel;

  /**
   * Initializes a gui fram with the given model, command names and the action listener.
   *
   * @param model the given model
   * @param commandNames the given list of commands
   * @param actionListener the action listener used to detect any inputs from the user
   */
  public GuiFrameViewImpl(Map<String, Image> model, ArrayList<String> commandNames,
                          ActionListener actionListener) {

    // set model and listeners
    this.model = model;
    this.commandNames = commandNames;
    this.actionListener = actionListener;
    this.imageNamesToBeAdded = new ArrayDeque<String>();
    this.imageNamesAdded = new ArrayDeque<String>();
    this.imagesToBeAdded = new ArrayDeque<BufferedImage>();

    // create the frame
    this.frame = new JFrame();
    this.frame.setTitle("Image Processing");

    // add the main panel and main scroll pane
    this.mainPanel = new JPanel();
    this.mainPanel.setLayout(new GridBagLayout());
    JScrollPane mainScrollPane = new JScrollPane(this.mainPanel);
    this.frame.add(mainScrollPane);

    // add create the sub panels
    this.createSubPanels();
    this.renderView();
  }

  // creates panels inside the main panel
  private void createSubPanels() {
    GridBagConstraints mainC = new GridBagConstraints();

    // create the control panel
    this.controlPanel = new JPanel();
    this.controlPanel.setLayout(new GridBagLayout());
    this.controlPanel.setBorder(new TitledBorder("Controls"));
    mainC.fill = GridBagConstraints.HORIZONTAL;
    mainC.gridx = 0;
    mainC.gridy = 0;
    mainC.ipady = 50;
    mainC.weightx = 0.5;
    mainC.anchor = GridBagConstraints.PAGE_START;
    this.mainPanel.add(this.controlPanel, mainC);

    // add to the control panel
    GridBagConstraints c = new GridBagConstraints();

    // add a file open button
    JButton fileOpenButton = new JButton("Open image");
    fileOpenButton.setActionCommand("Open image");
    fileOpenButton.addActionListener(this.actionListener);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 0.5;
    c.gridx = 0;
    c.gridy = 0;
    this.controlPanel.add(fileOpenButton, c);

    // add a file save button
    JButton fileSaveButton = new JButton("Save image");
    fileSaveButton.setActionCommand("Save image");
    fileSaveButton.addActionListener(this.actionListener);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 0.5;
    c.gridx = 1;
    c.gridy = 0;
    this.controlPanel.add(fileSaveButton, c);

    // add the command options dropdown
    JComboBox<String> commandsOptions = new JComboBox<String>();
    commandsOptions.setActionCommand("Command chosen");
    commandsOptions.addActionListener(this.actionListener);
    commandsOptions.addItem("none");
    for (String command : this.commandNames) {
      commandsOptions.addItem(command);
    }
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 0.0;
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 2;
    this.controlPanel.add(commandsOptions, c);

    // add the image names panel
    this.imageCheckBoxesPanel = new JPanel();
    this.imageCheckBoxesPanel.setBorder(new TitledBorder("Select Image To Edit: "));
    this.imageCheckBoxesPanel.setLayout(new GridLayout(0, 5));
    JScrollPane imageCheckBoxesScrollPane = new JScrollPane(this.imageCheckBoxesPanel);
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 2;
    c.insets.top = 10;
    c.insets.left = 10;
    c.insets.bottom = 10;
    c.insets.right = 10;
    c.weightx = 1;
    this.controlPanel.add(imageCheckBoxesScrollPane, c);

    // add the transform button
    JButton transformButton = new JButton("Transform Image");
    transformButton.setActionCommand("Transform image");
    transformButton.addActionListener(this.actionListener);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 2;
    c.weightx = 0.5;
    this.controlPanel.add(transformButton, c);

    // add the curr image panel
    this.currImagePanel = new JPanel();
    this.currImagePanel.setBorder(new TitledBorder(""));
    this.currImagePanel.setLayout(new GridBagLayout());
    mainC.fill = GridBagConstraints.HORIZONTAL;
    mainC.gridx = 0;
    mainC.gridy = 1;
    mainC.ipady = 0;
    mainC.weighty = 0.0;
    this.mainPanel.add(this.currImagePanel, mainC);

    GridBagConstraints currImageC = new GridBagConstraints();
    this.currImageIcon = new ImageIcon();
    JLabel currImage = new JLabel(this.currImageIcon);
    this.currImageScrollPane = new JScrollPane(currImage);
    this.currHistoIcon = new ImageIcon();
    JLabel currHistoLabel = new JLabel(this.currHistoIcon);
    this.currImagePanel.setBorder(new TitledBorder("Current Image: "));
    JPanel currHistoPanel = new JPanel();
    currHistoPanel.setBorder(new TitledBorder("Histogram"));
    currHistoPanel.setPreferredSize(new Dimension(400, 400));
    currHistoPanel.add(currHistoLabel);
    currImageC.fill = GridBagConstraints.HORIZONTAL;
    currImageC.gridx = 0;
    currImageC.gridy = 0;
    currImageC.weightx = 0.0;
    this.currImagePanel.add(this.currImageScrollPane, currImageC);
    currImageC.fill = GridBagConstraints.HORIZONTAL;
    currImageC.gridx = 1;
    currImageC.gridy = 0;
    currImageC.weightx = 0.5;
    currImageC.anchor = GridBagConstraints.EAST;
    this.currImagePanel.add(currHistoPanel, currImageC);

    // add the image panel to the main panel
    this.imagesPanel = new JPanel();
    this.imagesPanel.setLayout(new GridLayout(0, 3));
    this.imagesPanel.setBorder(new TitledBorder("All Images"));
    mainC.fill = GridBagConstraints.HORIZONTAL;
    mainC.gridx = 0;
    mainC.gridy = 2;
    mainC.ipady = 0;
    mainC.weighty = 1.0;
    this.mainPanel.add(this.imagesPanel, mainC);
  }

  /**
   * Initializes the frame in the gui window.
   */
  @Override
  public void initialiseFrame() {
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.frame.setVisible(true);
  }

  /**
   * Renders the view of the gui window.
   */
  @Override
  public void renderView() {
    for (Map.Entry<String, Image> s : this.model.entrySet()) {
      if (!this.imageNamesAdded.contains(s.getKey())) {
        this.imageNamesToBeAdded.addFirst(s.getKey());
        this.imagesToBeAdded.addFirst(s.getValue().createBufferedImage());
      }
    }

    PanelDecorator imageDecorator = new PanelDecoratorImpl(this.imagesPanel);
    PanelDecorator radioButtonDecorator = new PanelDecoratorImpl(this.imageCheckBoxesPanel);
    while (!this.imagesToBeAdded.isEmpty()) {
      String name =  this.imageNamesToBeAdded.removeFirst();
      BufferedImage imageAdded = this.imagesToBeAdded.removeFirst();
      imageDecorator = new ImageDecorator(imageDecorator, name, imageAdded);
      radioButtonDecorator = new ImageNameRadioDecorator(radioButtonDecorator, name,
              this.actionListener);
      this.imageNamesAdded.addFirst(name);
      this.currImageName = this.imageNamesAdded.getFirst();
    }
    imageDecorator.decorate(new ArrayList<Component>());
    radioButtonDecorator.decorate(new ArrayList<Component>());

    Image image = this.model.getOrDefault(this.currImageName, null);

    if (image != null) {
      this.currImageIcon.setImage(image.createBufferedImage());
      this.currImagePanel.setBorder(new TitledBorder("Current Image: " + this.currImageName));
      this.currHistoIcon.setImage(this.displayHistogramImage(image));
      this.currImageScrollPane.setPreferredSize(new Dimension(800, 400));
      this.selectRadioButton(this.currImageName);
    }

    this.controlPanel.revalidate();
    this.imagesPanel.revalidate();
    this.mainPanel.revalidate();
  }

  // displays the image of the histogram of this image
  private BufferedImage displayHistogramImage(Image image) {
    Map<Integer, Integer> redH = image.getHistogram("red");
    Map<Integer, Integer> greenH = image.getHistogram("green");
    Map<Integer, Integer> blueH = image.getHistogram("blue");
    Map<Integer, Integer> intensityH = image.getHistogram("intensity");

    int maxFrequency = 0;
    for (int i = 0; i < 256; i++) {
      if (redH.get(i) > maxFrequency) {
        maxFrequency = redH.get(i);
      }
      if (greenH.get(i) > maxFrequency) {
        maxFrequency = greenH.get(i);
      }
      if (blueH.get(i) > maxFrequency) {
        maxFrequency = blueH.get(i);
      }
      if (intensityH.get(i) > maxFrequency) {
        maxFrequency = intensityH.get(i);
      }
    }

    int width = 150;
    int height = 100;

    double xScale = (double) (width - 1) / 256.0;
    double yScale = (double) (height - 1) / (double) maxFrequency;

    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    BufferedImage redHistogram = this.makeHistogram(redH, width, height, xScale, yScale,
            0xCEFF0000);
    BufferedImage greenHistogram = this.makeHistogram(greenH, width, height, xScale, yScale,
            0xCE00FF00);
    BufferedImage blueHistogram = this.makeHistogram(blueH, width, height, xScale, yScale,
            0xCE0000FF);
    BufferedImage intensityHistogram = this.makeHistogram(intensityH, width, height, xScale, yScale,
            0xCE000000);

    Graphics2D g = img.createGraphics();

    g.setComposite(AlphaComposite.Clear);
    g.fillRect(0,0, width, height);

    g.setComposite(AlphaComposite.SrcOver);
    g.drawImage(redHistogram, 0, 0, null);

    g.setComposite(AlphaComposite.SrcOver);
    g.drawImage(greenHistogram, 0, 0, null);

    g.setComposite(AlphaComposite.SrcOver);
    g.drawImage(blueHistogram, 0, 0, null);

    g.setComposite(AlphaComposite.SrcOver);
    g.drawImage(intensityHistogram, 0, 0, null);

    g.dispose();

    BufferedImage finalImg = new BufferedImage(480, 320, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = finalImg.createGraphics();
    graphics.drawImage(img.getScaledInstance(480, 320, BufferedImage.TYPE_INT_ARGB),
            0, 0, null);
    graphics.dispose();

    return finalImg;
  }

  // makes the histogram of the given color
  private BufferedImage makeHistogram(Map<Integer, Integer> values, int width, int height,
                                      double xScale, double yScale, int color) {

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    for (int v = 0; v < 256; v++) {
      int x = (int) (v * xScale);
      int y = (int) (values.get(v) * yScale);
      for (int i = 1; i <= y; i++) {
        image.setRGB(x, height - 1 - i, color);
      }
    }

    return image;

  }

  /**
   * Assigns the extension to this image.
   *
   * @return the extension type
   * @throws IllegalArgumentException if the operation is cancelled
   */
  @Override
  public String chooseImagePath() throws IllegalStateException {
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Images", "jpg", "ppm", "png", "bmp");
    final JFileChooser fChooser = new JFileChooser(".");
    fChooser.setFileFilter(filter);
    int retValue = fChooser.showOpenDialog(this.frame);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      return f.getPath();
    }
    throw new IllegalStateException("Operation cancelled");
  }

  /**
   * Gets the name of the image from the user.
   * @return the name of the image
   */
  @Override
  public String inputImageName() {
    String result = JOptionPane.showInputDialog(this.mainPanel,
            "Enter the name of the image:", "", JOptionPane.PLAIN_MESSAGE);

    if (result == null) {
      return null;
    }

    boolean isValid = (!this.model.containsKey(result) && this.isValidImageName(result));

    while (!isValid && !result.equals("")) {
      String message = "Try again!";
      if (this.model.containsKey(result)) {
        message = "That image name already exists! Enter the name of the image:";
      }
      if (!this.isValidImageName(result)) {
        message = "That image name is invalid! Valid names do not have spaces or special " +
                "characters. Enter the name of the image:";
      }
      result = JOptionPane.showInputDialog(this.mainPanel, message, "",
              JOptionPane.PLAIN_MESSAGE);
      if (result == null) {
        return null;
      }
      isValid = (!this.model.containsKey(result) && this.isValidImageName(result));
    }

    return result;
  }

  // checks if the given name is valid without any special characters
  private boolean isValidImageName(String name) {
    ArrayList<String> characters = new ArrayList<String>(Arrays.asList(".", "/", ",", " ", "!",
            "@", "#", "$", "%", "&", "*", "?", "<", ">"));
    for (String invalid : characters) {
      if (name.contains(invalid)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Gets the value to brighten the image from the user.
   * @return the value to brighten image by
   */
  @Override
  public int inputBrightenIncrement() {
    String result = JOptionPane.showInputDialog(this.mainPanel,
            "Enter the brighten/darken increment:", "", JOptionPane.PLAIN_MESSAGE);

    if (result == null) {
      return 0;
    }

    int increment = 0;
    boolean validIncrement = false;
    try {
      increment = Integer.parseInt(result);
      validIncrement = true;
    } catch (Exception e) {
      validIncrement = false;
    }
    boolean isValid = (result != null && validIncrement);

    while (!isValid && !(result.equals(""))) {
      result = JOptionPane.showInputDialog(this.mainPanel,
              "Please enter a valid integer increment:", "",
              JOptionPane.PLAIN_MESSAGE);
      try {
        increment = Integer.parseInt(result);
        validIncrement = true;
      } catch (Exception e) {
        validIncrement = false;
      }
      isValid = (result != null && validIncrement);
    }

    return increment;
  }

  /**
   * Displays the given message.
   *
   * @param message the given message
   */
  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this.frame, message, "Error!",
            JOptionPane.WARNING_MESSAGE);
  }

  /**
   * Sets this image to be the given imageName.
   *
   * @param imageName the name of the image to set as this image
   */
  @Override
  public void setCurrImage(String imageName) {
    this.currImageName = imageName;
    this.renderView();
  }

  /**
   * Selects the radio button clicked on by the user and deselects all others.
   *
   * @param selected the name of the selected radio button
   */
  @Override
  public void selectRadioButton(String selected) {
    for (Component c : this.imageCheckBoxesPanel.getComponents()) {
      if (c instanceof JRadioButton) {
        JRadioButton radioButton = (JRadioButton) c;
        if (radioButton.getActionCommand().equals(selected)) {
          radioButton.setSelected(true);
        } else {
          radioButton.setSelected(false);
        }
      }
    }
  }

  /**
   * Chooses the appropriate file path to save this image.
   *
   * @return the extension
   * @throws IllegalStateException if the operation is cancelled
   */
  @Override
  public String chooseFilePath() throws IllegalStateException {
    final JFileChooser fChooser = new JFileChooser(".");
    fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int retValue = fChooser.showOpenDialog(this.frame);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      if (fChooser.getSelectedFile().isDirectory()) {
        String path = fChooser.getSelectedFile().toString();
        if (!path.contains("./")) {
          return null;
        }
        return path.substring(path.indexOf(".") + 2);
      }
    }
    throw new IllegalStateException("Operation cancelled");
  }

  /**
   * Asks the user to input the name of the file to save it.
   *
   * @return the name of the file
   * @throws IllegalStateException if the name of file already exists or doesn't fit the naming
   *                               criteria
   */
  @Override
  public String inputFileName() throws IllegalStateException {
    String result = JOptionPane.showInputDialog(this.mainPanel,
            "Enter the file name of the image. Supported file formats are .png, .bmp, " +
                    ".jpg, and .ppm. For example: exampleImage.ppm ", "",
            JOptionPane.PLAIN_MESSAGE);

    if (result == null) {
      return null;
    }

    boolean isValid = this.isValidFileName(result);

    while (!isValid && !result.equals("")) {
      String message = "That file name is invalid! Valid names do not have spaces or special " +
                "characters and \n must have .ppm, .png, .jpg, or .bmp at the end. Enter the " +
                "name of the image:";
      result = JOptionPane.showInputDialog(this.mainPanel, message, "",
              JOptionPane.PLAIN_MESSAGE);
      if (result == null) {
        return null;
      }
      isValid = this.isValidFileName(result);
    }

    return result;
  }

  /**
   * Displays a message once the image has been saved.
   *
   * @param message the given message to display
   */
  @Override
  public void displaySuccessMessage(String message) {
    JOptionPane.showMessageDialog(this.frame, message, "Success!",
            JOptionPane.PLAIN_MESSAGE);
  }

  // checks if the given file name is valid
  private boolean isValidFileName(String name) {
    String[] filenameParts = name.split("\\.");
    if (filenameParts.length != 2) {
      return false;
    }
    String imageName = filenameParts[0];
    String extension = filenameParts[1];
    if (!this.isValidImageName(imageName)) {
      return false;
    }
    return (extension.equals("png") || extension.equals("jpg") || extension.equals("ppm")
            || extension.equals("bmp"));
  }
}
