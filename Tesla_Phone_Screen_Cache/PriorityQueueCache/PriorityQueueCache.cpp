#include <iostream>
#include <string>
#include <unordered_map>
#include <map>
#include <set>

using namespace std;

class Item {
public:
    string key;   // The unique key for the item
    int val;      // The value of the item
    int priority; // The priority of the item
    int expiry;   // The expiry time of the item

    // Constructor for the item
    Item(string key, int val, int priority, int expiry)
        : key(key), val(val), priority(priority), expiry(expiry) {}
};

// Node class for DoublyLinkedList
class Node {
public:
    string value; // The value of the node
    Node* prev;   // The previous node
    Node* next;   // The next node

    // Constructor for Node
    Node(string value, Node* prev, Node* next)
        : value(value), prev(prev), next(next) {}
};

// Doubly Linked List class for implementing priority cache
class DoublyLinkedList {
public:
    Node* start; // The start node
    Node* end;   // The end node
    unordered_map<string, Node*> map; // Hashmap to map keys to nodes

    // Constructor for DoublyLinkedList
    DoublyLinkedList() : start(nullptr), end(nullptr) {}

    // Add a new node to the start of the list
    void add(string val) {
        Node* newNode = new Node(val, nullptr, start); // Create a new node
        // Set the new node as the start node
        if (map.size() == 0) {
            start = end = newNode;
        }
        else {
            start->prev = newNode;
            start = newNode;
        }

        map[val] = newNode; // Map the key to the new node
    }

    // Remove the top node from the list
    string removeTop() {
        if (end == nullptr) {
            return "";
        }
        map.erase(end->value); // Remove the key from the map
        string removedValue = end->value; // Get the value of the node
        if (end->prev == nullptr) {
            start = nullptr;
            end = nullptr;
        }
        else {
            end->prev->next = nullptr; // Set the next node of the previous node as nullptr
            end = end->prev; // Set the previous node as the end node
        }

        return removedValue; // Return the value of the removed node
    }

    // Remove a node with the given value from the list
    void remove(string val) {
        Node* node = map[val]; // Get the node for the given key
        if (node->next == nullptr) {
            // Removal from top
            removeTop(); // If the node is the end node, remove the top node
            return;
        }
        if (node->prev == nullptr) {
            node->next->prev = nullptr;
            start = node->next; // If the node is the start node, set the next node as the start node
        }
        else {
            node->next->prev = node->prev;
            node->prev->next = node->next; // If the node is in the middle, set the next node of the previous node as the next node of the current node
        }
        map.erase(val); // Remove the key from the map
    }

    // Get the size of the map
    int getSize() {
        return map.size();
    }
};

// PriorityExpiryCache class to implement the cache
class PriorityExpiryCache {
public:
    int currentTime = 5; // The current time
    int capacity;        // The capacity of the cache
    unordered_map<string, Item> cacheInfo;
    map<int, string> expiryMap;
    map<int, DoublyLinkedList> priorityMap;

    PriorityExpiryCache(int cap) : capacity(cap) {}

    int getItem(string key) {
        Item item = cacheInfo[key];
        DoublyLinkedList& doublyLinkedList = priorityMap[item.priority];
        if (doublyLinkedList.getSize() > 1) {
            doublyLinkedList.remove(item.key);
            doublyLinkedList.add(item.key);
        }
        return item.val;
    }

    void setItem(Item item) {
        if (cacheInfo.size() == capacity) {
            // Evict item
            evictItem();
        }
        // add new frame
        addNewItem(item);
    }

    set<string> getKeys() {
        set<string> keys;
        for (const auto& pair : cacheInfo) {
            keys.insert(pair.first);
        }
        return keys;
    }

    void evictItem() {
        // Evict on expiry basis
        auto leastExpiry = expiryMap.begin();
        if (leastExpiry->first < currentTime) {
            // Remove the first element from cache
            Item item = cacheInfo[leastExpiry->second];
            expiryMap.erase(leastExpiry);
            cacheInfo.erase(leastExpiry->second);
            // Remove from priority map
            DoublyLinkedList& doublyLinkedList = priorityMap[item.priority];
            doublyLinkedList.remove(leastExpiry->second);
            if (doublyLinkedList.getSize() == 0) {
                priorityMap.erase(item.priority);
            }
        }
        else {
            // Evict on priority
            auto entry = priorityMap.begin();
            DoublyLinkedList& leastPriority = entry->second;
            string val = leastPriority.removeTop();

            Item item = cacheInfo[val];

            if (leastPriority.getSize() == 0) {
                priorityMap.erase(item.priority);
            }
            expiryMap.erase(item.expiry);
            cacheInfo.erase(item.key);
        }
    }

    void addNewItem(Item item) {
        cacheInfo[item.key] = item;
        expiryMap[item.expiry] = item.key;
        DoublyLinkedList& doublyLinkedList = priorityMap[item.priority];
        if (doublyLinkedList.getSize() == 0) {
            DoublyLinkedList newDoublyLinkedList;
            newDoublyLinkedList.add(item.key);
            priorityMap[item.priority] = newDoublyLinkedList;
        }
        else {
            doublyLinkedList.add(item.key);
        }
    }
};

int main() {
    PriorityExpiryCache priorityExpiryCache(5);
    priorityExpiryCache.setItem(Item("A", 5, 5, 100));
    priorityExpiryCache.setItem(Item("B", 6, 15, 3));
    priorityExpiryCache.setItem(Item("C", 2, 5, 10));
    priorityExpiryCache.setItem(Item("D", 1, 1, 15));
    priorityExpiryCache.setItem(Item("E", 3, 5, 150));

    priorityExpiryCache.getItem("C");

    auto keys = priorityExpiryCache.getKeys();
    for (const auto& key : keys) {
        cout << key << " ";
    }
    cout << endl;

    priorityExpiryCache.evictItem();

    keys = priorityExpiryCache.getKeys();
    for (const auto& key : keys) {
        cout << key << " ";
    }
    cout << endl;

    priorityExpiryCache.evictItem();

    keys = priorityExpiryCache.getKeys();
    for (const auto& key : keys) {
        cout << key << " ";
    }
    cout << endl;

    priorityExpiryCache.evictItem();

    keys = priorityExpiryCache.getKeys();
    for (const auto& key : keys) {
        cout << key << " ";
    }
    cout << endl;

    return 0;
}



