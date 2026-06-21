package PrototypeDesignPattern;
class NPC{
    public String name;
    public int health;
    public int attack;
    public int defense;
    public NPC(String n,int h,int a,int d){
        this.name=n;
        this.health=h;
        this.attack=a;
        this.defense=d;
        System.out.println("Creatig NPC "+name+"[HP: "+health+", ATK: "+attack+", DEF: "+defense);

    }
    public void describe(){
        System.out.println("NPC: "+name+" name"+" | HP= "+health+" | ATK= "+attack+" | DEF= "+defense);

    }
}
public class WithoutPrototype {
    public static void main(String[]args){
        NPC alien=new NPC("Alien", 30,5,2);
        alien.describe();
        NPC Alien2=new NPC("Powerful Alien",30,5,5);
        Alien2.describe();
        
    }
}
