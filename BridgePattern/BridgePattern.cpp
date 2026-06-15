#include<iostream>
#include<string>

using namespace std;

class Engine{//implemantion(low level layer)
    public:
    virtual void start()=0;
    virtual ~Engine(){}
};

class PetrolEngine:public Engine{//concrete Implementor
public:
    void start()override{
        cout<<"Petrol engine starting with ignition!"<<endl;
    }
};
class DieselEngine:public Engine{
public:
    void start()override{
        cout<<"Diesel engine starting!!"<<endl;
    }
};
class ElectricEngine:public Engine{
public:
    void start()override{
        cout<<"Electric engine powering up!!"<<endl;
    }
};

class Car{//abstraction (High level layer)
protected:
    Engine* engine;
public:
    Car(Engine* engine){
        this->engine=engine;
    }
    virtual void drive()=0;
};
class Sedan:public Car{
public:
    Sedan(Engine* e):Car(e){}

    void drive()override{
        engine->start();
        cout<<"Driving in Sedan in highway"<<endl;
    }
};
class SUV:public Car{
    public:
    SUV(Engine* e):Car(e){}

    void drive(){
        engine->start();
        cout<<"Driving in SUV off road"<<endl;
    }
};

int main(){
    Engine* petrolEngine= new PetrolEngine();
    Engine* dieselEngine= new DieselEngine();
    Engine* electricEngine= new ElectricEngine();

    Car* sedan= new Sedan(petrolEngine);
    Car* suv= new SUV(electricEngine);

    sedan->drive();
    suv->drive();

    //delete sedan;
    //delete suv;
    return 0;
}