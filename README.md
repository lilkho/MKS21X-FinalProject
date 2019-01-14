# MKS21X-FinalProject
## UNO!
Game rules [here](https://www.unorules.com/)
### **Note**
Players are not required to say "UNO!" when they have one card.

### **Instructions**

`$ javac -cp lanterna.jar:. UNO.java`

`$ java -Djava.awt.headless=true -cp lanterna.jar:. UNO`


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
~~- Bug: Deck is not printing correct cards~~
- Questions: setName? how does player choose to draw or to play +2? keep track of combo? turn not printing correctly?

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
