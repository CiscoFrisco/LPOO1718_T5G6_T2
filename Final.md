# Final Check-Point

## Project Setup

The project was developed with Android Studio.
After cloning the repo, building with gradle should be sufficient to get everything to work.
Just make sure not to update gradle even if the IDE asks for it!

## App Setup

Desktop: Run the provided .jar on both Windows and Linux, with a double-click or ENTER. Obviously, the latest
JRE is recommended.

Android: Run the provided .apk, after allowing installation of external apps.

## UML

### Package and Class Diagram

### State Diagram

## Design Patterns

- Model-View-Controller

    Following professor André Restivo's example, the game implemented this pattern, separating logic, info, and interface. The view package contains the screens, controller contains the bodies and powerups, and model contains the bodies' user data.

- Singleton

    GameController and GameModel are singletons, since there should be no more than one instance of them.

- Strategy

    The game has powerups like DuplicatePucks and FreezeHandle, so it was a good idea to implement
    Strategy here. This way, the game can, at a defined rate, substitute the current power up with another random one, while not needing to change the methods it needs to call.

## Relevant Design Decisions

Some of the following may have already been explained elsewhere, but we feel these are the most
import design decision in our project.

- MVC

    Initially we had another structure, but after careful thought we decided this was the best way to model the project. This way each task is split accordingly and we can more easily detect bugs, or refactor the code.

- PowerUp

    Since we needed to inject something 'original', we decided to introduce powerups. In order to implement them, we used the Strategy design pattern: the power up body also holds a reference to a power up type, which can be changed any time and holds the main logic in other classes that implement that interface. In the GameController update method, 3 methods are effectively called:
    
    - check: responsible for checking if the power up has been activated by the puck passing through it.
    - effect: makes changes on the world based on the type.
    - reset: resets the changes made by the power up.

    The main functionality is the following: 5 seconds after the beggining of the game, a power up is created and put on the world. After the puck passes through it, it is activated and the body is erased. When a goal is scored, the power up is deactivated and the timer restarts, thus 5 seconds later a neW power up comes up.

- Database

    Since there's no way to use the same database for Android and Desktop, the main game class will hold
    an object that implements the Database interface. In Android, it will be AndroidDatabase (using Android's SQLite interface), and in Desktop it will be DesktopDatabase (using jqbc library for SQLite). Thus, in each device, the user will have access to their previous results, independently of the operative system.

- Bot

    In this case we decided not to use derived classes or implement the Strategy interface. There are 3
    difficulties (Easy, Medium and Hard), and the only difference are some internal values like the distance to the puck before the bot starts to calculate trajectories, etc.

## Major Difficulties

Since this was our first time working with Android and libgdx, we had a touch time learning
how to use it and its tools. The first big issue was setting up the git repository, since
there were some files that should be local (like local.properties), and due to that, we couldn't
test the application in both computers. That was quickly fixed though.
Then, we had to learn how to use Android Studio since we used Eclipse previously.
Finally, the big issue came with libgdx. In our opinion it wasn't easy learning it, and often times
we had to rely on external tutorials to solve our problems.
The major issues were learning how to use world and screen coordinates, how to use touchDragged
in order to move the handles and impact the puck, how to setup different difficulties for
the bot, since we tried to make it as 'human' as possible, and also how to implement a database with SQLite. The problem came when we realised there was no easy solution to use the same methods for Desktop and Android, so after a few hours of searching, we decided to make two databases: one to be used on Android, and another to be used on Desktop. Both implement a common interface, so the core module is not affected.
Another big problem came when writing unit tests. In our game, Arena and View dimensions are closely related, since the arena should fill the screen. As such, when testing GameController, an error came up because graphics were not initialized. So, we had to put default values into the arena dimensions, that would later be altered by the game view, in case it existed. After fixing this mistake, we still couldn't write tests, as we got some strange compiler errors. Fortunately, after some research, we fixed the build.gradle file and we could finally start unit testing. Unfortunately, some features could not be tested, like Views and Preferences.

## Lessons Learned

Despite being a tiresome task, with this project we gained a better understanding of how to develop
a game with a user interface and involving physics.
Also it was our first time using design patterns and other refactoring methods, so it gaves a good idea of its importance.

## Overall time spent developing

Before the end of the classes, we had to focus on other projects, since their delivery point were earlier, so in average, we spent 2/3 hours per day on this game.
However, on the final week, we shift our full focus towards this project, so we can safely say that 
we worked around 8/9 hours per day.

## Work distribution

Ignoring some discrepancies, the work distribution was basically 50/50. Most of the time we tried to
work together (at FEUP for example), and when we couldn't, we decided what each of us would do.

## Sources

As explained above, we had to follow some external tutorials in order to complete some tasks. They are the following:

- [Menus and Preferences](https://www.gamedevelopment.blog/full-libgdx-game-tutorial-project-setup/)
- [First setup](http://williammora.com/a-running-game-with-libgdx-part-1)
- [Database on Desktop](http://www.sqlitetutorial.net/sqlite-java/)
- [Database on Android](https://www.tutorialspoint.com/android/android_sqlite_database.htm)
- Recurring questions: libgdx wiki, stack overflow and similar sites

## User manual

### Menus

By clicking the buttons, the user will be redirected to their respective menus. Besides New Game, all of them have a Back Button to allow them to comeback to the main menu.
In the Game screen, there is a pause button, which also allows the user to quit and comeback to the main menu.

- New Game: Starts a new AirHockeyMania game, with settings that are available in the Preferences menu.

- Preferences: Allows the user to change settings like Music, Sound and bot difficulty.

- Results: Shows (at most) the 10 latest results to the user: the game score and date.

- Exit: Exits the game.

### In-Game

Your handle is in the lower half of the game and you are not allowed to go the upper one.
In order to move it, touch and drag your phone, or move your mouse around while clicking on the screen.
In order to score, hit the puck and hope the bot lets it go into the net.
Some power ups may show up, in order to gain them, send the puck in their direction, and it will
be activated. Some of them may spoil the bot, but be careful. The bot may also get the powerup, thus affecting you!

You can pause the game by pressing the button at the top right corner. There are options to activate/deactivate music and sounds and to resume/exit the game.
