//package ProxyDesignPattern;

interface IImage{
    public void display();
}

class RealImage implements IImage{
    String filename;
    public RealImage(String file){
        this.filename=file;
        System.out.println("[RealImage] Loading the image from disk: "+ filename);
    }
    @Override
    public void display(){
        System.out.println("[RealImage] Displaying "+filename);
    }
}
class ProxyImage implements IImage{
    RealImage realimage;
    String filename;
    public ProxyImage(String file){
        this.realimage=null;
        this.filename=file;
    }
    public void display(){
    if(realimage==null){
        realimage= new RealImage(filename);
    }
    realimage.display();
    }
}

public class VirtualProxy {
    public static void main(String[] args){
        IImage image1 =new ProxyImage("photo.jpg") ;
        image1.display();
    }
}
