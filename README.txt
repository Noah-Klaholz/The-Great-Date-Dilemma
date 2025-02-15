The Game starts out by default with the Background image at path in CONST.backgroundImagePath.

The Story can be written by adjusting the Story.txt file and adding the necessary resources to the Media file.
Syntax:
Switching Background:
    Background:Media/YOUR_FILE_NAME
Giving the player a Choice:
    Choice:CHOICE1#CHOICE2;JUMP_TO1#JUMP_TO2
-> CHOICE# = Text that should be displayed on the choice Button
-> JUMP_TO# = Int of the line that it should jump to if choice# is made
Showing text:
    NAME_OF_THE_CHARACTER:TEXT
Adding an Achievement:
    Achievement:ACHIEVEMENT_NUMBER
-> ACHIEVEMENT_NUMBER can be looked up / new achievements added in achievements.txt file
Showing the Ending screen:
    Ending:
Note: Proper endings / cutscenes should be written using the other features listed here.
Jump to Line:
    Jump:LINE#
