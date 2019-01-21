# MKS21X-FinalProject
## UNO!
Game rules [here](https://www.unorules.com/)

This is UNO! with extra rules.

#### **Note**
- Players are not required to say "UNO!" when they have one card.
- If you draw and attempt to play a card, choosing the wrong card will automatically force you to draw.
- Playing a WILD or +4 chooses a random color.

#### **Instructions**
1. Clone this repo
```
git clone git@github.com:lilkho/MKS21X-FinalProject.git
```
2. Move into the directory of the repo
```
cd MKS21X-FinalProject/
```
3. Compile the UNO game
```
javac -cp lanterna.jar:. UNO.java
```
4. Run the UNO game
```
java -cp lanterna.jar:. UNO
```
5. For best gameplay, play in full screen.

#### **Bugs**
-

---

### **Development Log**


**1/4/19**
- Completed basic methods for Player, Rule, and UNO class
- Started working on Card class

**1/6/19**
- Changed Player class to include remove card and add card methods
- Changed UNO class to include a deck, discard pile, and randgen

**1/7/19**
- Added function to set name of player
- Added remove and add card
- Added terminal functionability.

**1/8/19**
- Changed Card's toString for testing purposes
- Bug: returning null pointer exception after adding Cards to deck

**1/9/19**
- Created method to initialize a deck
- Players are given random 7 cards and a card is placed down
- Bug fixed! Deck is working
- Bug: Deck is not printing correct cards

**1/10/19**
- Added get functions to Game class so UNO can use them
- Added printPlayers function to print by line rather than an array ArrayList
- Draw function works in all cases
- Bug fixed! Printing turn works
- Playing a card works for everything except +2
- Added setColor for when playing a wild card
- Player says UNO! if player has 1 card
- Terminal shows correct amount of players, amount of cards, player deck, and topcard.
- Terminal shows cards with corresponding colors.
- Deck is random.

**1/12/19**
- Added determineColor method to set terminal background&foreground color to match a card
- Added exceptions for starting a game in terminal
- Added modes for displaying/hide cards and drawing (which doesn't work)

**1/13/19**
- Drawing and playing cards properly work with their proper function.
- Drawing a card that is playable will be playable.
- WILD and +4 become a random color when played for now, so nothing really breaks.
- Added reset method to reset terminal colors
- Added combo and drawing the correct number of cards rather than 1
- Added setTurn to set the turn to the next player (1 if drawing, reversing, or adding; 2 if skipping)

**1/14/19**
- Tried to start implement rules, but rule name is not printing correctly
- Fixed continuous error where a matching color could be placed on any + card (given that there is a combo)
- Fixed error (expanding on the above) where you cannot place a matching color card on top of a + card (no combo)
- Added game mechanic where a reverse works like a skip when there are two players
- Added a simple winning message!
- Added option to pass your turn *_(only IFF you draw 1 card!! which we didn't figure out yet)_*

**1/15/19**
- Added a limit for rules
- Fixed error where rule printed description instead of name
- Added function to disable viewing cards of a player that does not exist.

**1/16/19**
- Added a log of actions (drawing, playing, passing)
- Perfection & camouflage rules working *_why isn't no action working i have no clue_*

**1/17/19**
- Fixed playing a card bug
- Found out why No Action (and other rules related to the deck, such as bomb card) weren't working. Fixed!
- No Action, Camouflage, No Combo, Clean Finish, Bomb Card, and Super Combo rules are all working
- Will continue testing Stacking and Perfection rules
- Added rule info to the game for reference

**1/19/19**
- Completely changed how playing works.
- Have to use a cursor that goes only up or down and is restricted by how many cards there are.
- Pressing space plays the card the cursor is on.
- Fixed flickering problem with rules
- Ink, Sudden Death, Mess, and Hell rules are all working

**1/20/19**
- Added javadoc
- Added gitignore file
- Added method to Player to remove N random cards
- Equality, rain, clone, magnet, and justice rules are all working
- Added rules to README
- Added methods to print rules in sets of 10
- Added getType method for rule
- Updated Game so that contradicting rules cannot appear in the same game
- Fixed bug where first player would have 2 turns
- Fixed bug where adding and removing a new player would crash the game
- Fixed bug where players could continuously draw
- Added function to change background color if sudden death effect is activated
- Added mode for drawing
- Added different commands for different modes

**1/21/19**
- Changed win message to automatically exit out of private mode
- Fixed bug where next player draws 1 card if current player draws
- Added comments to all code
- Removed useless methods
- Added function so that same player cannot be chosen twice when thunder card is played
