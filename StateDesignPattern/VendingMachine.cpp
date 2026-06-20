#include<iostream>
#include <string>
using namespace std;

class VendingMachine;
class NoCoinState;
class HasCoinState;
class DispenseState;
class SoldOutState;

class VendingState{
public:
    virtual VendingState* insertCoin(VendingMachine* machine,int coin)=0;
    virtual VendingState* selectItem(VendingMachine* machine)=0;
    virtual VendingState* dispense(VendingMachine* machine)=0;
    virtual VendingState* returnCoin(VendingMachine* machine)=0;
    virtual VendingState* refill(VendingMachine* machine,int q)=0;
    virtual string getStateName()=0;
};
class VendingMachine{
    private:
    VendingState* currState;
    int itemCount;
    int itemPrice;
    int insertedCoins;

    VendingState* noCoinState;
    VendingState* hasCoinState;
    VendingState* dispenseState;
    VendingState* soldOutState;

    public:
    VendingMachine(int itemCount,int itemPrice);
    void insertCoin(int coin);
    void selectItem();
    void dispense();
    void returnCoin();
    void refill(int q);
    void printStatus();

    VendingState* getNoCoinState(){
        return noCoinState;
    }
    VendingState* getHasCoinState(){
        return hasCoinState;
    }
    VendingState* getDispenseState(){
        return dispenseState;
    }
    VendingState * getSoldOutState(){
        return soldOutState;
    }
    int getItemCount(){
        return itemCount;
    }
    void decrementItemCount(){
        itemCount--;
    }
    void incrementItemCount(int count=1){
        itemCount+=count;
    }
    int getInsertedCoin(){
        return insertedCoins;
    }
    void setInsertedCoin(int coin){
        insertedCoins=coin;
    }
    void addCoin(int coin){
        insertedCoins+=coin;
    }
    int getPrice(){
        return this->itemPrice;
    }
    void setPrice(int p){
        this->itemPrice=p;
    }
};
class NoCoinState:public VendingState{
    public:
    VendingState* insertCoin(VendingMachine*m,int coin)override{
        m->setInsertedCoin(coin);
        cout<<"Coin inserted.Current balance is Rs: "<<coin<<endl;
        return m->getHasCoinState();
    }
    VendingState* selectItem(VendingMachine*m)override{
        cout<<"Please insert coin first"<<endl;
        return m->getHasCoinState();
    }
    VendingState* dispense(VendingMachine*m)override{
        cout<<"PLEASE INSERT COIN FIRST AND SELECT!"<<endl;
        return m->getNoCoinState();

    }
    VendingState * returnCoin(VendingMachine* machine) override{
        cout<<"NO COIN TO RETURN !"<<endl;
        return machine->getNoCoinState();
    }
    VendingState* refill(VendingMachine* machine,int q) override{
        cout<<"ITMS REFILLING" <<endl;
        machine->incrementItemCount(q);
        return machine->getNoCoinState();
    }
    string getStateName() override{
        return "NO_COIN";
    }
};
class HasCoinState : public VendingState{
    public:
    VendingState* insertCoin(VendingMachine* m,int coin) override{
        m->addCoin(coin);
        cout<<"ADDITIOAL COIN INSERTED. CURRENT BALANCE: RS. "<<m->getInsertedCoin();
        return m->getHasCoinState();
    }
    VendingState* selectItem(VendingMachine*m)override{
        if(m->getInsertedCoin()>=m->getPrice()){
            cout<<" ITEM SELECTED DISPENSING"<<endl;
            int change=m->getInsertedCoin()-m->getPrice();
            if(change>0)cout<<"CHANGE RETURNED: "<< change<<endl;
            m->setInsertedCoin(0);
            return m->getDispenseState();
        }else{
            int need= m->getPrice()-m->getInsertedCoin();
            cout<<"NEED RS: "<<need<<endl;
            return m->getHasCoinState();
        }
    }
    VendingState* dispense(VendingMachine* machine) override{
        cout<<"PLEASE SELECT AN ITEM FIRST"<<endl;
        return machine->getHasCoinState();
    }

    VendingState* returnCoin(VendingMachine* machine) override{
        cout<<"COIN RETURNED : RS. "<<machine->getInsertedCoin() <<endl;
        machine->setInsertedCoin(0);
        return machine->getNoCoinState();
    }
    
    VendingState* refill(VendingMachine* machine,int q) override{
        cout<<"CANT REFILL IN THIS STATE"<<endl;
        return machine->getHasCoinState();
    }
    string getStateName() override{
        return "HAS_COIN";
    }

};
class DispenseState : public VendingState{
    public:
    VendingState* insertCoin(VendingMachine* machine,int coin) override{
        cout<<"PLEASE WAIT , ALREADY DISPENSING ITEM, COIN RETURNED: RS. "<<coin<<endl;
        return machine->getDispenseState();
    }

