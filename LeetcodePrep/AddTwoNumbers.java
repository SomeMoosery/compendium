package LeetcodePrep;

import LeetcodePrep.Common.ListNode;

class AddTwoNumbers {
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Check edge cases
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        
        // Create the head of our added number, and a dummy to point to that head
        ListNode head = new ListNode(l1.val + l2.val);
        ListNode curr = new ListNode(0);
        curr.next = head;
        l1 = l1.next;
        l2 = l2.next;
        
        // Add all the numbers we can
        while (l1 != null && l2 != null) {
            head.next = new ListNode(l1.val + l2.val);
            head = head.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        
        // Deal with residuals on either list (i.e. if one list is longer than the other)
        if (l1 != null) {
            head.next = l1;
        }
        if (l2 != null) {
            head.next = l2;
        }
        
        // Get the head of our new list
        head = curr.next;
        
        // Deal with numbers >= 10
        curr = head;
        while (curr != null) {
            if (curr.val >= 10) {
                curr.val = curr.val - 10;
                if (curr.next == null) {
                    curr.next = new ListNode(1);
                } else {
                    curr.next.val++;
                }
            }
            curr = curr.next;
        }
        
        // Return our finished list
        return head;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode curr = l1;
        System.out.print("l1: ");
        while (curr != null) {
            System.out.print(curr.val + "->");
            curr = curr.next;
        }
        System.out.println("null");

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        curr = l2;
        System.out.print("l2: ");
        while (curr != null) {
            System.out.print(curr.val + "->");
            curr = curr.next;
        }
        System.out.println("null");

        ListNode head = addTwoNumbers(l1, l2);
        System.out.print("sum: ");
        while (head != null) {
            System.out.print(head.val+"->");
            head = head.next;
        }
        System.out.println("null");
    }
}