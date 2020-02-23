public class Node {
    Node next;
    Node prev;
    int data;

    public Node(int data) {
        this.data = data;
    }

    public boolean hasNext() {
        return next != null;
    }
}