    VendingState* selectItem(VendingMachine* machine) override{
        cout<<"ALAREADY DISPENSING ITEM. PLEASE WAIT. "<<endl;
        return machine->getDispenseState();
    }

    VendingState* dispense(VendingMachine* machine) override{
        cout<<"ITEM DISPENSED!"<<endl;
        machine->decrementItemCount();
        if(machine->getItemCount()>0)return machine->getNoCoinState();
        else{
            cout<<"MACHINE IS NOW SOLD OUT"<<endl;
            return machine->getSoldOutState();
        }
    }

    VendingState * returnCoin(VendingMachine* machine) override{
        cout<<"CANANOT RETURN COINT WHILE DISPENSING ITEM!"<<endl;
        return machine->getDispenseState();
    }

    VendingState* refill(VendingMachine* machine,int q)override{
        cout<<"CANNOT REFILL IT THIS STATE"<<endl;
        return machine->getDispenseState();
    }
    
    string getStateName()override{
        return "DISPENSING";
    }
};
class soldOutstate: public VendingState{
    public:
    VendingState* insertCoin(VendingMachine* machine,int coin) override{
        cout<<"MACHINE IS SOLD OUT. COIN RETURNED RS. "<<coin<<endl;
        return machine->getSoldOutState();
    }

    VendingState* selectItem(VendingMachine* machine) override{
        cout<<"MACHINE IS SOLD OUT"<<endl;
        return machine->getSoldOutState();
    }

    VendingState* dispense(VendingMachine* machine) override{
        cout<<"MACHINE IS SOLD OUT!"<<endl;
        return machine->getSoldOutState();
    }

    VendingState* returnCoin(VendingMachine* machine) override{
        cout<<"MACHINE IS SOLD OUT:. NO COIN INSERTED"<<endl;
        return machine->getSoldOutState();
    }

    VendingState* refill(VendingMachine* machine,int q) override{
        cout<<"ITEMS REFILLING"<<endl;
        machine->incrementItemCount(q);
        return machine->getNoCoinState();
    }
    
    string getStateName() override{
        return "SOLD_OUT";
    }
};
VendingMachine:: VendingMachine(int itemCount,int itemPrice){
    this->itemCount=itemCount;
    this->itemPrice=itemPrice;
    this->insertedCoins=0;

    noCoinState=new NoCoinState();
    hasCoinState=new HasCoinState();
    dispenseState=new DispenseState();
    soldOutState=new soldOutstate();

    if(itemCount>0)currState=noCoinState;
    else currState=soldOutState;
}

void VendingMachine :: insertCoin(int coin){
    currState=currState->insertCoin(this,coin);
}

void VendingMachine :: selectItem(){
    currState=currState->selectItem(this);
}

void VendingMachine::dispense() {
    currState = currState->dispense(this);
}

void VendingMachine::returnCoin(){
    currState=currState->returnCoin(this);
}

void VendingMachine::refill(int q){
    currState=currState->refill(this,q);
}

void VendingMachine::printStatus(){
    cout << "\n--- Vending Machine Status ---" << endl;
    cout << "Items remaining: " << itemCount << endl;
    cout << "Inserted coin: Rs " << insertedCoins << endl;
    cout << "Current state: " << currState->getStateName() << endl << endl;
}

int main(){
    int itemcount=2;
    int itemprice=20;
    VendingMachine machine(itemcount,itemprice);
    machine.printStatus();
    
    cout<<"1. TRYING TO SELECT ITEM WITHOUT COIN:"<<endl;
    machine.selectItem();
    machine.printStatus();

    cout<<"2. INSERTING COIN: "<<endl;
    machine.insertCoin(10);
    machine.printStatus();

    cout<<"3. SELECTING ITEM WITH INSUFFIECIENT FUNDS." <<endl;
    machine.selectItem();
    machine.printStatus();

    cout<<"4. ADDING MORE COINS."<<endl;
    machine.insertCoin(10);
    machine.printStatus();

    cout<<"5. SELECTING ITEM NOW."<<endl;
    machine.selectItem();
    machine.printStatus();

    cout<<"6. DISPENSING ITEM."<<endl;
    machine.dispense();
    machine.printStatus();

    cout<<"7.BUYING LAST ITEM."<<endl;
    machine.insertCoin(20);
    machine.selectItem();
    machine.dispense();
    machine.printStatus();

    cout<<"8. TRYING TO USE SOLD OUT MACHINE"<<endl;
    machine.insertCoin(5);

    cout<<"9. TRYING TO USE SOLD OUT MACHINE."<<endl;
    machine.refill(2);
    machine.printStatus();

    return 0;
}