package model.utility;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.IPixel;
import model.Image;
import model.Pixel;
import model.SimpleImage;

/**
 * Class that represents the operations required to load and save any kind of image.
 */
public class ImageUtil {

  /**
   * Loads any kind of image.
   *
   * @param filename the filename of the image to load into the program.
   * @return the image.
   * @throws IllegalArgumentException if the image is not found.
   */
  public Image loadImage(String filename) throws IllegalArgumentException {

    String[] filenameParts = filename.split("\\.");
    String fileType = filenameParts[filenameParts.length - 1];

    if (fileType.equals("ppm")) {
      return this.loadPPM(filename);
    } else {
      return this.loadImg(filename);
    }
  }

  /**
   * Saves the image with the given filename.
   *
   * @param filename the given filename.
   * @param width the width of the image.
   * @param height the height of the image.
   * @param pixels the pixels of the image.
   * @throws IllegalArgumentException if the file cannot be saved.
   */
  public void saveImage(String filename, int width, int height, IPixel[][] pixels)
          throws IllegalArgumentException {

    String filenameParts = filename.substring(filename.indexOf("."));
    String fileType = filenameParts.substring(1);

    if (fileType.equals("ppm")) {
      this.savePPM(filename, width, height, pixels);
    } else {
      this.saveImg(filename, fileType, width, height, pixels);
    }
  }

  /**
   * Loads a PPM image.
   *
   * @param filename the filename of the image to load into the program.
   * @return the image.
   * @throws IllegalArgumentException if the image is not found.
   */
  private Image loadPPM(String filename) throws IllegalArgumentException {

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }

    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    int bits = 8;

    IPixel[][] pixels = new IPixel[width][height];

    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        int red = sc.nextInt();
        int green = sc.nextInt();
        int blue = sc.nextInt();
        pixels[c][r] = new Pixel(bits, red, green, blue);
      }
    }

    return new SimpleImage(width, height, pixels);
  }

  /**
   * Loads any kind of image except for PPM.
   *
   * @param filename the filename of the image to load into the program.
   * @return the image.
   * @throws IllegalArgumentException if the image is not found.
   */
  private Image loadImg(String filename) {

    File file = null;
    BufferedImage img = null;
    try {
      file = new File(filename);
      img = ImageIO.read(file);
    } catch (Exception e) {
      throw new IllegalArgumentException("Not able to read " + filename + " file!");
    }

    int width = img.getWidth();
    int height = img.getHeight();
    int bits = img.getColorModel().getPixelSize() / img.getColorModel().getNumComponents();

    IPixel[][] pixels = new IPixel[width][height];
    for (int r = 0; r < width; r++) {
      for (int c = 0; c < height; c++) {
        Color color = new Color(img.getRGB(r, c));
        pixels[r][c] = new Pixel(bits, color.getRed(), color.getGreen(), color.getBlue());
      }
    }

    return new SimpleImage(width, height, pixels);
  }

  /**
   * Saves a PPM image with the given filename.
   *
   * @param filename the given filename.
   * @param width the width of the image.
   * @param height the height of the image.
   * @param pixels the pixels of the image.
   * @throws IllegalArgumentException if the file cannot be saved.
   */
  private void savePPM(String filename, int width, int height, IPixel[][] pixels)
          throws IllegalArgumentException {

    FileOutputStream out = null;

    try {
      out = new FileOutputStream(filename);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    try {
      out.write(this.writeString("P3"));
      out.write(this.writeString(String.valueOf(width)));
      out.write(this.writeString(String.valueOf(height)));
      out.write(this.writeString(String.valueOf(255)));

      for (int r = 0; r < height; r++) {
        for (int c = 0; c < width; c++) {
          IPixel pixel = pixels[c][r];
          out.write(this.writeString(String.valueOf(pixel.getChannelValue("red"))));
          out.write(this.writeString(String.valueOf(pixel.getChannelValue("green"))));
          out.write(this.writeString(String.valueOf(pixel.getChannelValue("blue"))));
        }
      }

      out.close();

    } catch (Exception e) {
      throw new IllegalArgumentException("Unable to write to file!");
    }
  }

  /**
   * Converts the given string into a byte array.
   *
   * @param str the given string.
   * @return the array of the string.
   */
  private byte[] writeString(String str) {
    String newString = str + System.lineSeparator();
    return newString.getBytes();
  }

  /**
   * Saves the image with the given filename.
   *
   * @param filename the given filename.
   * @param width the width of the image.
   * @param height the height of the image.
   * @param pixels the pixels of the image.
   * @throws IllegalArgumentException if the file cannot be saved.
   */
  private void saveImg(String filename, String extension, int width, int height, IPixel[][] pixels)
          throws IllegalArgumentException {

    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int r = 0; r < width; r++) {
      for (int c = 0; c < height; c++) {
        IPixel pixel = pixels[r][c];
        int red = pixel.getChannelValue("red");
        int green = pixel.getChannelValue("green");
        int blue = pixel.getChannelValue("blue");
        Color color = new Color(red, green, blue);
        img.setRGB(r, c, color.getRGB());
      }
    }

    try {
      File file = new File(filename);
      ImageIO.write(img, extension, file);
    } catch (Exception e) {
      throw new IllegalArgumentException("Unable to save " + filename + " file!");
    }
  }

}
