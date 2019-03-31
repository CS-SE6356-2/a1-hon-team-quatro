/*
	Programmer: Tyler Heald
	Date: 3/31/2019
	Description:
	This is a test unit for the Deck class. It can use a prewritten cardList
	file, or it can generate one by reading in from System.in.
	Once a cardList has been made, it creates a Deck object, and lists all the
	cards in the Deck. It then shuffles the deck, checks the cards again, twice.
	Also serves to test the Card class, as Deck is a composition of Cards, and this
	utilizes methods from Card.
	
	METHODS:
	testDeck(Deck d)
		Checks the cards in a deck, and then shuffles and checks the cards again
		twice
	checkDeck(Deck d, int size)
		Checks the cards in the deck
		
	TODO:
		
*/

import java.util.*;
import java.io.*;

public class DeckTest {
	public static void main(String[] args) throws IOException
	{
		//Creating a scanner for input
		Scanner input = new Scanner(System.in);
		
		Menu:
		while(true) {
			//Printing a header and test options menu
			System.out.println("Testing Deck class. Choose an option:\n" +
			"1. Premade cardList\n" +
			"2. Make a cardList\n" +
			"3. Quit");
			
			//Reading in user choice
			String choice = input.nextLine();
			//Printing new line for formatting
			System.out.print("\n");
			int choiceInt;
			//Checking for valid input
			try {
				choiceInt = Integer.parseInt(choice);
			}	catch(NumberFormatException e) {
				//Not a number, print error and go back to menu
				System.out.println("Not a number!");
				continue Menu;
			}
			
			//Is valid, check which one
			if(choiceInt == 1)
			{
				//Make the deck and check it
				Deck deck = new Deck(new File("cardList"));
				
				//Printing header
				System.out.println("Cards in the deck:");
				//Testing the deck
				testDeck(deck);
			}
			else if(choiceInt == 2)
			{
				//Creating a buffered writer for writing to the file
				BufferedWriter writer = new BufferedWriter(new FileWriter("cardList"));
				
				//Looping to add new cards to the list
				System.out.println("Enter a card in the form \"value category\", or 'q' to finish");
				String newCard;
				while(true)
				{
					newCard = input.nextLine();
					//Checking input
					//Finishing the file
					if(newCard.equals("q"))
					{
						//newline for formatting
						System.out.print("\n");
						break;
					}
					//Checking that the input is of format "String String"
					else if(newCard.indexOf(" ") == newCard.lastIndexOf(" "))
					{
						//Add the card
						writer.write(newCard);
						//Newline
						writer.newLine();
					}
					else
					{
						//newCard format isn't right, output error
						System.out.println("Improper card format! Try again!");
						continue;
					}
				}
				//Closing the writer
				writer.close();
				
				//Testing the deck
				Deck deck = new Deck(new File("cardList"));
				testDeck(deck);
			}
			else if(choiceInt == 3)
			{
				//Quit out
				System.exit(0);
			}
			else
			{
				//Not a valid choice
				System.out.println("Not a valid option!");
				continue Menu;
			}
		}
	}
	
	//Helper method to print all the cards in a deck, given the
	//deck and its size
	static void checkDeck(Deck d, int numCards) {
		for(int i = 0; i < numCards; i ++)
		{
			d.getCardAt(i).printCard();
		}
	}
	//Helper method to test the deck. Checks the cards, shuffles, checks again,
	//shuffles, and checks again
	static void testDeck(Deck d) {
		//Printing the number of cards
		System.out.println("There are " + d.getNumOfCards() + " cards in the deck");
		checkDeck(d, d.getNumOfCards());
		//newline for formatting
		System.out.print("\n");
		
		d.shuffle();
		checkDeck(d, d.getNumOfCards());
		//newline for formatting
		System.out.print("\n");
		
		d.shuffle();
		checkDeck(d, d.getNumOfCards());
		//newline for formatting
		System.out.println("\n");
	}
}