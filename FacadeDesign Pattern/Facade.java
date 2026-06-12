class PowerSupply{
    public void providepower(){
        System.out.println("Power Supply: Providing power");
    }
}
class CoolingSystem{
    public void startfan(){
        System.out.println("Cooling System: Starting fan");
    }
}
class CPU{
    public void initialize(){
        System.out.println("CPU: Initialization started");
    }
}
class Memory{
    public void selfTest(){
        System.out.println("Memory: Self Test started");
    }
}
class HardDrive{
    public void spinup(){
     System.out.println("Hard Drive :Spinning up");
    }
}
class BIOS{
    public void boot(CPU cpu,Memory memory ){
        System.out.println("BIOS : Booting CPU and checking Memory ");
        cpu.initialize();
        memory.selfTest();
    }
}
class OperatingSystem{
    public void load(){
        System.out.println("Operating system: Loading into memory");
    }
}

class ComputerFacade{
    PowerSupply powerSupply=new PowerSupply();
    CoolingSystem coolingSystem =new CoolingSystem();
    CPU cpu= new CPU();
    Memory memory= new Memory();
    HardDrive hardDrive= new HardDrive();
    BIOS bios=new BIOS();
    OperatingSystem os= new OperatingSystem();
    
    public void StartComputer(){
        System.out.println("-------Staring Computer-------");
        powerSupply.providepower();
        coolingSystem.startfan();
        bios.boot(cpu, memory);
        hardDrive.spinup();
        os.load();
        System.out.println("Computer Booted Successfully");
    }
}

public class Facade{
    public static void main(String[] args){
        ComputerFacade computerFacade=new ComputerFacade();
        computerFacade.StartComputer();
    }
}