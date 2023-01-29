# How to use the program

Our image processing application runs on 3 different types of user inputs:

- Script based inputs
- Typed text inputs
- GUI based inputs

The script based inputs takes in a `.txt` file and runs the commands in the file.
The typed text inputs runs in the terminal using `System.in` and executes the
commands as the user types them.
The GUI based inputs opens up a graphical user interface with visual elements
for the user to interact with.

The commands supported and how to use them are explained in more detail below 
(after the `main()` method is explained).

## The `main()` method

In order to run the program, the `main()` method expects either 0 arguments, 1 argument, or
2 arguments **only**. 

If no arguments are given, then the program runs on GUI based inputs.

If one argument is given, it must be `-text` and the program will run on `System.in` inputs.

If two arguments are given, the first argument myst be `-file` and the second
argument must be the `filename` with the path of the text based script that has the inputs
stored as a `.txt` file.


## The `GUI` edition

In order to use the GUI:

1. Open an image using the `Open image` button.
2. A file chooser will open. Make sure to select an image **within the given directory** (do not 
go back).
3. After selecting the image, you will have to give it a name. The name cannot have any
special characters or spaces.
4. After clicking okay, the image will load. You will be able to see the image name with a radio
button next to it, the image itself, and the histogram of the four components.
5. In order to transform the image, choose a transformation from the dropdown.
6. Then, click `Transform Image`. You will have to name the new image.
7. The `brighten` command requires an increment which is inputted through a dialog box after the
   user selects `brighten` command and clicks `Transform Image`.
8. Once the new image has been named, the selected image will be transformed and the old image as well
as the new transformed image will be visible (if you scroll down a little).
9. In our application, even though many images can be loaded, the selected image is the one that 
is always transformed and saved. And, the selected image's histogram is always shown.
10. The radio buttons at in the top panel can be used to toggle between images in the application.
11. If the image is too big, there are scrollbars around the image.
12. After transforming images, you can save an image by clicking the `Save image` button.
13. The file chooser will open, and you will have to select a folder **within the current directory**.
14. After clicking save, you will be prompted for the filename of the image.
15. Then, the image will be saved and a message will be displayed to the user accordingly.


## The `non-GUI` edition

The program supports the following commands:

- `load filepath image-name` - loads the image given the filepath
- `brighten increment image-name dest-image-name` - brightens or darkens the image given the 
increment
- `vertical-flip image-name dest-image-name` - vertically flips the image
- `horizontal-flip image-name dest-image-name` - horizontally flips the image
- `red-component image-name dest-image-name` - converts the image to greyscale using the red
component of the image 
- `green-component image-name dest-image-name` - converts the image to greyscale using the green 
component of the image
- `blue-component image-name dest-image-name` - converts the image to greyscale using the blue 
component of the image
- `value-component image-name dest-image-name` - converts the image to greyscale using the value 
component of the image
- `intensity-component image-name dest-image-name` - converts the image to greyscale using the 
intensity component of the image
- `luma-component image-name dest-image-name` - converts the image to greyscale using the luma 
component of the image
- `blur image-name dest-image-name` - performs a gaussian blur on the image
- `sharpen image-name dest-image-name` - sharpens the image
- `greyscale image-name dest-image-name` - converts the image to greyscale
- `sepia image-name dest-image-name` - adds a sepia tone to the image
- `save filepath image-name` - saves the image at the specified file path

where:

- `filepath` is the file path of the image to load or save
- `image-name` is the name of the image
- `dest-image-name` is the new name of the changed image
- `increment` is the integer value used to brighten or darken an image (a 
positive value brightens while a negative value darkens)


When using `System.in` inputs:
Each command should be on a new line. The controller prompts the user for
inputs when `System.in` is used. If at any point during the `System.in` inputs, the user wishes to 
quit, they can enter `"q"` or `"quit"`, and the program will terminate. If at any point the image to 
enhance does not exist, or the filepath doesn't exist, the user is notified with a message and 
the program ends.

When using the script based inputs:
Comments in the `.txt` are written using a `#` at the start. All other lines
in the `.txt` file are read as commands. Each command should be on a new line.


### Example of a valid set of commands for script based inputs

    # load puppies.ppm and call is 'puppies'
    load res/images/puppies.ppm puppies
    
    # brighten 'puppies' and call it 'puppies-brighter'
    brighten 20 puppies puppies-brighter
    
    # sharpen 'puppies-brighter' and call it 'puppies-edited'
    sharpen puppies-brighter puppies-edited
    
    # horizontally flip 'puppies-edited' and call it 'puppies-final'
    horizontal-flip puppies-edited puppies-final

    # save 'puppies-final' 
    save res/images/puppies-final.png puppies-final