Team:
Chris
Jacob
Jonathan
Tyler

Compilation Instructions:
1. Make sure you have a Java Development Kit (JDK) installed
2. Find the JDK (should be in c:\Program Files\Java
3. Enther this line in command line
set path=%path%;C:\Program Files\Java\jdk-9.0.1\bin
4. Change Directory to the location of this project, into the src folder

To compile and run the project:
javac Main.java GameGUI.java Cardgame.java Deck.java Cardpile.java Card.java Player.java Hand.java PlayerQueue.java
Java Main

To compile and run CardpileTest
javac CardpileTest.java Cardpile.java Card.java
java CardpileTest

To compile and run DeckTest
javac DeckTest.java Deck.java Card.java
java DeckTest

Compiling Main.java will run the framework at its current development.
Compiling DeckTest.java will run a unit test for the Deck class and Card class.
Compiling CardpileTest.java will run a unit test for the Cardpile class and Card class.
