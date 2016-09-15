Game Analysis
===================

Rachel Bransom
NetID: rnb11
September 2016

----------


Project Journal
-------------

**Time Review**
I started writing the code for my game on Friday, 4th September and finished Sunday 11th September. In this time I spent roughly 25 hours working on the game.

I spent much of this time debugging, especially as I was unfamiliar with javafx and had great difficulting linking every screen of the game together. I also spent a good amount of time refactoring (approx 8 hours), because I had too much duplicate code that was difficult to consolidate. 

I typically worked on my game in large chunks (of 4-5+ hours) and pushed it afterwards. ***

**Commits**
I committed code 11 times during the project, each with fairly significant changes. The commits were for added features and refactoring - I didn't think to commit after debugging which would have been wise. I think the commits I did make were purposeful and told my project's story, but I would say I didn't commit enough. There were times when I would add multiple large pieces of code and the commits didn't reflect everything that changed in my code. Next time I would definitely be more conscious to commit multiple times a session, because some of my commit sizes were too large for someone else to review before allowing it into the repository. I'm glad I know this now before the group projects start.

**Conclusions**
I estimated fairly accurately the size of the project - I essentially cleared the week to work on this at least multiple hours every day. This meant I was able to work on it throughout the week and digest debugging tools or ideas I had and return to the code later to implement them.

My game class required the most editing because the role it was playing in the game changed throughout the week. At first it acted as a level manager but I changed this as I understood javafx better. Now it sets up the game, and did much of what my main class did before.

To be a better designer, I think I should write good code from the start - not write bad code and refactor because, which is largely what I did in this project. Writing bad code and then refactoring creates problems and it would be faster to spend time thinking about how to make the code as well designed as possible before writing it. I should definitely keep going to TA hours and talking over problems with the TAs. I have found those sessions to be incredibly helpful. I also think I should start planning more. For the plan, I should have thought about implementation more than I did.

If I could work on one part of my project I would make the two classes, Level One and Level Two more similar in their structure, so that the Level class controlled more of how the game functions.

Design Review
-------------
**Status**
My code is generally very consistent, because each file's logic follows similar patterns. For example, most classes have an init method, a display method, and a set method. My naming conventions also stay fairly consistent, as they are all very descriptive - although I'm not sure if sometimes they were too long (e.g. *levelOneEnemyCreation* or *TRANSITION_MILLISECOND_DURATION*).

I would say my code is generally very readable, although I'm not as familiar with coding conventions as I would like to be, which may have reduced the readability. For example,  I named my Parallel Transition parallelTransition, which I found to be most helpful, although it may be confusing to some. I noticed how little I wanted to comment, compared to usual, because I felt my code was more readable than usual.

I tried to make my dependencies fairly clear; the only 'back channel' dependencies I used were Main.sizeOfScreen(), Main.getMyGame, enemy.getRect, and player.getPlayer(). The fact I had a getter for the game, Main.getMyGame, was one of the worse smells in my code. However, I struggled with this for a while and wasn't able to avoid it with my current understanding. I needed this getter because there was a method in Game that changed the screens from the flash screen, to level one, to level two. It threaded all the parts of my game together, but I didn't feel it was appropriate to make it a superclass, as that was what Level, the level manager, was for.

In the Level class I have code to change the screen to tell the user it's game over. I have four methods; setScreenToGameOver, displayGameOverText, displayGameOverButtons, exitOutButtonInit. These four methods set up the game over text, set the gameOver variable to true, and display a 'play again' button. I use this in both levels so this is a superclass to the two levels. They reference this when there is a collision with an enemy and the game is over.

**Design**
My code is largely organized into two levels, as my first and second level are very different in structure - one the player is floating around and the other there is gravity and it walks on the ground. My sprite class is a superclass to my Coin, Enemy, Player and LevelTwoEndPoint. Everything that is specific to a certain character remains within it's own class and everything that's applicable to all, such as getSpeed, is in the sprite class. The PlayersFish is the same main character in both levels.

The main class has very little, because it then calls the game class which sets the entire screen and game up. The game class changes the screens and tracks the level, so it's the thread that holds all of the screens of my game together.

To add a new level to the game,  you would create a class extending Level, and add another outcome in the changeScreens method in the game class, so that after level two ends, the game automatically changes to level 3.

I designed my code so that I had one stage, one scene, and one root. I passed the root around to the screen initializers (e.g. splashInit), which I was unsure about. However, there was no other way to connect all of the aspects of each level to the screen that I was aware of during the creation of the game. I also had the changeScreens method in game, so I had a getter for my game in main, which I didn't think was the best code possible - again I only chose this because I wasn't aware of any other alternatives after struggling with it for a couple days.

My game was a 2-D Scrolling Platformer game and to do this I used Translate Transition and Parallel Transition. I created two copies of the background image and set each to be a translate transition, setting their fromX to be 0 and their toX to the negative width of the image. Then I set up a parallel transition to loop this transition indefinitely.

    private void backgroundInit(){
        image = new Image(getClass().getClassLoader().getResourceAsStream("SeaFloorBackground.png"));
        image2 = new Image(getClass().getClassLoader().getResourceAsStream("SeaFloorBackground.png"));
        levelOneBackground1 = new ImageView(image);
        levelOneBackground2 = new ImageView(image2);
        levelOneRoot.getChildren().addAll(levelOneBackground1, levelOneBackground2);
        levelOneBackground2.setX(image.getWidth());     
    }

    private void makeBackgroundScroll(final int width) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(TRANSITION_MILLISECOND_DURATION), levelOneBackground1);
        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(TRANSITION_MILLISECOND_DURATION), levelOneBackground2);
        
        translateTransitionInit(translateTransition);
        translateTransitionInit(translateTransition2);

        parallelTransition = new ParallelTransition(translateTransition2, translateTransition);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();
    }

My game also has characters that move about the game area and if the main character collides with them, the game is over. In level one I did this by spawning enemies every 50th step and setting their velocities, sizes and colors to be random. To check for collisions, in my step function I checked the the shape of each enemy and the shape of the player had 0 intersect in every frame, as shown below:

     public boolean collision(Shape mainPlayer) {
        Shape intersect = Shape.intersect((Shape) this.getRect(), mainPlayer);
        return (intersect.getBoundsInLocal().getWidth() != -1);
    }

**Alternate Designs**
As I have mentioned in the previous part of the analysis, I was reluctant to pass around my root Group and also to have a getter for my game object in the main.

For passing the root around, I could have instead made root an instance variable of a new class, with a getter and setter. This would have required another file, and perhaps more complex code. On the other hand, passing the root around seems insecure, because it's such a large and important variable. 

I would have preferred to have made root an instance variable of a new class, because I think that would have been easier to read and follow the logic of. It's harder now to trace back to which root each level is using and remember to change it every time. 

For the game getter, I could have had a listener in game that checked when it was time to go onto the next level/screen. This would have been much more secure - not having to pass game around. However, it would have added a lot of extra code to be carried out in every frame, and potentially slowed the game down.

I preferred the game getter at the time because it was easier to implement and made things simple when I was first connecting the levels in my game.

The three most important bugs that remain in the game's implementation are:

1. The occasional freezing of the game screen, probably due to badly written code somewhere.

2. Sometimes, inexplicably, the game will say game over even when there was no collision with an enemy. Perhaps this is something to due with a miscommunication between the player object and the shape it contains.

3. There are cases when the enemies, because they're all moving at different speeds, end up in the same x position, creating a wall in front of the main character. Then the characters loses because passing them is impossible.