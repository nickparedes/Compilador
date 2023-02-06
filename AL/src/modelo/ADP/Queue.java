package modelo.ADP;

public class Queue <TD>{
    Node<TD> first;
    Node<TD> last;

    public Queue(){
        first = null;
        last = null;
    }

    public void enqueue(TD data){
        Node newNode = new Node(data);
        if(first == null){
            first = newNode;
            last = newNode;
        }else{
            last.next = newNode;
            last = newNode;
        }
    }

    public TD getFirst() {
        return first.data;
    }
    
    

    public String toString(){
        String result = "";
        Node aux = first;
        while(aux != null){
            result += "   " + aux.data;
            aux = aux.next;
        }
        return result;
    }

    public Object dequeue(){
        Node aux = first;
        first = first.next;
        return aux.data;
    }


}
