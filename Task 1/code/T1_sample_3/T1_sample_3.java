
package esame_02_traccia1;


public class Automobile extends VeicoloAutonomo {
    private int velocitaCorrente;

    public Automobile(int velocitaCorrente, int carica, int kmPercorsi, String Id) {
        super(carica, kmPercorsi, Id);
        this.velocitaCorrente = velocitaCorrente;
    }

    @Override
    public String toString() {
        return super.toString() + "velocitaCorrente=" + velocitaCorrente;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Automobile other = (Automobile) obj;
        return super.equals(other) &&
                this.velocitaCorrente == other.velocitaCorrente;
    }

    @Override
    public void increase(int value) {
        this.velocitaCorrente = this.velocitaCorrente+value;
    }

    
    
    
}

package esame_02_traccia1;


public class Drone extends VeicoloAutonomo {
    private int altezzaCorrente;

    public Drone(int altezzaCorrente, int carica, int kmPercorsi, String Id) {
        super(carica, kmPercorsi, Id);
        this.altezzaCorrente = altezzaCorrente;
    }

    @Override
    public String toString() {
        return super.toString() + "altezzaCorrente=" + altezzaCorrente;
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
        final Drone other = (Drone) obj;
        return super.equals(other) &&
                this.altezzaCorrente == other.altezzaCorrente;
    }

    @Override
    public void increase(int value) {
        this.altezzaCorrente = this.altezzaCorrente+value;
    }
    
    
}


package esame_02_traccia1;


public class Esame_02_Traccia1 {

    public static void main(String[] args) {
        Automobile a1= new Automobile (12, 3, 4, "Audi");
        Automobile a2= new Automobile (12, 2, 13, "Audi");
        Automobile a3 = new Automobile (11, 9, 45, "BMW");
        
        
        Drone dr1 = new Drone(23,2,7, "Drxon");
        
        Drone dr2 = new Drone(22,11,7, "DJI");
        
        Drone di = new Drone(32, 4, 9, "AutelRobotics");
        
        Lista l = new Lista();
        System.out.println("Lista iniziale");
        l.push(dr1);
        l.push(dr2);
        l.push(a1);
        l.push(a2);
        l.push(a3);
        l.stampa();
        System.out.println("\nInseriamno il veicolo:");
        System.out.println(di);
        l.inserisciConOrdine(di);
        System.out.println("\nLista dopo chiamata del metodo");
        l.stampa();
        
        
        
        
        
    }
    
}


package esame_02_traccia1;


public interface Increasable {
    public void increase(int value);
}


package esame_02_traccia1;

import java.util.ArrayList;


public class Lista {
    
    class Nodo {

    private VeicoloAutonomo data;  
    private Nodo next;  
    
    Nodo(VeicoloAutonomo elem) {data = elem; next=null;}

}
    
    Nodo testa;
    
    Lista(){ testa = null;}
    
    public boolean empty(){return testa==null;}
    public boolean full() {return false;}
    
    public void push(VeicoloAutonomo e) {
        Nodo q = new Nodo(e);
        q.next = testa;
        testa = q;
    }
    
    public void append(VeicoloAutonomo e) {
        if(empty()) push(e);
        else {
           Nodo temp = testa;
           Nodo q= new Nodo(e);
           while(temp.next!=null) temp = temp.next;
           temp.next = q;
        }   
    }
    
     public void inserisci(VeicoloAutonomo e) {
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
    
    public VeicoloAutonomo pop() {
        VeicoloAutonomo e = testa.data;
        testa = testa.next;
        return e;
    }
    
    public VeicoloAutonomo top() {
        VeicoloAutonomo e = testa.data;
        return e;
    }
    
    public VeicoloAutonomo pop_back() {
        if(testa.next==null) return pop();
        else {
            Nodo temp = testa;
            while(temp.next.next!=null)  temp = temp.next;
            VeicoloAutonomo e = temp.next.data;
            temp.next=null;
            return e;
        } 
    }
    
    public VeicoloAutonomo top_back() {
        if(testa.next==null) return top();
        else {
            Nodo temp = testa;
            while(temp.next.next!=null)  temp = temp.next;
            VeicoloAutonomo e = temp.next.data;
            return e;
        } 
    }
    
    public void elimina(VeicoloAutonomo e){
        if(testa.data.equals(e)) pop();
        else {
            Nodo temp = testa;
            while(!temp.next.data.equals(e)) temp = temp.next;
            temp.next = temp.next.next;
        }
    }
    
    
    public boolean inLista(VeicoloAutonomo e) {
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
    
    public void inserisciConOrdine(VeicoloAutonomo e){
        this.append(e);
        ArrayList<VeicoloAutonomo> vaal = new ArrayList<VeicoloAutonomo>();
        Nodo temp = testa;
        while(temp.next!=null){
            if(temp.data.compareTo(e)<0) vaal.add(temp.data);
            temp=temp.next;
        }
        for(int i=0; i<vaal.size();i++){
            this.append(vaal.get(i));
            this.elimina(vaal.get(i));
        }
    }
    
    
}



package esame_02_traccia1;

import java.util.Objects;


public abstract class VeicoloAutonomo implements Comparable<VeicoloAutonomo>, Increasable {
    private int carica;
    private int kmPercorsi;
    private String Id;

    public VeicoloAutonomo(int carica, int kmPercorsi, String Id) {
        this.carica = carica;
        this.kmPercorsi = kmPercorsi;
        this.Id = new String(Id);
    }
    
    
    public int getCarica() {
        return carica;
    }

    @Override
    public String toString() {
        return "carica=" + carica + ", kmPercorsi=" + kmPercorsi + ", Identificativo=" + Id +", ";
    }
    
    
    @Override
    public int compareTo (VeicoloAutonomo va) {
        return (this.carica - va.getCarica());
    }    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.Id);
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
        final VeicoloAutonomo other = (VeicoloAutonomo) obj;
        return Objects.equals(this.Id, other.Id);
    }

    
    
    
    
}
