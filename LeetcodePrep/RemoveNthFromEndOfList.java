package LeetcodePrep;

import LeetcodePrep.Common.ListNode;

class RemoveNthFromEnd {
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        // Edge case
        if (head == null) return null;

        // Get the length of the list
        int length = 0;
        ListNode curr = head;
        while (curr != null) {
            length++;
            curr = curr.next;
        }

        // A couple more edge cases pop up here
        if (length == 1) return null;
        if (length == n) return head.next;
        
        // Remove specified node
        curr = head;
        int count = 1;
        while (count < length-n) {
            curr = curr.next;
            count++;
        }
        curr.next = curr.next.next;
        return head;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(3);
        l1.next.next.next = new ListNode(4);
        l1.next.next.next.next = new ListNode(5);

        ListNode curr = l1;
        while (curr != null) {
            System.out.print(curr.val+"->");
            curr = curr.next;
        }
        System.out.println("->null\nRemove 3");
        curr = removeNthFromEnd(l1, 3);
        while (curr != null) {
            System.out.print(curr.val+"->");
            curr = curr.next;
        }
        System.out.println("->null");
    }
}