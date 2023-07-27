//This appraoch uses 2 TreeMap
import java.util.*;

class Item {
    String key; // The unique key for the item
    int val; // The value of the item
    int priority; // The priority of the item
    int expiry; // The expiry time of the item
    Item prev; // The previous item in the linked list
    Item next; // The next item in the linked list

    // Constructor for the item
    public Item(String key, int val, int priority, int expiry) {
        this.key = key;
        this.val = val;
        this.priority = priority;
        this.expiry = expiry;
        this.prev = null;
        this.next = null;
    }
}

// Doubly Linked List class for implementing priority cache
class DoublyLinkedList {
    Item start; // The start item
    Item end; // The end item
    HashMap<String, Item> map; // Hashmap to map keys to items

    // Constructor for DoublyLinkedList
    public DoublyLinkedList() {
        this.start = null;
        this.end = null;
        map = new HashMap<>();
    }

    // Add a new item to the start of the list
    public void add(Item item) {
        if (start == null) {
            start = end = item;
        } else {
            start.prev = item;
            item.next = start;
            start = item;
        }
        map.put(item.key, item);
    }

    // Remove the end item from the list
    public String removeEnd() {
        if (end == null) {
            return null;
        }
        System.out.println("Key Removed from Double LinkedList: " + end.key);
        map.remove(end.key); // Remove the key from the map
        String removedValue = end.key; // Get the key of the item
        if (end.prev == null) {
            start = null;
            end = null;
        } else {
            end.prev.next = null; // Set the next item of the previous item as null
            end = end.prev; // Set the previous item as the end item
        }

        return removedValue; // Return the key of the removed item
    }

    // Remove an item with the given key from the list
    public void remove(String key) {
        Item item = map.get(key); // Get the item for the given key
        if (item.next == null) {
            removeEnd(); // If the item is the end item, remove the end item
            return;
        }
        if (item.prev == null) {
            item.next.prev = null;
            start = item.next; // If the item is the start item, set the next item as the start item
        } else {
            item.next.prev = item.prev;
            item.prev.next = item.next; // If the item is in the middle, set the next item of the previous item as the
                                        // next item of the current item
        }
        map.remove(key); // Remove the key from the map
    }

    // Get the size of the map
    public int getSize() {
        return map.size();
    }
}

// PriorityExpiryCache class to implement the cache
class PriorityExpiryCache {
    int currentTime = 5; // The current time
    int capacity; // The capacity of the cache
    int currentSize = 0;

    TreeMap<Integer, Set<Item>> expiryMap;

    TreeMap<Integer, DoublyLinkedList> priorityMap;

    public PriorityExpiryCache(int cap) {
        this.capacity = cap;
        this.currentSize = 0;
        expiryMap = new TreeMap<>();
        priorityMap = new TreeMap<>();
    }

    public int getItem(String key) {
        Item item = null;
        for (Set<Item> itemSet : expiryMap.values()) {
            for (Item i : itemSet) {
                if (i.key.equals(key)) {
                    item = i;
                    break;
                }
            }
            if (item != null) {
                break;
            }
        }

        if (item == null) {
            // Item with the given key not found in the cache
            return -1; // Or any appropriate default value indicating the item is not found
        }
        DoublyLinkedList doublyLinkedList = priorityMap.get(item.priority);
        if (doublyLinkedList.getSize() > 1) {
            doublyLinkedList.remove(item.key);
            doublyLinkedList.add(item);
        }
        return item.val;
    }

    public void setItem(Item item) {
        if (currentSize == this.capacity) {
            // Evict item
            evictItem();
        }
        // add new item
        addNewItem(item);
    }

    public Set<String> getKeys() {
        Set<String> keys = new HashSet<>();
        for (Set<Item> itemSet : expiryMap.values()) {
            for (Item item : itemSet) {
                keys.add(item.key);
            }
        }
        return keys;
        // return cacheInfo.keySet();
    }

