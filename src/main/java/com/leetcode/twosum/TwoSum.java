package com.leetcode.twosum;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class TwoSum {
	public int[] twoSum(int[] nums, int target) {
		System.out.println("Input: nums = " + Arrays.toString(nums) + "; target = " + target);
		int[] resultInt = new int[2];
		AtomicBoolean found = new AtomicBoolean(false);
		for (int i = 0; i < nums.length; i++) {

			int x = nums[i];
			int y = target - x;

			int xPosition = i;
			Arrays.stream(nums).filter(value -> value == y).findFirst().ifPresent(
					(value) -> {
						int yPosition = -1;
						for (int j = 0; j < nums.length; j++) {
							if (nums[j] == y) {
								yPosition = j;
								if (xPosition != yPosition) {
									resultInt[0] = xPosition;
									resultInt[1] = yPosition;
									found.set(true);
									break;
								}
							}
						}
					}
			);
			if (found.get()) break;
		}
		System.out.println("Resulted array is: " + Arrays.toString(resultInt) + " => " + nums[resultInt[0]] + " + " + nums[resultInt[1]] + " = " + (nums[resultInt[0]] + nums[resultInt[1]]) + "\n");
		return resultInt;
	}

	public static void main(String[] args) {
		TwoSum ts = new TwoSum();
		ts.twoSum(new int[]{2, 7, 11, 15}, 9);
		ts.twoSum(new int[]{3, 2, 4}, 6);
		ts.twoSum(new int[]{3, 3}, 6);
		ts.twoSum(new int[]{2, 4, 11, 3}, 6);
	}
}