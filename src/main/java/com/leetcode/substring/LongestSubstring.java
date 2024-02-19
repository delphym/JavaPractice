package com.leetcode.substring;

import java.util.HashMap;

public class LongestSubstring {
	public int lengthOfLongestSubstring(String s) {
		int resultLength = 0;
		String resultSubS = null;

		HashMap<Character, Integer> visitedChars = new HashMap<>();
		int l = 0;
		for (int r = 0; r < s.length(); r++) {
			char c = s.charAt(r);
			if (visitedChars.get(c) != null && visitedChars.get(c) >= l) {
				l = visitedChars.get(c) + 1;
			} else {
				resultLength = Math.max(resultLength, r - l + 1);
				resultSubS = s.substring(l, r + 1);
			}
			visitedChars.put(c, r);
		}

		System.out.println("Substring: '" + resultSubS + "', its length is: '" + resultLength);
		return resultLength;
	}

	public static void main(String[] args) {
		LongestSubstring ls = new LongestSubstring();
		ls.lengthOfLongestSubstring("abcacbdd");
		ls.lengthOfLongestSubstring("prdel");
		ls.lengthOfLongestSubstring("abcabcbb");
		ls.lengthOfLongestSubstring("bbbbb");
		ls.lengthOfLongestSubstring("pwwkew");
		ls.lengthOfLongestSubstring("prdmrdzmrd");
		ls.lengthOfLongestSubstring("mamamamaso");
	}
}