    public void evictItem() {
        // Evict on expiry basis
        if (!expiryMap.isEmpty()) {
            // Set<Item> leastExpirySet =
            // expiryMap.get(expiryMap.keySet().iterator().next());
            Map.Entry<Integer, Set<Item>> leastExpiry = this.expiryMap.firstEntry();
            Set<Item> leastExpirySet = leastExpiry.getValue();
            Item itemToBeRemoved = leastExpirySet.iterator().next();
            System.out.println(
                    "EXPIRY\n" + "leastExpirySet: " + leastExpirySet + '\n' + "leastExpiry: " + itemToBeRemoved.key
                            + " Expiry: " + itemToBeRemoved.expiry + " Priority: " + itemToBeRemoved.priority);
            if (itemToBeRemoved.expiry < this.currentTime) {
                // Remove the first element from cache
                leastExpirySet.remove(itemToBeRemoved);
                if (leastExpirySet.isEmpty()) {
                    this.expiryMap.remove(itemToBeRemoved.expiry);
                } else {
                    this.expiryMap.put(itemToBeRemoved.expiry, leastExpirySet);
                }
                // Remove from priority map
                DoublyLinkedList doublyLinkedList = this.priorityMap.get(itemToBeRemoved.priority);
                doublyLinkedList.remove(itemToBeRemoved.key);
                if (doublyLinkedList.getSize() == 0) {
                    this.priorityMap.remove(itemToBeRemoved.priority);
                }
            } else {
                // Evict on priority
                Map.Entry<Integer, DoublyLinkedList> entry = this.priorityMap.firstEntry();
                DoublyLinkedList leastPriority = entry.getValue();
                Item item = leastPriority.end;
                System.out.println("\nPRIORITY\nPriorityMap leastPriority: " + leastPriority + '\n' + "leastExpiry: "
                        + item.key + " Expiry: " + item.expiry + " Priority: " + item.priority);
                leastPriority.removeEnd();
                leastExpirySet = this.expiryMap.get(item.expiry);
                leastExpirySet.remove(item);
                System.out.println("UPDATED leastExpirySet: " + leastExpirySet);
                if (leastExpirySet.isEmpty()) {
                    this.expiryMap.remove(item.expiry);
                } else {
                    this.expiryMap.put(item.expiry, leastExpirySet);
                }
                System.out.println("Updated Expiry Map" + expiryMap);
                if (leastPriority.getSize() == 0) {
                    this.priorityMap.remove(item.priority);
                }
            }
        }
    }

    private void addNewItem(Item item) {
        // this.cacheInfo.put(item.key, item);
        Set<Item> itemSet = this.expiryMap.getOrDefault(item.expiry, new HashSet<>());
        itemSet.add(item);
        this.expiryMap.put(item.expiry, itemSet);
        currentSize++;
        DoublyLinkedList doublyLinkedList = this.priorityMap.get(item.priority);
        if (doublyLinkedList == null) {
            DoublyLinkedList newDoublyLinkedList = new DoublyLinkedList();
            newDoublyLinkedList.add(item);
            this.priorityMap.put(item.priority, newDoublyLinkedList);
        } else {
            doublyLinkedList.add(item);
        }
        System.out.println("expiryMap" + expiryMap + '\n' + "priorityMap" + priorityMap);
    }
}

public class Main {

    public static void main(String args[]) {
        PriorityExpiryCache priorityExpiryCache = new PriorityExpiryCache(5);
        priorityExpiryCache.setItem(new Item("A", 5, 5, 100));
        priorityExpiryCache.setItem(new Item("B", 6, 15, 3));
        priorityExpiryCache.setItem(new Item("C", 2, 10, 10));
        priorityExpiryCache.setItem(new Item("D", 1, 10, 15));
        priorityExpiryCache.setItem(new Item("E", 3, 20, 100));

        priorityExpiryCache.getItem("C");

        System.out.println(priorityExpiryCache.getKeys());

        priorityExpiryCache.evictItem();
        System.out.println(priorityExpiryCache.getKeys());

        priorityExpiryCache.evictItem();
        System.out.println(priorityExpiryCache.getKeys());

        priorityExpiryCache.evictItem();
        System.out.println(priorityExpiryCache.getKeys());

        priorityExpiryCache.evictItem();
        System.out.println(priorityExpiryCache.getKeys());
    }
}
