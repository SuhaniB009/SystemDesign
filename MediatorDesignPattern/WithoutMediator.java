package MediatorDesignPattern;

import java.util.*;

class User{
    private String name;
    private List<User> peers;
    private List<String> mutedPeers;

    public User(String n){
        name=n;
        peers=new ArrayList<>();
        mutedPeers=new ArrayList<>();
    }
    public void addPeer(User u){
        peers.add(u);
    }
    public void mute(String muteUser){
        mutedPeers.add(muteUser);
    }
    public boolean isMuted(String userName){
        for(String n:mutedPeers){
            if(n.equals(userName)){
                return true;
            }
        }
        return false;
    }
    public void send(String msg){
        System.out.println("["+name+" broadcasts]: "+msg);
        for(User peer:peers){
            if(!peer.isMuted(name)){
               peer.receive(name,msg);
            }
        }
    }
    public void sendTo(String msg,User target){
        System.out.println("["+name+" broadcasts to "+target.name+"]: "+msg);
        if(!target.isMuted(name)){
            target.receive(name,msg);
        }
    }
    public void receive(String from, String msg){
        System.out.println(" "+name +" got from "+from +": "+msg);
    }
}
public class WithoutMediator {
    public static void main (String []args){
        User user1 = new User("Rohan");
        User user2 = new User("Mohan");
        User user3 = new User("Sohan");

        user1.addPeer(user2);
        user1.addPeer(user3);

        user2.addPeer(user3);
        user2.addPeer(user1);

        user3.addPeer(user1);
        user3.addPeer(user2);

        user1.mute("Mohan");

        user1.send("Hey guyzz");

        user2.send("Hello ");
        user3.sendTo("Hey Rohan", user1);

    }
}
