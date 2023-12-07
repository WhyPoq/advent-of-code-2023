import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

enum HandType {
	highCard(0),
  	onePair(1),
  	twoPair(2),
  	three(3),
  	fullHouse(4),
  	four(5),
  	five(6);

  	private final int value;
    private HandType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

class CardPair {
	public char name;
	public int count;

	public CardPair(char name, int count) {
		this.name = name;
		this.count = count;
	}
}

class Hand {

	public String cardsStr;
	public int bid;
	public HandType type = HandType.highCard;

	public Hand(String cardsStr, int bid) {
		this.cardsStr = cardsStr;
		this.bid = bid;
		calcType();
	}

	private void calcType() {
		HashMap<Character, Integer> cardsFreq = new HashMap<>();

		int jokersCount = 0;
		for (int i = 0; i < cardsStr.length(); i++) {
			char curCard = cardsStr.charAt(i);
			if(curCard == 'J'){
				jokersCount++;
			}
			else{
				if (!cardsFreq.containsKey(curCard)) {
					cardsFreq.put(curCard, 1);
				} else {
					cardsFreq.put(curCard, cardsFreq.get(curCard) + 1);
				}
			}
		}

		ArrayList<CardPair> cards = new ArrayList<>();
		for (char cardName : cardsFreq.keySet()) {
			cards.add(new CardPair(cardName, cardsFreq.get(cardName)));
		}

		Collections.sort(cards, new Comparator<CardPair>() {
			public int compare(CardPair card1, CardPair card2) {
				return card2.count - card1.count;
			}
		});

		while(cards.size() < 5){
			cards.add(new CardPair('0', 0));
		}
		cards.get(0).count += jokersCount;

		if (cards.get(0).count == 5) {
			type = HandType.five;
		} else if (cards.get(0).count == 4) {
			type = HandType.four;
		} else if (cards.get(0).count == 3 && cards.get(1).count == 2) {
			type = HandType.fullHouse;
		} else if (cards.get(0).count == 3) {
			type = HandType.three;
		} else if (cards.get(0).count == 2 && cards.get(1).count == 2) {
			type = HandType.twoPair;
		} else if (cards.get(0).count == 2) {
			type = HandType.onePair;
		}

	}

	public String toString(){
		return cardsStr + " : " + type + " | " + bid;
	}

}

class Task2 {

	public static void main(String[] args) {

		HashMap<Character, Integer> cardValues = new HashMap<>();
		cardValues.put('2', 2);
		cardValues.put('3', 3);
		cardValues.put('4', 4);
		cardValues.put('5', 5);
		cardValues.put('6', 6);
		cardValues.put('7', 7);
		cardValues.put('8', 8);
		cardValues.put('9', 9);
		cardValues.put('T', 10);
		cardValues.put('J', 1); //joker
		cardValues.put('Q', 12);
		cardValues.put('K', 13);
		cardValues.put('A', 14);

		try {
			File inputFile = new File("day7_input.txt");
			Scanner input = new Scanner(inputFile);

			ArrayList<String> inputLines = new ArrayList<>();

			while (input.hasNextLine()) {
				String line = input.nextLine();

				inputLines.add(line);
			}
			input.close();

			ArrayList<Hand> hands = new ArrayList<>();
			for (int i = 0; i < inputLines.size(); i++) {
				String[] splitLine = inputLines.get(i).split(" ");
				hands.add(new Hand(splitLine[0], Integer.parseInt(splitLine[1])));
			}

			Collections.sort(hands, new Comparator<Hand>(){
				public int compare(Hand hand1, Hand hand2){
					if(hand2.type.getValue() == hand1.type.getValue()){
						int curInd = 0;
						while(hand1.cardsStr.charAt(curInd) == hand2.cardsStr.charAt(curInd)){
							curInd++;
						}
						return cardValues.get(hand1.cardsStr.charAt(curInd)) - cardValues.get(hand2.cardsStr.charAt(curInd));
					}
					else {
						return hand1.type.getValue() - hand2.type.getValue();
					}
				}
			});
			
			int res = 0;
			for(int i = 0; i < hands.size(); i++){
				res += hands.get(i).bid * (1 + i);
				//System.out.println(hands.get(i).toString());
			}

			System.out.println(res);

		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

}
