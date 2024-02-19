package com.leetcode.median;

import java.util.Arrays;

public class MedianTwoArrays {
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int[] mergedArray = Arrays.copyOf(nums1, nums1.length + nums2.length);
		System.arraycopy(nums2, 0, mergedArray, nums1.length, nums2.length);
		Arrays.sort(mergedArray);

		int middleIndex = mergedArray.length / 2;

		System.out.println("Array1: " + Arrays.toString(nums1));
		System.out.println("Array2: " + Arrays.toString(nums2));
		System.out.println("mergedArray: " + Arrays.toString(mergedArray) + ", its length: " + mergedArray.length);

		if (mergedArray.length % 2 == 0) {     // even length
			int val1 = mergedArray[middleIndex  - 1];
			int val2 = mergedArray[middleIndex ];
			return ((double)(val1 + val2) )/ 2.0;
		} else {
			return mergedArray[middleIndex];
		}
	}

	public static void main(String[] args) {
		MedianTwoArrays mta = new MedianTwoArrays();
		System.out.println("Median: " + mta.findMedianSortedArrays(new int[] {1,3}, new int[] {2}) + "\n");
		System.out.println("Median: " + mta.findMedianSortedArrays(new int[] {1,2}, new int[] {3,4}) + "\n");
		System.out.println("Median: " + mta.findMedianSortedArrays(new int[] {1,2,6,9,10}, new int[] {3,4,4,5}) + "\n");
		System.out.println("Median: " + mta.findMedianSortedArrays(new int[] {1,2,6,9,10}, new int[] {3,4,5,7}) + "\n");
	}

}
