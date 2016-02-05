package spelling;

import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete
 * ADT
 * 
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

	private TrieNode root;
	private int size;

	public AutoCompleteDictionaryTrie() {
		root = new TrieNode();
	}

	/**
	 * Insert a word into the trie. For the basic part of the assignment (part
	 * 2), you should ignore the word's case. That is, you should convert the
	 * string to all lower case as you insert it.
	 */
	public boolean addWord(String word) {
		// Implement this method.
		word = word.toLowerCase();
		TrieNode currentNode = root;
		for (Character c : word.toCharArray()) {
			TrieNode child = currentNode.getChild(c);
			if (child == null)
				currentNode = currentNode.insert(c);
			else
				currentNode = child;
		}

		// Word already exists.
		if (currentNode.endsWord())
			return false;

		currentNode.setEndsWord(true);
		size++;
		return true;
	}

	/**
	 * Return the number of words in the dictionary. This is NOT necessarily the
	 * same as the number of TrieNodes in the trie.
	 */
	public int size() {
		// Implement this method
		return size;
	}

	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) {
		// Implement this method
		TrieNode node = findTrieNode(root, s.toLowerCase());
		return node == null ? false : node.endsWord();
	}

	/**
	 * * Returns up to the n "best" predictions, including the word itself, in
	 * terms of length If this string is not in the trie, it returns null.
	 * 
	 * @param text
	 *            The text to use at the word stem
	 * @param n
	 *            The maximum number of predictions desired.
	 * @return A list containing the up to n best predictions
	 */
	@Override
	public List<String> predictCompletions(String prefix, int numCompletions) {
		// Implement this method
		// This method should implement the following algorithm:
		// 1. Find the stem in the trie. If the stem does not appear in the
		// trie, return an
		// empty list
		// 2. Once the stem is found, perform a breadth first search to generate
		// completions
		// using the following algorithm:
		// Create a queue (LinkedList) and add the node that completes the stem
		// to the back
		// of the list.
		// Create a list of completions to return (initially empty)
		// While the queue is not empty and you don't have enough completions:
		// remove the first Node from the queue
		// If it is a word, add it to the completions list
		// Add all of its child nodes to the back of the queue
		// Return the list of completions
		List<String> predictions = new ArrayList<>();
		TrieNode stem = findTrieNode(root, prefix);

		if (stem == null)
			return predictions;

		Queue<TrieNode> toExplore = new LinkedList<>();
		toExplore.add(stem);

		while (predictions.size() < numCompletions && !toExplore.isEmpty()) {
			TrieNode currentNode = toExplore.remove();
			if (currentNode.endsWord())
				predictions.add(currentNode.getText());

			for (Character c : currentNode.getValidNextCharacters()) {
				TrieNode child = currentNode.getChild(c);
				if (child != null)
					toExplore.add(child);
			}
		}

		return predictions;
	}

	// For debugging
	public void printTree() {
		printNode(root);
	}

	/** Do a pre-order traversal from this node down */
	public void printNode(TrieNode curr) {
		if (curr == null)
			return;

		System.out.println(curr.getText());

		TrieNode next = null;
		for (Character c : curr.getValidNextCharacters()) {
			next = curr.getChild(c);
			printNode(next);
		}
	}

	// private int numOfWords(TrieNode node) {
	// if (node == null)
	// return 0;
	//
	// int numOfWords = 0;
	// for (Character c : node.getValidNextCharacters())
	// numOfWords += numOfWords(node.getChild(c));
	//
	// return numOfWords + (node.endsWord() ? 1 : 0);
	// }

	private TrieNode findTrieNode(TrieNode node, String key) {
		if (node == null || key.isEmpty())
			return node;

		return findTrieNode(node.getChild(key.charAt(0)), key.substring(1));
	}
}