# crosses
A java interpretation of Tic Tac Toe.

## Menu
Menu (hastily hardcoded with GridBag Java layout) provides the Enter Game Button, Dispose Board Button, Exit Button, Resolution Slider, Difficulty Selection, and Update and Reset Buttons.
* Enter Game Button - Opens board
* Dispose Board Button - Deletes board if one has been created
* Exit Button - terminates program
* Resolution Slider - Screen height
* Difficulty Selection - Two-Player alternates between X & O. Easy plays by selecting a random board spot. Hard plays by using a minimax searching algorith.
* Update and Reset buttons - Update will update the X/O win ratio based on src/player_wins/wins.txt. Reset button resets src/player_wins/wins.txt.

## Board
Board presents a 3x3 playing area and 3 buttons. If easy or hard difficulty were selecteed, the player is X and the computer is O.
* Exit Button - terminates program
* Go Back Button - saves progress of the game and returns to the menu screen
* Reset Board - resets state of the board

## Win Screen
When a win or tie has occured, a dialog opens. It allows you to return to the menu or terminate the program
