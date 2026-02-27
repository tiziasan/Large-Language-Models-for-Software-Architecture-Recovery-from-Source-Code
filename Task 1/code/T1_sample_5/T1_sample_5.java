
package esame_03_traccia1;


public class Contatto {
    private String nome;
    private String numero;

    public Contatto(String nome, String numero) {
        this.nome = nome;
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "nome=" + nome + ", numero=" + numero;
    }
    
    
}


package esame_03_traccia1;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Gruppo {


    public static void main(String[] args) {

        Lista gruppo = new Lista();
        try {
            leggiDaFile(gruppo);
            gruppo.stampa();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        Scanner input = new Scanner (System.in);
        System.out.print("Inserisci testo da cercare: ");
        String stringa = input.next();
        gruppo.RicercaTesti(stringa);
        
        ArrayList<Vocale> mV = new ArrayList<Vocale>();
        try {
            mV=gruppo.ContattiMaxVocali();
        } catch (MiaEccezione ex) {
            System.out.println(ex.getMessage());
        }
        for(int i=0; i<mV.size(); i++){
            System.out.println(mV.get(i));
        }
    }
    
    public static void leggiDaFile(Lista l) throws IOException{
        Scanner input = new Scanner(Paths.get("gruppo.txt"));
        int messaggi = input.nextInt();
        for (int i=0; i<messaggi;i++) {
            if(input.next().equals("text")){
                l.append(new Testo(input.next(),input.nextInt(), input.nextInt(), input.next(), input.next()));
            } else l.append(new Vocale(input.nextInt(),input.nextInt(), input.nextInt(), input.next(), input.next()));
        }
        input.close();
    }
    
}


package esame_03_traccia1;

import java.util.ArrayList;

public class Lista {
    
    class Nodo {

    private Messaggio data;  
    private Nodo next;  
    
    Nodo(Messaggio elem) {data = elem; next=null;}

}
    
    Nodo testa;
    
    Lista(){ testa = null;}
    
    public boolean empty(){return testa==null;}
    public boolean full() {return false;}
    
    private void push(Messaggio e) {
        Nodo q = new Nodo(e);
        q.next = testa;
        testa = q;
    }
    
    public void append(Messaggio e) {
        if(empty()) push(e);
        else {
           Nodo temp = testa;
           Nodo q= new Nodo(e);
           while(temp.next!=null) temp = temp.next;
           temp.next = q;
        }   
    }
        
    
    public Messaggio pop() {
        Messaggio e = testa.data;
        testa = testa.next;
        return e;
    }
    
    public Messaggio top() {
        Messaggio e = testa.data;
        return e;
    }
    
    
    public boolean inLista(Messaggio e) {
        boolean trovato = false;
        Nodo temp = testa;
        while(temp!=null && !trovato)
            if(temp.data==e)
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
    
    public void RicercaTesti(String s){
        Nodo temp = testa;
        while(temp!=null) {
             if (temp.data instanceof Testo) {
                 Testo t = (Testo) temp.data;
                 if(t.getContenuto().contains(s)) {
                     System.out.println(t.toString());
                 }
             }
             temp = temp.next;
        }
    }
    
    public ArrayList<Vocale> ContattiMaxVocali() throws MiaEccezione {
        int massimaDurata = 0;
        int numVocali = 0;
        Nodo temp = testa;
        while(temp!=null) {
             if (temp.data instanceof Vocale) {
                 numVocali++;
                 Vocale v = (Vocale) temp.data;
                 if(v.getDurata()>massimaDurata) massimaDurata= v.getDurata();
             }
             temp = temp.next;
        }
        if (numVocali==0){
            throw new MiaEccezione("no Vocali");
        } else {
            ArrayList<Vocale> maxVocali = new ArrayList<Vocale>();
            temp = testa;
            while(temp!=null) {
                if (temp.data instanceof Vocale) {
                    Vocale v = (Vocale) temp.data;
                    if(v.getDurata()==massimaDurata) maxVocali.add(v);
                }
            temp = temp.next;
            }
            return maxVocali;
        }
        
    }
    
}


package esame_03_traccia1;

import java.util.Objects;

public abstract class Messaggio {
    private Orario istante;
    private Contatto mittente;

    public Messaggio(int ora, int minuto, String nome, String numero) {
        this.istante = new Orario(ora, minuto);
        this.mittente = new Contatto(nome, numero);   
    }    
    
    public String getNomeContatto(){
        return this.mittente.getNome();
    }
    
    public String getNumeroContatto(){
        return this.mittente.getNumero();
    }
    
    public int getOraIstante(){
        return this.istante.getOra();
    }
    
    public int getMinutoIstante() {
        return this.istante.getMinuto();
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
        final Messaggio other = (Messaggio) obj;
        
        if(!this.getNomeContatto().equals(other.getNomeContatto())) return false;
        
        if(!this.getNumeroContatto().equals(other.getNumeroContatto())) return false;
        
        if(this.getOraIstante()!=other.getOraIstante()) return false;
        
        if(this.getMinutoIstante()!=other.getMinutoIstante()) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public String toString() {
        return istante.toString() + ", " + mittente.toString();
    }

    
}


package esame_03_traccia1;

public class MiaEccezione extends Exception {

    public MiaEccezione(String message) {
        super(message);
    }
    
}

package esame_03_traccia1;


public class Orario {
    private int ora;
    private int minuto;

    public Orario(int ora, int minuto) {
        this.ora = ora;
        this.minuto = minuto;
    }

    public int getOra() {
        return ora;
    }

    public int getMinuto() {
        return minuto;
    }

    @Override
    public String toString() {
        return "ora=" + ora + ", minuto=" + minuto;
    }
    
    
    
}


package esame_03_traccia1;


public class Testo extends Messaggio {
    private String contenuto;

    public Testo(String contenuto, int ora, int minuto, String nome, String numero) {
        super(ora, minuto, nome, numero);
        this.contenuto = contenuto;
    }

    @Override
    public String toString() {
        return super.toString() + ", contenuto=" + contenuto;
    }

    public String getContenuto() {
        return contenuto;
    }
}


package esame_03_traccia1;


public class Vocale extends Messaggio {
    private int durata;

    public Vocale(int durata, int ora, int minuto, String nome, String numero) {
        super(ora, minuto, nome, numero);
        this.durata = durata;
    }

    @Override
    public String toString() {
        return super.toString() + ", durata=" + durata;
    }

    public int getDurata() {
        return durata;
    }
    
    
}
