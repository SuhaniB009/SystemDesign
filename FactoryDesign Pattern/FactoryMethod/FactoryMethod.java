//package FactoryDesign Pattern.FactoryMethod;


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
class BasicWheatBurger implements Burger {
    public void prepare() {
        System.out.println("Preparing Basic Wheat Burger with bun, patty, and ketchup!");
    }
}

class StandardWheatBurger implements Burger {
    public void prepare() {
        System.out.println("Preparing Standard Wheat Burger with bun, patty, cheese, and lettuce!");
    }
}

class PremiumWheatBurger implements Burger {
    public void prepare() {
        System.out.println("Preparing Premium Wheat Burger with gourmet bun, premium patty, cheese, lettuce, and secret sauce!");
    }
}

interface BurgerFactory{
    Burger createBurger(String type);
}
class SinghBurger implements BurgerFactory{
    @Override
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
class KingBurger implements BurgerFactory {
    public Burger createBurger(String type) {
        if (type.equalsIgnoreCase("basic")) {
            return new BasicWheatBurger();
        } else if (type.equalsIgnoreCase("standard")) {
            return new StandardWheatBurger();
        } else if (type.equalsIgnoreCase("premium")) {
            return new PremiumWheatBurger();
        } else {
            System.out.println("Invalid burger type!");
            return null;
        }
    }
}

public class FactoryMethod {
    public static void main(String[] args){
        String type="Premium";
        BurgerFactory factory= new SinghBurger();
        Burger burger=factory.createBurger(type);
        if(burger!=null){
            burger.prepare();
        }
    }
}
