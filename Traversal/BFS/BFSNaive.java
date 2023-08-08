package Traversal.BFS;
// Recursive Java program for level
// order traversal of Binary Tree

// Class containing left and right child of current
// node and key value
class Node {
	int data;
	Node left, right;
	public Node(int item)
	{
		data = item;
		left = right = null;
	}
}

class BFSNaive {
	
	// Root of the Binary Tree
	Node root;

	public BFSNaive() { root = null; }

	// Function to print level order traversal of tree
	void printLevelOrder()
	{
		int h = height(root);
		int i;
		for (i = 1; i <= h; i++)
			printCurrentLevel(root, i);
	}

int height(Node root) {
	System.out.println("Root: " + root);
    if (root == null) {
        System.out.println("Node is null for " + root); // Print statement for debugging
        return 0;
    } else {
        // Compute height of each subtree
        int lheight = height(root.left);
        System.out.println("Left subtree height: " + lheight); // Print statement for debugging
        int rheight = height(root.right);
        System.out.println("Right subtree height: " + rheight); // Print statement for debugging

        // use the larger one
        if (lheight > rheight) {
            System.out.println("Returning left subtree height + 1");
            return (lheight + 1);
        } else {
            System.out.println("Returning right subtree height + 1");
            return (rheight + 1);
        }
    }
}

	// Print nodes at the current level
	void printCurrentLevel(Node root, int level)
	{
		if (root == null)
			return;
		if (level == 1)
			System.out.print(root.data + " ");
		else if (level > 1) {
			printCurrentLevel(root.left, level - 1);
			printCurrentLevel(root.right, level - 1);
		}
	}

	// Driver program to test above functions
	public static void main(String args[])
	{
		BFSNaive tree = new BFSNaive();
		tree.root = new Node(1);
		tree.root.left = new Node(2);
		tree.root.right = new Node(3);
		tree.root.left.left = new Node(4);
		tree.root.left.right = new Node(5);

		System.out.println("Level order traversal of binary tree is " + tree);
		tree.printLevelOrder();
	}
}
