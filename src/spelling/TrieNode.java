package spelling;

import java.util.HashMap;
import java.util.Set;

/**
 * Represents a node in a Trie
 * 
 * @author UC San Diego Intermediate Programming MOOC Team
 *
 */
class TrieNode {
	private TrieNode parent;
	private HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
	private char c;
	private boolean isWord;

	/** Create a root TrieNode */
	public TrieNode() {
	}

	/**
	 * Return the TrieNode that is the child when you follow the link from the
	 * given Character
	 * 
	 * @param c
	 *            The next character in the key
	 * @return The TrieNode that character links to, or null if that link is not
	 *         in the trie.
	 */
	public TrieNode getChild(Character c) {
		return children.get(c);
	}

	/**
	 * Inserts this character at this node. Returns the newly created node, if c
	 * wasn't already in the trie. If it was, it does not modify the trie and
	 * returns null.
	 * 
	 * @param c
	 *            The character that will link to the new node
	 * @return The newly created TrieNode, or null if the node is already in the
	 *         trie.
	 */
	public TrieNode insert(Character c) {
		if (children.containsKey(c))
			return null;

		TrieNode next = new TrieNode();
		next.parent = this;
		next.c = c;
		children.put(c, next);
		return next;
	}

	/** Return the text string at this node */
	public String getText() {
		StringBuilder text = new StringBuilder();
		text.append(c);
		TrieNode stem = parent;

		while (stem != null) {
			text.append(stem.c);
			stem = stem.parent;
		}
		// Reverse text & exclude 'u\0000' @ root
		return text.reverse().substring(1);
	}

	/** Set whether or not this node ends a word in the trie. */
	public void setEndsWord(boolean b) {
		isWord = b;
	}

	/** Return whether or not this node ends a word in the trie. */
	public boolean endsWord() {
		return isWord;
	}

	/** Return the set of characters that have links from this node */
	public Set<Character> getValidNextCharacters() {
		return children.keySet();
	}

}
