package io.qualified;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BracketChallenge {
		public static boolean check(String str) {
			List<Character> brackets = new ArrayList<>();

			Map<Character, Character> bracketMap = new HashMap();
			bracketMap.put('[', ']');
			bracketMap.put('{', '}');
			bracketMap.put('(', ')');
			bracketMap.put('<', '>');

			for (char currentChar : str.toCharArray()) {
				for (Map.Entry<Character, Character> charEntry : bracketMap.entrySet()) {
					if (charEntry.getKey().equals(currentChar)) {
						brackets.add(currentChar);
						break;
					} else if (charEntry.getValue().equals(currentChar)) {
						if (!brackets.isEmpty() &&
								brackets.get(brackets.size() - 1).equals(charEntry.getKey())) {
							brackets.remove(brackets.size() - 1);
							break;
						} else {
							return false;
						}
					}
				}
			}

			return brackets.isEmpty();
		}

	public static void main(String[] args) {
			String input = "]";
			System.out.println("Input string: '" +  input + "'  => " + BracketChallenge.check(input));
	}
}
