interface Cloneable{
    Cloneable clone();
}
class NPC implements Cloneable{
    public String name;
    public int health;
    public int attack;
    public int defense;

    public NPC(String name,int health,int attack,int defense){
        this.name=name;
        this.health=health;
        this.attack=attack;
        this.defense=defense;
        System.out.println("Setting up template NPC "+name+ "'");
    }
    public NPC(NPC other){
        name=other.name;
        health=other.health;
        attack=other.attack;
        defense=other.defense;
        System.out.println("Cloning NPC "+name+"'");
    }
    public Cloneable clone(){
        return new NPC(this);
    }
    public void describe(){
       System.out.println("NPC " + name + " [HP=" + health + " ATK=" + attack 
            + " DEF=" + defense + "]");
   }
   public void setName(String n){
    name=n;
   }
   public void setHealth(int h){
    health=h;
   }
   public void setAttack(int a){
    attack=a;
   }
   public void setDefense(int d){
    defense=d;
   }
    
}
public class WithPrototype{
    public static void main(String[]args){
        NPC alien=new NPC("Alien",30,5,2);
        NPC copiedAlien=(NPC)alien.clone();
        copiedAlien.describe();
        NPC copiedAlien2= (NPC)alien.clone();
        copiedAlien2.setName("Powerful alien");
        copiedAlien.setHealth(50);
        copiedAlien2.describe();

        alien=null;
        copiedAlien=null;
        copiedAlien2=null;
    }
}