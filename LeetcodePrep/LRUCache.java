package LeetcodePrep;

import java.util.*;

class LRUCache {

    // we need to implement a doubly-linked list here
    private class ListNode {
        int key;
        int value;

        ListNode prev;
        ListNode next;
    }

    class Cache {
        // This gives us O(1) lookup, we just use this to back up our doubly-linked-list
        Map<Integer, ListNode> map = new HashMap<Integer, ListNode>();

        // Instantiate our doubly linked list
        ListNode head;
        ListNode tail;

        int count;
        int max;

        public Cache(int max) {
            // Cache starts empty and capacity is set by user
            count = 0;
            this.max = max;

            // Dummy head and tail nodes to avoid empty states
            head = new ListNode();
            tail = new ListNode();

            // Wire the head and tail together
            head.next = tail;
            tail.prev = head;
        }

        public Integer get(int key) {
            ListNode node = map.get(key); // This is our fast lookup in action!

            if (node == null) {
                return null;
            }

            // Item has been accessed. Move to the front of the cache
            moveToFront(node);

            return node.value;
        }

        public void put(int key, int value) {
            ListNode node = map.get(key);

            if (node == null) {
                // Item not found, create a new entry
                ListNode newNode = new ListNode();
                newNode.key = key;
                newNode.value = value;

                // Add to the map and the actual list that represents the cache
                map.put(key, newNode);
                addToFront(newNode);
                count++;

                // If over capacity remove the LRU item
                if (count > max) {
                    removeLRUEntry();
                }
            } else {
                // If item is found in the cache, just update it and move it to the front of the list
                node.value = value;
                moveToFront(node);
            }
        }

        private void removeLRUEntry() {
            ListNode tail = popTail();

            map.remove(tail.key);
            count--;
        }

        private ListNode popTail() {
            ListNode tailItem = tail.prev;
            removeFromList(tailItem);

            return tailItem;
        }

        private void removeFromList(ListNode node) {
            ListNode savedPrev = node.prev;
            ListNode savedNext = node.next;

            savedPrev.next = savedNext;
            savedNext.prev = savedPrev;
        }

        private void addToFront(ListNode node) {
            // Wire up the new node being to be inserted
            node.prev = head;
            node.next = head.next;

            /*
             * Re-wire the node after the head. Our node is still sitting
             * "in the middle of nowhere". We got the new node pointing to the right things,
             * but we need to fix up the original head & head's next.
             */
            head.next.prev = node;
            head.next = node;
        }

        private void moveToFront(ListNode node) {
            removeFromList(node);
            addToFront(node);
        }
    }
}