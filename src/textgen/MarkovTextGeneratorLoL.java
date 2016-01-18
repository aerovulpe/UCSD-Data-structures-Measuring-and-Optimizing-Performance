package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * An implementation of the MTG interface that uses a list of lists.
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList;

	// The starting "word"
	private String starter;

	// The random number generator
	private Random rnGenerator;

	public MarkovTextGeneratorLoL(Random generator) {
		wordList = new LinkedList<ListNode>();
		rnGenerator = generator;
	}

	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText) {
		// Implement this method
		String[] words = sourceText.split("[\\s]+");
		// new ArrayList<>();
		// Matcher m = Pattern.compile("[!?.]+|[a-zA-Z]+").matcher(sourceText);
		//
		// while (m.find()) {
		// String tok = m.group();
		// if (!(tok.indexOf("!") >= 0 || tok.indexOf("?") >= 0 ||
		// tok.indexOf(".") >= 0))
		// words.add(tok);
		// }

		if (starter == null)
			starter = words[0];
		String prevWord = starter;
		String word = null;

		for (int i = 1; i < words.length; i++) {
			word = words[i];
			ListNode prevWordNode = findListNode(prevWord);
			if (prevWordNode == null) {
				prevWordNode = new ListNode(prevWord);
				wordList.add(prevWordNode);
				prevWordNode.addNextWord(word);
			} else
				prevWordNode.addNextWord(word);

			prevWord = word;
		}

		ListNode lastWordNode = findListNode(word);
		if (lastWordNode == null) {
			lastWordNode = new ListNode(prevWord);
			wordList.add(lastWordNode);
			lastWordNode.addNextWord(starter);
		} else
			lastWordNode.addNextWord(starter);
	}

	/**
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		// Implement this method
		if (numWords == 0 || starter == null)
			return "";

		String currentWord = starter;
		String output = new String(currentWord);
		int currentWordCnt = 0;

		while (currentWordCnt < numWords) {
			String randWord = findListNode(currentWord).getRandomNextWord(rnGenerator);
			output += randWord + " ";
			currentWord = randWord;
			currentWordCnt++;
		}

		return output;
	}

	// Can be helpful for debugging
	@Override
	public String toString() {
		String toReturn = "";
		for (ListNode n : wordList) {
			toReturn += n.toString();
		}
		return toReturn;
	}

	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText) {
		// Implement this method.
		wordList.clear();
		starter = null;
		train(sourceText);
	}

	// Add any private helper methods you need here.

	private ListNode findListNode(String word) {
		for (ListNode wordNode : wordList)
			if (wordNode.getWord().equals(word))
				return wordNode;
		return null;
	}

	/**
	 * This is a minimal set of tests. Note that it can be difficult to test
	 * methods/classes with randomized behavior.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, " + "You say stop, and I say go, go, go, "
				+ "Oh no. You say goodbye and I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello. " + "I say high, you say low, "
				+ "You say why, and I say I don't know. " + "Oh no. "
				+ "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello. " + "Why, why, why, why, why, why, "
				+ "Do you say goodbye. " + "Oh no. " + "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello. " + "You say yes, I say no, "
				+ "You say stop and I say go, go, go. " + "Oh, oh no. "
				+ "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/**
 * Links a word to the next words in the list You should use this class in your
 * implementation.
 */
class ListNode {
	// The word that is linking to the next words
	private String word;

	// The next words that could follow it
	private List<String> nextWords;

	ListNode(String word) {
		this.word = word;
		nextWords = new LinkedList<String>();
	}

	public String getWord() {
		return word;
	}

	public void addNextWord(String nextWord) {
		nextWords.add(nextWord);
	}

	public String getRandomNextWord(Random generator) {
		// Implement this method
		// The random number generator should be passed from
		// the MarkovTextGeneratorLoL class
		return nextWords.get(generator.nextInt(nextWords.size()));
	}

	public String toString() {
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}

}
