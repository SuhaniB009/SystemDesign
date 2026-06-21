package MediatorDesignPattern.WithMediator;
import java.util.*;

interface IMediator{
    void registerColleague(Colleague c);
    void send(String from ,String msg);
    void sendPrivate(String from,String to,String msg);
}
abstract class Colleague{
    protected IMediator mediator;
    public Colleague(IMediator m){
        mediator=m;
        mediator.registerColleague(this);
    }
    public abstract String getName();
    public abstract void send(String msg);
    public abstract void sendPrivate(String to, String msg);
    public abstract void receive(String from ,String msg);

}
class ChatMediator implements IMediator{
    private List<Colleague> colleagues;
    private HashMap<String,Set<String>> mutes;
    public ChatMediator(){
        colleagues=new ArrayList<>();
        mutes= new HashMap<>();
    }
    public void registerColleague(Colleague c){
        colleagues.add(c);
    }
    public void mute(String who,String whom){
        if(!mutes.containsKey(who)){
            mutes.put(who,new HashSet<>());
        }
        mutes.get(who).add(whom);
    }
    public void send(String from,String msg){
        System.out.println("["+from+"broadcasts]: "+msg);
        for(Colleague c:colleagues){
            if(c.getName().equals(from))continue;
            boolean isMuted =false;
            if(mutes.containsKey(c.getName()) && 
            mutes.get(c.getName()).contains(from)){
                isMuted=true;
            }
            if(!isMuted){
                c.receive(from,msg);
            }
        }
    }
    public void sendPrivate(String from,String to,String msg){
        System.out.println("["+from+" to "+"]: "+msg);
        for(Colleague c: colleagues){
            if(c.getName().equals(to)){
                if(mutes.containsKey(c.getName()) &&
            mutes.get(c.getName()).contains(from)){
                System.out.println("\n Message is muted ");
                return ;
            }
            c.receive(from, msg);
            return ;
            }
        }
        System.out.println("[Mediator] User \""+to+"\" not found]");
    }

}
class User extends Colleague{
    private String name;
    public User(String n,IMediator m){
        super(m);
        name=n;
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public void send(String msg){
        mediator.send(name,msg);
    }
    @Override
    public void sendPrivate(String to, String msg){
        mediator.sendPrivate(name,to,msg);
    }
    @Override
    public void receive(String from,String msg){
        System.out.println("    " + name + " got from " + from + ": " + msg);
    }
}
public class WithMediator {
    public static void main(String[] args){
        ChatMediator chatRoom= new ChatMediator();
        User user1 = new User("Rohan", chatRoom);
        User user2 = new User("Neha", chatRoom);
        User user3 = new User("Mohan", chatRoom);
        chatRoom.mute("Rohan","Mohan");
        user3.send("Hello Everyone!");
        user3.sendPrivate("Neha", "Hey Neha!");

    }
}
