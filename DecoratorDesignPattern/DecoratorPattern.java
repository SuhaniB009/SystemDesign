interface Character {
    String getAbilities();
}

class Mario implements Character {
    public String getAbilities() {
        return "Mario";
    }
}abstract class CharacterDecorator implements Character {
    protected Character character;  
    public CharacterDecorator(Character c) {
        this.character = c;
    }
}class HeightUp extends CharacterDecorator {
    public HeightUp(Character c) {
        super(c);
    }

    public String getAbilities() {
        return character.getAbilities() + " with HeightUp";
    }
}class GunPowerUp extends CharacterDecorator {
    public GunPowerUp(Character c) {
        super(c);
    }

    public String getAbilities() {
        return character.getAbilities() + " with Gun";
    }
}class StarPowerUp extends CharacterDecorator {
    public StarPowerUp(Character c) {
        super(c);
    }

    public String getAbilities() {
        return character.getAbilities() + " with Star Power (Limited Time)";
    }
}

public class DecoratorPattern {
    public static void main(String[] args) {
        Character mario = new Mario();
        System.out.println("Basic Character: " + mario.getAbilities());
        mario = new HeightUp(mario);
        System.out.println("After HeightUp: " + mario.getAbilities());
        mario = new GunPowerUp(mario);
        System.out.println("After GunPowerUp: " + mario.getAbilities());
        mario = new StarPowerUp(mario);
        System.out.println("After StarPowerUp: " + mario.getAbilities());
    }
}