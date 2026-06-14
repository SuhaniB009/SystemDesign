//package ProxyDesignPattern.ProtectionProxy;

interface IDocumentReader{
    public void unlockPDF(String filePath, String password);
}
class RealDocumentReader implements IDocumentReader{
    @Override
    public void unlockPDF(String filePath,String pass){
       System.out.println("Unlocking pdf at : "+filePath);
       System.out.println("PDF unlocked successfully with password:"+pass);
       System.out.println("Displaying content");
    }
}
class User{
    public String  name;
    public boolean isPremium;

    public User(String n, boolean isPremium){
        this.name=n;
        this.isPremium=isPremium;
    }
}
class DocumentProxy implements IDocumentReader{
    private User user;
    private RealDocumentReader realReader;
    public DocumentProxy(User user ){
        this.user=user;
        this.realReader=new RealDocumentReader();
    }
    @Override
    public void unlockPDF(String filePath,String password){
       if(!user.isPremium){
        System.out.println("[DocumentProxy]Access denied: Only premium members can unlock PDF");
        return ;
       }
       realReader.unlockPDF(filePath, password);
    }
}

public class ProtectionProxy {
    public static void main(String[] args){
        User user2= new User("Suhani",true);
        User user1= new User("Rohan",false);
        
        System.err.println("-----Rohan tries to unlock pdf------ ");
        IDocumentReader docReader= new DocumentProxy(user1);
        docReader.unlockPDF("protectedDocument.pdf", "secret123");

        System.out.println("------Suhani tries to unlock pdf-----");
        docReader = new DocumentProxy(user2);
        docReader.unlockPDF("protectedDocument.pdf", "secret123");

    }
}
