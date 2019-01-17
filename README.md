# MKS21X-FinalProject
## UNO!
Game rules [here](https://www.unorules.com/)
#### **Note**
Players are not required to say "UNO!" when they have one card.

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
For git bash:
```
javac -cp "lanterna.jar;." UNO.java
```
4. Run the UNO game
```
java -Djava.awt.headless=true -cp lanterna.jar:. UNO
```
For git bash:
```
java -Djava.awt.headless=true -cp "lanterna.jar;." UNO
```


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
