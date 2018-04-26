# LPOO1718_T5G6_T2
Second project for FEUP MIEIC's curricular unit LPOO: AirHockey on Android.

## UML

### Package Diagram and Class Diagram

### Sequence/Activity/State Diagram

## Design Patterns

## GUI Design

### Main functionalities
The GUI can be divided in two parts, in and off game.
Off game state is represented by the menu's, which may include the following:

- Main Menu
  - New Game
    - SinglePlayer
    - MultiPlayer
  - Settings
    - Sound
  - About
  
In game, the user will have access to the current score, and a pause option (only in singleplayer mode, in multiplayer this feature may be added but requires both users to press the button in order for it to work) that may allow the user to forfeit, or change the game's options, such like puck speed or bounciness.

### Mock ups

## Test Design

- Menu Navigation tests: The purpose of this tests is to check whether or not the buttons do their expected behaviour (i.e. upon touching the Settings Button, the user should be redirected to the Settings Menu).

- Test Settings: This tests check if the changes in the game settings are applied to the game (i.e. if the user changes the difficulty
from easy to hard, the game should be harder to win).

- Test Movements: Test in-game movements of our objects according to their attributes (i.e. check if the puck moves upwards when it has a positive linear velocity in the y axis).

- Test Collisions: Check if the objects collide with each other.

- Test Score/ End Game: Check if the score updates when a player scores a goal and if the game ends (win or loss) when a player reaches the max score. 
