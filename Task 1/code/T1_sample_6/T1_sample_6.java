package esame_03_traccia2;

import java.util.ArrayList;


public class ArticoliLettura {

    private final int Dim = 3;
    private int pos=0;
    
    private Libro articoli[];
    
    public ArticoliLettura(){
        articoli = new Libro[Dim];
    }
    
    public void inserisciLibro(Libro l) throws MiaEccezioneLibroEsistente, MiaEccezioneListaPiena{
        if(pos==Dim) throw new MiaEccezioneListaPiena();
        if(ricercaPerAutore(l)) throw new MiaEccezioneLibroEsistente();
        articoli[pos++]=l;
    }
    
    public boolean ricercaPerAutore(Libro l){
        boolean trovato=false;
        int i=0;
        while (i<pos && !trovato)
            if(articoli[i].equals(l))
                trovato=true;
            else i++;
        return trovato;
    }

    @Override
    public String toString() {
        String s ="";
        for (int i=0;i<pos;i++){
            s=s+articoli[i].toString();
        }
        return s;
    }
    
    public int[] LibriDiAutore (Autore a){
        int[] la = new int[2];
        la[0]=0;
        la[1]=0;
        for (int i=0; i<pos; i++){
            if(articoli[i].ricercaAutore(a)) {
                if (articoli[i] instanceof Cartaceo) la[0]++;
                else la[1]++;
            }
        }
        return la;
    }
}



package esame_03_traccia2;

import java.util.Objects;


public class Autore {
    private String nome;

    public Autore(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
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
        final Autore other = (Autore) obj;
        return this.nome.equals(other.getNome());
                
    }

    @Override
    public String toString() {
        return "Autore{" + "nome=" + nome + '}';
    }
    
    
}



package esame_03_traccia2;


public class Cartaceo extends Libro {
    public int pagine;

    public Cartaceo(int pagine, int num_autori, String titolo, Autore a) throws IllegalArgumentException {
        super(num_autori, titolo, a);
        this.pagine = pagine;
    }

    @Override
    public String toString() {
        String s = "Cartaceo{" + "pagine=" + pagine + '}'+"\n";
        s=s+super.toString();
        return s;
    }
    
}




package esame_03_traccia2;


public class Digitale extends Libro {
    public int kByte;

    public Digitale(int kByte, int num_autori, String titolo, Autore a) throws IllegalArgumentException {
        super(num_autori, titolo, a);
        this.kByte = kByte;
    }

    @Override
    public String toString() {
        String s =  "Digitale{" + "kByte=" + kByte + '}'+"\n";
        s=s+super.toString();
        return s;
    }
    
}


package esame_03_traccia2;


public class GestoreArrticoli {


    public static void main(String[] args) {
        Autore a1 = new Autore("Autore1");
        Autore a2 = new Autore("Autore2");
        Autore a3 = new Autore("Autore3");
        Autore a4 = new Autore("Autore4");
        Autore a1_1 = new Autore("Autore1");

        try {
            Cartaceo lc1 = new Cartaceo(40, 2, "LibroCartaceo1", a1);
            lc1.add_autore(a3);
            Cartaceo lc2 = new Cartaceo(40, 2, "LibroCartaceo2", a1);
            lc2.add_autore(a4);
            Digitale ld1 = new Digitale(40, 2, "LibroDigitale1", a3);
            ld1.add_autore(a4);
            ArticoliLettura al= new ArticoliLettura();
            al.inserisciLibro(lc1);
            al.inserisciLibro(lc2);
            al.inserisciLibro(ld1);
            int[] libriAutore = new int[2];
            libriAutore = al.LibriDiAutore(a3);
            System.out.println("Cartacei: "+libriAutore[0]+ " Digitali: "+libriAutore[1]);
            
        } catch (IllegalArgumentException | ArrayStoreException | MiaEccezioneLibroEsistente | MiaEccezioneListaPiena ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}


package esame_03_traccia2;

import java.util.Objects;


public abstract class Libro {
    private int num_autori;
    private int posizione;
    private String titolo;
    private Autore[] autori;

    public Libro(int num_autori, String titolo, Autore a) throws IllegalArgumentException {
        
        if (num_autori<=0) throw new IllegalArgumentException("la lista degli autori non può essere vuota");
        this.num_autori = num_autori;
        this.titolo = titolo;
        this.autori = new Autore[num_autori];
        this.posizione=0;
        
        this.add_autore(a);
    }

    public String getTitolo() {
        return titolo;
    }
    
    
    
    public void add_autore(Autore a) throws ArrayStoreException{

        if (posizione<num_autori) this.autori[this.posizione++]=a;
        else throw new ArrayStoreException("Lista autori piena");
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
        final Libro other = (Libro) obj;
        return this.titolo.equals(other.getTitolo());
    }

    @Override
    public String toString() {
        String s = "Libro{" + "titolo=" + titolo + '}';
        for (int i=0; i<this.posizione; i++){
            s=s+"\n"+this.autori[i].toString();
        }
        return s;
    }
    
    public boolean ricercaAutore(Autore a){
        boolean trovato=false;
        int i=0;
        while (i<posizione && !trovato)
            if(autori[i].equals(a))
                trovato=true;
            else i++;
        return trovato;
    }
    
}


package esame_03_traccia2;

public class MiaEccezioneLibroEsistente extends Exception {

    public MiaEccezioneLibroEsistente() {
        super("Il libro già esiste in elenco");
    }
    
}


package esame_03_traccia2;


public class MiaEccezioneListaPiena extends Exception {

    public MiaEccezioneListaPiena() {
        super("Non si possono aggiungere altri libri");
    }
    
}
