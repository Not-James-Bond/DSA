//This approach uses 2 HashMap & TreeMap

import java.util.*;

class Item {
    String key;         // The unique key for the item
    int val;            // The value of the item
    int priority;       // The priority of the item
    int expiry;         // The expiry time of the item

    // Constructor for the item
    public Item(String key, int val, int priority, int expiry) {
         this.key = key;
         this.val = val;
         this.priority = priority;
         this.expiry = expiry;
     }
}


// Node class for DoublyLinkedList
class Node {
    String value;       // The value of the node
    Node prev;          // The previous node
    Node next;          // The next node

    // Constructor for Node
    public Node(String value, Node prev, Node next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }
}

// Doubly Linked List class for implementing priority cache
class DoublyLinkedList {
    int value;
    Node start;         // The start node
    Node end;           // The end node
    HashMap<String, Node> map;  // Hashmap to map keys to nodes

    // Constructor for DoublyLinkedList
    public DoublyLinkedList() {
        this.start = null;
        this.end = null;
        map = new HashMap<>();
    }

    // Add a new node to the start of the list
    public void add(String val) {
        Node newNode = new Node(val, null, end); // Create a new node
         // Set the new node as the start node
        if(map.size()==0){
            start = end = newNode;
        }
        else {
            start.prev=newNode; 
            start = start.prev; //Changed the start node variable to the new node.
        }
        
        map.put(val, newNode);  // Map the key to the new node
    }

    // Remove the end node from the list
   public String removeEnd() {
        if(end == null) {
            return null;
        }
        map.remove(end.value);  // Remove the key from the map
        String removedValue = end.value;    // Get the value of the node
        if(end.prev == null) {
            start = null;
            end = null;
        } else {
            end.prev.next = null;   // Set the next node of the previous node as null
            end = end.prev; // Set the previous node as the end node
        }
        
        return removedValue;    // Return the value of the removed node
    }

    // Remove a node with the given value from the list
    public void remove(String val) {
        Node node = map.get(val);   // Get the node for the given key
        if(node.next == null) {
            //Removal from end/top
            removeEnd();    // If the node is the end node, remove the end node
            return;
        }
        if(node.prev == null) {
            node.next.prev= start;
            start = node.next;      // If the node is the start node, set the next node as the start node
        }
        else {
            node.next.prev= node.prev;
            node.prev.next = node.next; // If the node is in the middle, set the next node of the previous node as the next node of the current node
        }
        map.remove(val);    // Remove the key from the map
    }

    // Get the size of the map
    public int getSize() {
        return map.size();
    }
}

// PriorityExpiryCache class to implement the cache
class PriorityExpiryCache{
    int currentTime = 5;        // The current time
    int capacity;               // The capacity of the cache
    HashMap<String, Item> cacheInfo;   

    TreeMap<Integer, String> expiryMap;
    TreeMap<Integer, DoublyLinkedList> priortyMap;

    public PriorityExpiryCache(int cap){
        this.capacity = cap;
        cacheInfo = new HashMap<>();
        expiryMap = new TreeMap<>();
        priortyMap = new TreeMap<>();;
    }

    public int getItem(String key){
        Item item = this.cacheInfo.get(key);
        DoublyLinkedList doublyLinkedList = priortyMap.get(item.priority);
        if(doublyLinkedList.getSize()>1){
            doublyLinkedList.remove(item.key);
            doublyLinkedList.add(item.key);
        }
        return item.val;
    }

    public void setItem(Item item){
        if(cacheInfo.size() == this.capacity){
            //Evict item
            evictItem();
        }
            //add new frame
            addNewItem(item);
    }

    public Set<String> getKeys() {
        return cacheInfo.keySet();
    }

    public void evictItem(){
    // Evict on expiry basis
        Map.Entry<Integer,String> leastExpiry = this.expiryMap.firstEntry();
        System.out.println("leastExpiry: "+leastExpiry+'\n'+leastExpiry.getKey()+'\n'+leastExpiry.getValue());
        if(leastExpiry.getKey() < this.currentTime){
            //Remove the first element from cache
            Item item = this.cacheInfo.get(leastExpiry.getValue());
            System.out.println("leastExpiry.getKey()"+leastExpiry.getKey());
            this.expiryMap.remove(leastExpiry.getKey());
            this.cacheInfo.remove(leastExpiry.getValue());
            // Remove from priority map
            DoublyLinkedList doublyLinkedList = this.priortyMap.get(item.priority);
            doublyLinkedList.remove(leastExpiry.getValue());
            if(doublyLinkedList.getSize() == 0) {
                this.priortyMap.remove(item.priority);
            }
        }else{  
            //Evict on priority
            //System.out.println("priortyMap: "+this.priortyMap);
            Map.Entry<Integer,DoublyLinkedList> entry = this.priortyMap.firstEntry();
            DoublyLinkedList leastPriority = entry.getValue();
            //System.out.println("leastPriority.getSize()"+leastPriority.getSize());
            String val= leastPriority.removeEnd();
            
            Item item = this.cacheInfo.get(val);
            
            if(leastPriority.getSize() == 0) {
                // System.out.println("item.priority: "+item.priority);
                this.priortyMap.remove(item.priority);
                //System.out.println("priortyMap: "+this.priortyMap);
            }
            this.expiryMap.remove(item.expiry);
            this.cacheInfo.remove(item.key);
            System.out.println("PriorityMap leastPriority: " + leastPriority + '\n' + "leastExpiry: " + item.key + " Expiry: " + item.expiry + " Priority: " + item.priority);

            //System.out.println("entry"+entry);
        }
       // System.out.println("expiryMap: "+this.expiryMap);
        //System.out.println("priortyMap: "+this.priortyMap);
      //  System.out.println("cacheInfo: "+cacheInfo);
    }

    private void addNewItem(Item item){
        this.cacheInfo.put(item.key, item);
        this.expiryMap.put(item.expiry, item.key);
        System.out.println("cacheInfo"+cacheInfo);
        System.out.println("expiryMap"+expiryMap);
        DoublyLinkedList doublyLinkedList = this.priortyMap.get(item.priority);
        if(doublyLinkedList == null) {
            DoublyLinkedList newDoublyLinkedList = new DoublyLinkedList();
            newDoublyLinkedList.add(item.key);
            this.priortyMap.put(item.priority, newDoublyLinkedList);
            System.out.println("priortyMap: " + priortyMap);
        }else {
            doublyLinkedList.add(item.key);
        }
        //System.out.println("priorityMap"+priortyMap);
        System.out.println("expiryMap" + expiryMap + '\n' + "priorityMap" + priortyMap);

    }

}

public class Main{

    public static void main( String args[] ){
        PriorityExpiryCache priorityExpiryCache = new PriorityExpiryCache(5);
        priorityExpiryCache.setItem(new Item("A",5 , 5, 15));
        priorityExpiryCache.setItem(new Item("B", 6, 15, 3));
        priorityExpiryCache.setItem(new Item("E", 3, 5, 150));
        priorityExpiryCache.setItem(new Item("C", 2, 5, 10));
        priorityExpiryCache.setItem(new Item("D", 1, 1, 20));

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
