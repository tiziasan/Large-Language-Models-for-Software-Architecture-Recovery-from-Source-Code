
package VulnScanner;


public interface Exploitable {
    public boolean checkExploitable();
    public void fix();
}

package VulnScanner;

import java.util.Objects;


public class HWVulnerability extends Vulnerability {
    String device;
    boolean connected=true;

    public HWVulnerability(String device, int severity, boolean attackPattern) {
        super(severity, attackPattern);
        this.device = new String(device);
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
        final HWVulnerability other = (HWVulnerability) obj;
        return Objects.equals(this.device, other.device);
    }

    @Override
    public String toString() {
        return this.getClass().getName() +  ", device=" + device + ", connected=" + connected + ", "+super.toString();
    }
    
    
    @Override
    public void fix() {
        if(this.checkExploitable()) this.connected=false;
    }
}

package VulnScanner;

import java.util.ArrayList;


public class Lista {
    
    class Nodo {

    private Vulnerability data;  
    private Nodo next;  
    
    Nodo(Vulnerability elem) {data = elem; next=null;}

}
    
    Nodo testa;
    
    Lista(){ testa = null;}
    
    public boolean empty(){return testa==null;}
    public boolean full() {return false;}
    
    public void push(Vulnerability e) {
        Nodo q = new Nodo(e);
        q.next = testa;
        testa = q;
    }
    
    public void append(Vulnerability e) {
        if(empty()) push(e);
        else {
           Nodo temp = testa;
           Nodo q= new Nodo(e);
           while(temp.next!=null) temp = temp.next;
           temp.next = q;
        }   
    }
    
    
    public Vulnerability pop() {
        Vulnerability e = testa.data;
        testa = testa.next;
        return e;
    }
    
    
    public void elimina(Vulnerability e){
        if(testa.data.equals(e)) pop();
        else {
            Nodo temp = testa;
            while(!temp.next.data.equals(e)) temp = temp.next;
            temp.next = temp.next.next;
        }
    }
    
    
    
    public boolean inLista(Vulnerability e) {
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
    
    public int[] numberExploitable() {
        int explSW=0;
        int explHW=0;
        int [] expl = new int[2];
        Nodo temp = testa;
        while(temp!=null) {
             if(temp.data.checkExploitable() && temp.data instanceof SWVulnerability) explSW++;
             if(temp.data.checkExploitable() && temp.data instanceof HWVulnerability) explHW++;
             temp = temp.next;
        }
        expl[0]=explSW;
        expl[1]=explHW;
        return expl;
    }
    
    public void removeNotExploitable(){
        Nodo temp = testa;
        ArrayList<Vulnerability> ne = new ArrayList<Vulnerability>();
        while(temp!=null) {
            if(!temp.data.checkExploitable()) ne.add(temp.data);
            temp=temp.next;
        }
       
        for(int i=0; i<ne.size();i++) {
            elimina(ne.get(i));
        }
    }
    
}


package VulnScanner;

import java.util.Objects;


public class SWVulnerability extends Vulnerability {
    String software;
    int ports;

    public SWVulnerability(String software, int ports, int severity, boolean attackPattern) {
        super(severity, attackPattern);
        this.software = software;
        this.ports = ports;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final SWVulnerability other = (SWVulnerability) obj;
        return Objects.equals(this.software, other.software);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + ", software=" + software + ", ports=" + ports + ", "+super.toString();
    }

    
    @Override
    public void fix() {
        if (this.checkExploitable()) this.ports = 0;
    }
    
    
}


package VulnScanner;

public abstract class Vulnerability implements Exploitable {
    public int severity;
    public boolean attackPattern;

    public Vulnerability(int severity, boolean attackPattern) {
        this.severity = severity;
        this.attackPattern = attackPattern;
    }
    
    @Override 
    public boolean checkExploitable() {
        return this.severity >= 2 && this.attackPattern;
    }

    @Override
    public String toString() {
        return  "severity=" + severity + ", attackPattern=" + attackPattern;
    }
    
    
}


package VulnScanner;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;


public class VulnerabilityScanner {


    public static void main(String[] args) throws IOException {
        Lista v = new Lista();
        
        leggiDaFile(v);
        v.stampa();
        System.out.println(v.numberExploitable());
        v.removeNotExploitable();
        System.out.println("Dopo Rimozione");
        v.stampa();
    }
    
    public static void leggiDaFile(Lista l) throws IOException{
        Scanner input = new Scanner(Paths.get("vulnerabilities.txt"));
        int vulnerabilita = input.nextInt();
        for (int i=0; i<vulnerabilita;i++) {
            if(input.next().equals("sw")){
                l.append(new SWVulnerability(input.next(),input.nextInt(), input.nextInt(), input.nextBoolean()));
            } else l.append(new HWVulnerability(input.next(), input.nextInt(), input.nextBoolean()));
        }
        input.close();
    }
}
