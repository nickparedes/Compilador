package modelo.ADP;

public class Stack <TD>{
    Node <TD> last;

    public void push(TD newValue){
        Node newNode = new Node(newValue);
        newNode.next = last;
        last = newNode;
    }

    public Object pop(){
        Node aux = last;
        last = last.next;
        return aux.data;
    }

    public String toString(){
        String result = "";
        Node aux = last;
        while(aux != null){
            result += " " + aux.data.toString();
            aux = aux.next;
        }
        return result;
    }

    public TD getLast() {
        TD result = null;
        if(last != null)
            result = last.data;
        return result;
    }

}
