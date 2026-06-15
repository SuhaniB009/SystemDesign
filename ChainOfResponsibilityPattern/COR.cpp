#include<iostream>
using namespace std;

class MoneyHandler{
   protected:
   MoneyHandler* next;

   public:
   MoneyHandler(){
    this->next=nullptr;
   }
   void setNext(MoneyHandler* next){
     this->next=next;
   }
   virtual void dispense(int amount)=0;
};

class ThousandHandler:public MoneyHandler{
    private:
    int numNotes;

    public:
    ThousandHandler(int notes){
        this->numNotes=notes;
    }
    void dispense(int amount)override{
        int notesNeeded=amount/1000;
        if(notesNeeded>numNotes){
            notesNeeded=numNotes;
            numNotes=0;
        }
        else{
            numNotes-=notesNeeded;
        }

        if(notesNeeded>0)cout<<"Dispensing "<<notesNeeded<<"x 1000 notes.\n ";
        int remainingAmount=amount-(notesNeeded*1000);
        if(remainingAmount>0){
            if(next!=nullptr)next->dispense(remainingAmount);
            else{
                cout<<"Remaining amount of "<<remainingAmount<<"cannot be fullfilled.\n";
            }
        }
    }
};

class FiveHundredHandler:public MoneyHandler{
    private:
    int numNotes;

    public:
    FiveHundredHandler(int notes){
        this->numNotes=notes;
    }
    void dispense(int amount)override{
        int notesNeeded=amount/500;
        if(notesNeeded>numNotes){
            notesNeeded=numNotes;
            numNotes=0;
        }
        else{
            numNotes-=notesNeeded;
        }

        if(notesNeeded>0)cout<<"Dispensing "<<notesNeeded<<"x 500 notes.\n ";
        int remainingAmount=amount-(notesNeeded*500);
        if(remainingAmount>0){
            if(next!=nullptr)next->dispense(remainingAmount);
            else{
                cout<<"Remaining amount of "<<remainingAmount<<"cannot be fullfilled.\n";
            }
        }
    }
};
class TwoHundredHandler:public MoneyHandler{
    private:
    int numNotes;

    public:
    TwoHundredHandler(int notes){
        this->numNotes=notes;
    }
    void dispense(int amount)override{
        int notesNeeded=amount/200;
        if(notesNeeded>numNotes){
            notesNeeded=numNotes;
            numNotes=0;
        }
        else{
            numNotes-=notesNeeded;
        }

        if(notesNeeded>0)cout<<"Dispensing "<<notesNeeded<<"x 200 notes.\n ";
        int remainingAmount=amount-(notesNeeded*200);
        if(remainingAmount>0){
            if(next!=nullptr)next->dispense(remainingAmount);
            else{
                cout<<"Remaining amount of "<<remainingAmount<<"cannot be fullfilled.\n";
            }
        }
    }
};
class HundredHandler:public MoneyHandler{
    private:
    int numNotes;

    public:
    HundredHandler(int notes){
        this->numNotes=notes;
    }
    void dispense(int amount)override{
        int notesNeeded=amount/100;
        if(notesNeeded>numNotes){
            notesNeeded=numNotes;
            numNotes=0;
        }
        else{
            numNotes-=notesNeeded;
        }

        if(notesNeeded>0)cout<<"Dispensing "<<notesNeeded<<"x 100 notes.\n ";
        int remainingAmount=amount-(notesNeeded*100);
        if(remainingAmount>0){
            if(next!=nullptr)next->dispense(remainingAmount);
            else{
                cout<<"Remaining amount of "<<remainingAmount<<"cannot be fullfilled.\n";
            }
        }
    }
};

int main(){
    MoneyHandler* thousandHandler = new ThousandHandler(3);
    MoneyHandler* fiveHundredHandler = new FiveHundredHandler(5);
    MoneyHandler* twoHundredHandler= new TwoHundredHandler(10);
    MoneyHandler* hundredHandler= new HundredHandler(20);

    thousandHandler->setNext(fiveHundredHandler);
    fiveHundredHandler->setNext(twoHundredHandler);
    twoHundredHandler->setNext(hundredHandler);

    int amountToWithdraw = 3000;
    cout << "\nDispensing amount: ₹" << amountToWithdraw << endl;
    thousandHandler->dispense(amountToWithdraw);

    return 0;
}    
