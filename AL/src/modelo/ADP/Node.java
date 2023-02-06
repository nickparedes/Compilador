package modelo.ADP;

public class Node <TD>{
    Node<TD> next;
    TD data;

    public Node(TD data){
        this.data = data;
        next = null;
    }
}
