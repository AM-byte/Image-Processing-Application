# Image Processing

The Image Processing Application can be explained in
three parts, the model, the view, and the controller.

## The Model

In our project, an image is represented using the `Image`
and `IPixel` interfaces.

The `IPixel` interface has the following methods:

- `int getChannelValue(String channel)` to get the value of
  a given channel
- `int getValue()` to get the maximum value of the
  channels
- `int getIntensity()` to get the intensity
  (average value) of the channels
- `int getLuma()` to get the luma of the pixel
- `IPixel setBrightness(int increment)` to increase the
  brightness of the pixel by the given value
- `int getMaxValue()` to get the max possible value of the pixel

The `Image` interface has the following methods:

- `Image brightenImage(int increment)` to increase the
  brightness of the image
- `Image flipVertical()` to flip the image vertically
- `Image flipHorizontal()` to flip the image horizontally
- `Image convertGreyscale(String method)` to convert the
  image to greyscale given the component to use
- `Image applyFilter(IFilter filter)` to apply a filter to this image given the filter kernel
- `Image applyTransformation(ITransformation transformation)` to apply a color transformation to
this image given the transformation object
- `int getWidth()` to get the width of the image
- `int getHeight()` to get the height of the image
- `IPixel[][] writePixels()` to return the pixel values of image. Each inner list contains the rgb values of a pixel.

**NEW METHODS ADDED FOR ASSIGNMENT 6**
 
- `BufferedImage createBufferedImage()` to create a buffered image of this image.
- `Map<Integer, Integer> getHistogram(String component)` to get the histogram of a given component of this image.

We then implemented these interfaces in classes
called `Pixel` and `SimpleImage`.

### The View

For the text based view, we had a `View` interface which had the
`void renderMessage(String message)` method. In our `SimpleView`
class which implemented the `View` interface, the
`renderMessage(String message)` function appends
the given message to its `Appendable` object.

This `Appendable` object could either be `System.out`
or any provided `Appendable` through the constructor
of `SimpleView()`

This allowed us to display messages to the user, which
is an important part of our program.

For the gui based view, we had a `GuiFrameView` interface which had
the methods:

- `void initialiseFrame()` to initialize the frame in the gui window
- `void renderView()` to render the view in the gui window with the updated model
- `String chooseImagePath()` to choose the file path of the image to load
- `String inputImageName()` to get the name of the image from the user
- `int inputBrightenIncrement()` to get the value to brighten the image
- `void displayMessage()` to display a given message to the user
- `void setCurrImage()` to set the current image to be the given image name
- `void selectRadioButton(String selected)` to select the given radio button and deselect all others
- `String chooseFilePath()` to choose the appropriate file path to save the image
- `String inputFileName()` to ask the user to enter the name of file to save it
- `void displaySuccessMessage()` to display a success message once the image has been saved

We then implemented this interface in a class called `GuiFrameViewImpl`.

`GuiFrameViewImpl` has one constructor which takes in the model in the form of
a map of `Image`, a list of command names, and the action listener.

To include the features of populating a panel with components we used the decorator pattern. 
We made an interface called `PanelDecorator` which had the following methods:

- `void decorate(ArrayList<Component> panels)` to add the given components to the panel.

We then implemented the interface in the classes `PanelDecoratorImpl`, `ImageDecorator`, 
and `ImageNameRadioDecorator`.

`PanelDecoratorImpl` has one constructor which initializes the panel with the given panel.
When the `decorate()` method of this class is called, it adds all the components in the given 
`ArrayList<Component>` to the given panel that was given upon initialization.

`ImageDecorator` has one constructor which takes in a `PanelDecorator` object, the name of the image,
and the `BufferedImage` itself. When the `decorate()` method of this class is called, the 
`BufferedImage` is drawn using a `JLabel` and this component is added to the `ArrayList<Component>`.
The method then recursively calls the next `decorate()` method with the updated list of `Component`.

`ImageNameRadioDecorator` has one constructor which takes in a panel decorator object, the name of the image, 
and an `ActionListener` object that will listen for radio button clicks. When the `decorate()` method of this
class is called, a `JRadioButton` is created and added to the `ArrayList<Component>`. Again, the
method recursively calls the decorate method with the updated list of `Component`.

Implementing the view in such a way allowed us to display the entire program in a gui window by using
the various components of the Java Swing Gui Builder. To implement this new gui view we did not need to
change any part of our previous view interface/class and they both function independently.

## The Controller

To make a controller that would use the gui view instead of the text based view, we use
an adapter that would allow us to use the previous controller in the new
controller without changing any parts of the previous controller.

For the adapter we made an interface called `BetterController` that
extended the previous `IController` interface which had the method:

- `void resetReadable(Readable in)` to reset the readable to the given readable
                                    at any time in the gui controller.

We then implemented this in a class called `BetterControllerImpl` that extends `ProController`
and implements `BetterController`. This class has one constructor which takes in the model 
as a `Map` of multiple images and an Appendable log which throws an `IllegalArgumentException` if
the model or the log are null.

To support the features offered by the gui view of the program we made
an interface called `GuiController`. This interface had the method:

- `void runApplication()` to run the program

We then implemented this interface in a class called `SimpleGuiController`
which also implemented the interface `ActionListener`. This class used the
adapter `BetterControllerImpl` through composition and as a result we were
able to reset the readable at any time during the program. After resetting
the readable, we could then call the `process()` method in the `BetterController`
(which is inherited from the `SimpleController`). This allowed us,
not only, to use the previous controller, but we didn't have to make any changes
in it either.

### Media

All images used in this assignment such as `puppies.png`
and `testImg.ppm` are our own images and have not been taken
of the internet. `puppies.ppm` was taken using a camera by us
and `testingImg.ppm` was created by us for testing purposes.

The images can be found in the `res/` folder of the project.
