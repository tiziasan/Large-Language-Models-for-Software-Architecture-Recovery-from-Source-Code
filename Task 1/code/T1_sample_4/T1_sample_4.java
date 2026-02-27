
package esame_02_traccia2;

public class Audio extends Canale {
    private int audioQuality;

    public Audio(int audioQuality, int frequenza, String nomeCanale) {
        super(frequenza, nomeCanale);
        this.audioQuality = audioQuality;
    }

    @Override
    public void tune(int value) throws IllegalArgumentException {
        if (value == 0)
            throw new IllegalArgumentException("valore nullo");
        this.audioQuality= this.audioQuality+value;
    }   

    @Override
    public String toString() {
        return "audioQuality=" + audioQuality + ", "+ super.toString();
    }
    
    
}


package esame_02_traccia2;


public abstract class Canale implements Tunable, Comparable<Canale> {
    private int frequenza;
    private String nomeCanale;

    public int getFrequenza() {
        return frequenza;
    }

    public Canale(int frequenza, String nomeCanale) {
        this.frequenza = frequenza;
        this.nomeCanale = nomeCanale;
    }
    
    
    @Override
    public int compareTo (Canale c) {
        return (this.frequenza - c.getFrequenza());
    }    

    @Override
    public String toString() {
        return  "frequenza=" + frequenza + ", nomeCanale=" + nomeCanale;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Canale other = (Canale) obj;
        return this.frequenza == other.frequenza;
    }
            
    
    
    
}

package esame_02_traccia2;


public class Esame_02_Traccia2 {

    public static void main(String[] args) {
        Audio a = new Audio (3, 4, "RadioDeejay");
        Audio a1 = new Audio (3, 13, "RadioRTL");
        Video v = new Video (4, 3, 11,"RaiTre Campania");
        Video v1 = new Video (2, 0,11,"RaiTre Puglia");
        Video v3 = new Video (1, 1,11,"RaiTre Abruzzo");
        Audio a2 = new Audio (3, 4, "RadioDeejay HD");
        Video v4 = new Video (2,9, 12, "Canale 5");
        Video v5 = new Video (3,3,13,"Italia 1");
        
        Lista l = new Lista();
        
        l.inserisci(v);
        l.inserisci(a);
        l.inserisci(a2);
        l.inserisci(a1);
        l.inserisci(v1);
        l.inserisci(v3);
        l.inserisci(v4);
        l.inserisci(v5);
        System.out.println("Lista iniziale");
        l.stampa();
        System.out.println("Riordinamento");
        l.canaliRipetuti();
    }
    
}


package esame_02_traccia2;

import java.util.ArrayList;



public class Lista {
    
    class Nodo {

    private Canale data;  
    private Nodo next;  
    
    Nodo(Canale elem) {data = elem; next=null;}

}
    
    Nodo testa;
    
    Lista(){ testa = null;}
    
    public boolean empty(){return testa==null;}
    public boolean full() {return false;}
    
    public void push(Canale e) {
        Nodo q = new Nodo(e);
        q.next = testa;
        testa = q;
    }
    
    public void append(Canale e) {
        if(empty()) push(e);
        else {
           Nodo temp = testa;
           Nodo q= new Nodo(e);
           while(temp.next!=null) temp = temp.next;
           temp.next = q;
        }   
    }
    
     public void inserisci(Canale e) {
       if(empty() || testa.data.compareTo(e)>=0) push(e);
           else {
             Nodo temp = testa;
             Nodo q = new Nodo(e);
             while(temp.next!=null && temp.next.data.compareTo(e)<0)
                 temp = temp.next;
             q.next = temp.next;
             temp.next = q;
       }
     }
    
    public Canale pop() {
        Canale e = testa.data;
        testa = testa.next;
        return e;
    }
    
    public Canale top() {
        Canale e = testa.data;
        return e;
    }
    
    public Canale pop_back() {
        if(testa.next==null) return pop();
        else {
            Nodo temp = testa;
            while(temp.next.next!=null)  temp = temp.next;
            Canale e = temp.next.data;
            temp.next=null;
            return e;
        } 
    }
    
    public Canale top_back() {
        if(testa.next==null) return top();
        else {
            Nodo temp = testa;
            while(temp.next.next!=null)  temp = temp.next;
            Canale e = temp.next.data;
            return e;
        } 
    }
    
    public void elimina(Canale e){
        if(testa.data.equals(e)) pop();
        else {
            Nodo temp = testa;
            while(!temp.next.data.equals(e)) temp = temp.next;
            temp.next = temp.next.next;
        }
    }
    
    
    public boolean inLista(Canale e) {
        boolean trovato = false;
        Nodo temp = testa;
        while(temp!=null && !trovato)
            if(temp.data.equals(e))
                trovato = true;
            else temp = temp.next;
        return trovato;
    }
            

    public void stampa(){
        Nodo temp = testa;
        while(temp!=null) {
             System.out.println(temp.data);
             temp = temp.next;
        }
    }
    
    public void canaliRipetuti(){
        ArrayList<Canale> cs = new ArrayList<Canale>();
        ArrayList<Canale> cr = new ArrayList<Canale>();
        Nodo temp = testa;
        while(temp!=null) {
            if (!cs.contains(temp.data)){
                cs.add(temp.data);
            } else {
                cr.add(temp.data);
            }
            temp = temp.next;
        }
        
        for (int i=0; i<cs.size(); i++){
            if(cr.contains(cs.get(i)))
                System.out.println(cs.get(i));
            for(int j=0;j<cr.size();j++){
                if (cr.get(j).equals(cs.get(i)))
                        System.out.println(cr.get(j));
            }
        }
    }
    
    public void modificaLista(){
        Nodo temp = testa;
        ArrayList<Canale> a = new ArrayList<Canale>();
        while(temp!=null) {
            if(temp.data instanceof Audio){
                a.add(temp.data);
            }
            temp= temp.next;
        }
        for (int i=0; i<a.size(); i++){
            append(a.get(i));
            elimina(a.get(i));
        }
    
    }
    
    
    
}


package esame_02_traccia2;


public interface Tunable {
    public void tune(int value) throws IllegalArgumentException;
}


package esame_02_traccia2;


public class Video extends Canale {
    
    private int videoQuality;
    private int resolution;

    public Video(int videoQuality, int resolution, int frequenza, String nomeCanale) {
        super(frequenza, nomeCanale);
        this.videoQuality = videoQuality;
        this.resolution = resolution;
    }
    
    @Override
    public void tune(int value) throws IllegalArgumentException{
        if (value == 0)
            throw new IllegalArgumentException("valore nullo");
        this.videoQuality = this.videoQuality*value;
        if (value<0)
            this.resolution--;
            else this.resolution++;
    }

    @Override
    public String toString() {
        return "videoQuality=" + videoQuality + ", resolution=" + resolution + ", "+super.toString();
    }
    
    
}
