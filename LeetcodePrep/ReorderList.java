package LeetcodePrep;

import LeetcodePrep.Common.ListNode;

public class ReorderList {
    public static void reorderList(ListNode head) {
        // Check for edge cases
        if (head == null || head.next == null) return;
        
        // This is the head of the first half of the original list
        ListNode l1 = head;
        
        // Get halfway through the original list
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // Split the first and second halves of the original list
        prev.next = null;
        
        // Reverse the second half of the original list
        ListNode l2 = reverse(slow);
        
        // Merge the two lists together
        merge(l1, l2);
    }
    
    // Reverse a LinkedList - useful in its own regard
    private static ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        // You really should just memorize this formula
        while(curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        return prev;
    }
    
    // Merge two lists - useful in its own regard
    private static void merge(ListNode l1, ListNode l2) {
        // You should memorize this formula as well
        while (l1 != null) {
            ListNode l1Next = l1.next;
            ListNode l2Next = l2.next;
            
            l1.next = l2;
            
            if (l1Next == null) break;
            l2.next = l1Next;
            l1 = l1Next;
            l2 = l2Next;
        }
    }

    public static void main(String[] args) {
        ListNode curr = new ListNode(1);
        ListNode head = curr;
        for (int i = 2; i < 5; i++) {
            curr.next = new ListNode((i));
            curr = curr.next;
        }

        System.out.println("Test 1");
        System.out.print("Original List: ");
        ListNode test1Curr = head;
        while (test1Curr != null) {
            System.out.print(test1Curr.val + "->");
            test1Curr = test1Curr.next;
        }
        System.out.println("null");
        System.out.print("Reordered List: ");
        test1Curr = head;
        reorderList(test1Curr);
        while (test1Curr != null) {
            System.out.print(test1Curr.val + "->");
            test1Curr = test1Curr.next;
        }
        System.out.println("null");

        curr = new ListNode(1);
        head = curr;
        for (int i = 2; i < 6; i++) {
            curr.next = new ListNode((i));
            curr = curr.next;
        }

        System.out.println("Test 2");
        System.out.print("Original List: ");
        ListNode test2Curr = head;
        while (test2Curr != null) {
            System.out.print(test2Curr.val + "->");
            test2Curr = test2Curr.next;
        }
        System.out.println("null");
        System.out.print("Reordered List: ");
        test2Curr = head;
        reorderList(test2Curr);
        while (test2Curr != null) {
            System.out.print(test2Curr.val + "->");
            test2Curr = test2Curr.next;
        }
        System.out.println("null");
    }
}