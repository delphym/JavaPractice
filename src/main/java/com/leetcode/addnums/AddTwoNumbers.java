package com.leetcode.addnums;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;


/**
 * Definition for singly-linked list.
 */
class ListNode {
	int val;
	ListNode next;
	ListNode() {}
	ListNode(int val) {this.val = val;}
	ListNode(int val, ListNode next) {this.val = val;this.next = next;}
}

public class AddTwoNumbers {
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		List<String> list1 = createListFromLinkedList("l1:", l1);
		BigInteger i1 = revertListAsInt(list1);

		List<String> list2 = createListFromLinkedList("l2:", l2);
		BigInteger i2 = revertListAsInt(list2);

		BigInteger addedResult = i1.add(i2);

		List<String> addedResultList = String.valueOf(addedResult).chars().mapToObj(value -> String.valueOf((char) value)).collect(Collectors.toList());
		ListNode listNode = createLinkedListFromNonReversedList(addedResultList);
		System.out.println("addedResult: " + addedResult);
		System.out.println("addedResultList: " + addedResultList);
		createListFromLinkedList("OutputeLinkedList", listNode);
		return listNode;
	}

	private static ListNode createLinkedListFromNonReversedList(List<String> list) {
		ListIterator<String> listIterator = list.listIterator();
		ListNode node = new ListNode(Integer.parseInt(listIterator.next()));
		while (listIterator.hasNext()) {
			node = new ListNode(Integer.parseInt(listIterator.next()), node);
		}
		return node;
	}

	private static List createListFromLinkedList(String linkedListName, ListNode current) {
		System.out.println("Linked List " + linkedListName);
		List<String> list = new ArrayList<>();
		while (current != null) {
			System.out.print(current.val);
			list.add(String.valueOf(current.val));
			current = current.next;
		}
		System.out.println("\nThere are " + list.size() + " elements.");
		return list;
	}

	private static BigInteger revertListAsInt(List<String> list) {
		StringBuilder sb = new StringBuilder();
		ListIterator<String> listIterator = list.listIterator(list.size());
		while (listIterator.hasPrevious()) {
			sb.append(listIterator.previous());
		}
		System.out.println("Reversed list: " + sb.toString() + "\n");
		return new BigInteger(sb.toString());
	}

	public static void main(String[] args) {
		AddTwoNumbers atn = new AddTwoNumbers();

		ListNode l1 = atn.createListNodeFromArray(new int[] {2,4,3});
		ListNode l2 = atn.createListNodeFromArray(new int[] {5,6,4});

		ListNode l11 = new ListNode(9);
		ListNode l22 = atn.createListNodeFromArray(new int[] {1, 9, 9, 9, 9, 9, 9, 9, 9, 9});

		ListNode l111 = atn.createListNodeFromArray(new int[] {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1});
		ListNode l222 = atn.createListNodeFromArray(new int[] {5,6,4});

		atn.addTwoNumbers(l1, l2);
		atn.addTwoNumbers(l11, l22);
		atn.addTwoNumbers(l111, l222);
	}

	private ListNode createListNodeFromArray(int[] listNodeArray) {
		ListNode head = null;

		for (int i = listNodeArray.length - 1; i >= 0; i--) {
			head = new ListNode(listNodeArray[i], head);
				System.out.println("The " + i + "th node's value is: " + head.val + " and points to: " + head.next);
		}
		return head;
	}
}
