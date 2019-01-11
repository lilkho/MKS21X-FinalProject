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

**1/10/19**
- Deck is working
~~- Bug: Deck is not printing correct cards~~
- Questions: setName? how does player choose to draw or to play +2? keep track of combo? turn not printing correctly?
- Added get functions to Game class so UNO can use them
- Added printPlayers function to print by line rather than an array ArrayList
- Draw function works in all cases
- Playing a card works for everything except +2
- Added setColor for when playing a wild card
- Player says UNO! if player has 1 card
- Terminal shows correct amount of players, amount of cards, player deck, and topcard.
- Terminal shows cards with corresponding colors.
- Deck is random.
