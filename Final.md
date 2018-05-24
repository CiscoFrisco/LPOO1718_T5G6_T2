# Final Check-Point

## Project Setup

The project was developed with Android Studio.
After cloning the repo, building with gradle should be sufficient to get everything to work.
Just make sure not to update gradle even if the IDE asks for it!

## App Setup

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

## Major Difficulties

Since this was our first time working with Android and libgdx, we had a touch time learning
how to use it and its tools. The first big issue was setting up the git repository, since
there were some files that should be local (like local.properties), and due to that, we couldn't
test the application in both computers. That was quickly fixed though.
Then, we had to learn how to use Android Studio since we used Eclipse previously.
Finally, the big issue came with libgdx. In our opinion it wasn't easy learning it, and often times
we had to rely on external tutorials to solve our problems.
The major issues were learning how to use world and screen coordinates, how to use touchDragged
in order to move the handles and impact the puck, and how to setup different difficulties for
the bot, since we tried to make it as 'human' as possible.

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

- Menus and Preferences: [Menus](https://www.gamedevelopment.blog/full-libgdx-game-tutorial-project-setup/)
- First setup: [First setup](http://williammora.com/a-running-game-with-libgdx-part-1)
- Database: [Database](http://www.sqlitetutorial.net/sqlite-java/)
- Recurring questions: libgdx wiki, stack overflow and similar sites

## User manual