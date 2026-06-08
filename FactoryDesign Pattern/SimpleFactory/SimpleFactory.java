//package FactoryDesign Pattern;

interface Burger{
   void prepare();
}
class BasicBurger implements Burger{
    @Override
    public void prepare(){
        System.out.println("Preparing basic burger with bun, patty and ketcup");
    }
}
class StandardBurger implements Burger{
    @Override
    public void prepare(){
        System.out.println("Preparing standard burger with bun, patty, cheese and lettuce");
    }
}
class PremiumBurger implements Burger{
    @Override
    public void prepare(){
       System.out.println("Preparing Premium burger with gourmet bun, premium patty, cheese ,lettuce and secret sauce");
    }
}

class BurgerFactory{
    public Burger createBurger(String type){
        if (type.equalsIgnoreCase("Basic")){
            return new BasicBurger();
        }else if (type.equalsIgnoreCase(("Standard"))){
            return new StandardBurger();
        }
        else if (type.equalsIgnoreCase("Premium")){
            return new PremiumBurger();
        }
        else {
            System.out.println("Invalid Burger type");
            return null;
        }
    }
}

public class SimpleFactory {
    public static void main(String[] args){
        String type="Premium";
        BurgerFactory factory= new BurgerFactory();
        Burger burger=factory.createBurger(type);
        if(burger!=null){
            burger.prepare();
        }
    }
}
