
package galleria;

public class Galleria {

    public static void main(String[] args) {
        Lista l = new Lista();
        Image i = new Image (79, 100, "nomefile", 'h', true);
        System.out.println(i);
        l.push(i);
        l.stampa();
        System.out.println(i.checkQuality());
        i.resize(50);
        System.out.println(i);
    }
    
}


package galleria;


public class Image extends Multimedia {
    public int xPixel;
    public int yPixel;

    public Image(int xPixel, int yPixel, String fileName, char resolution, boolean raw) {
        super(fileName, resolution, raw);
        this.xPixel=xPixel;
        this.yPixel=yPixel;
    }
    
    @Override
    public void resize(int percentage) {
       if (percentage<=0 || percentage >100) throw new IllegalArgumentException("bad percent");
       xPixel=xPixel*percentage/100;
       yPixel=yPixel*percentage/100;
    }

    @Override
    public String toString() {
        return "xPixel=" + xPixel + ", yPixel=" + yPixel + ", " +super.toString();
    }
    
    
    
}


package galleria;



public class Lista {
    
    class Nodo {

    private Multimedia data;  
    private Nodo next;  
    
    Nodo(Multimedia elem) {data = elem; next=null;}

}
    
    Nodo testa;
    
    Lista(){ testa = null;}
    
    public boolean empty(){return testa==null;}
    public boolean full() {return false;}
    
    public void push(Multimedia e) {
        Nodo q = new Nodo(e);
        q.next = testa;
        testa = q;
    }
    
    
    
    public void elimina(Multimedia e){
        if(testa.data == e) pop();
        else {
            Nodo temp = testa;
            while(temp.next.data!=e) temp = temp.next;
            temp.next = temp.next.next;
        }
    }
    
    public Multimedia pop() {
        Multimedia e = testa.data;
        testa = testa.next;
        return e;
    }
    
    
    public boolean inLista(Multimedia e) {
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
    
}



package galleria;


public abstract class Multimedia implements Resizable {
    String fileName;
    char resolution;
    boolean raw;
    
    public Multimedia(String fileName, char resolution, boolean raw) {
        this.fileName = fileName;
        this.resolution = resolution;
        this.raw=raw;
    }

    @Override
    public String toString() {
        return "fileName=" + fileName + ", resolution=" + resolution +  ", raw="+raw;
    }
    
    @Override
    public boolean checkQuality(){
        return this.resolution == 'h' && this.raw;
    }
}

package galleria;


public interface Resizable {
    public void resize(int percentage);
    public boolean checkQuality();
}


package galleria;


public class Video extends Multimedia {
    
    public int numFrames;
    
    public Video(int numFrames, String fileName, char resolution, boolean raw) {
        super(fileName, resolution, raw);
        this.numFrames = numFrames;
    }
    
    @Override
    public void resize(int percentage) {
        
        numFrames = numFrames*percentage/100;
    }

    @Override
    public String toString() {
        return "numFrames=" + numFrames + ", "+super.toString();
    }

    
    
    
}
