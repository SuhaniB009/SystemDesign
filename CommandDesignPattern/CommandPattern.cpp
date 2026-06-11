#include<iostream>
using namespace std;

class Command{
   public:
   virtual void execute()=0;
   virtual void undo()=0;
   virtual ~Command(){}
};

class Light{
    public:
    void on(){
        cout<<"Light is ON"<<endl;
    }
    void off(){
        cout<<"Light is OFF"<<endl;
    }
};
class Fan{
    public:
    void on(){
        cout<<"Fan is ON"<<endl;
    }
    void off(){
        cout<<"Fan is OFF"<<endl;
    }
};

class LightCommand:public Command{
    private:
    Light* light;
    public:
    LightCommand(Light* light){
      this->light=light;
    }
    void execute(){
        light->on();
    }
    void undo(){
        light->off();
    }
};
class FanCommand: public Command{
    private:
    Fan* fan;
    public:
    FanCommand(Fan* fan){
        this->fan=fan;
    }
    void execute(){
        fan->on();
    }
    void undo(){
        fan->off();
    }
};
class RemoteController{
    private:
    static const int numButtons=4;
    Command* buttons[numButtons];
    bool buttonPressed[numButtons];

    public:
    RemoteController(){
        for(int i=0;i<numButtons;i++){
            buttons[i]=nullptr;
            buttonPressed[i]=0;
        }
    }
    void setCommand(int index,Command* cmd){
        if(index>=0 && index<numButtons){
            if(buttons[index]!=NULL)delete buttons[index];
            buttons[index]=cmd;
            buttonPressed[index]=false;
        }
    }
    void pressButton(int index){
        if(index>=0 && index<numButtons && buttons[index]!=NULL){
            if(buttonPressed[index]==false)buttons[index]->execute();
            else buttons[index]->undo();
            buttonPressed[index]=!buttonPressed[index];
        }else{
            cout<<"No command assigned at button"<<endl;
        }
    }

    ~RemoteController(){
        for(int i=0;i<numButtons;i++){
            if(buttons[i]!=NULL)delete buttons[i];
        }
    }
};

int main(){
    Light* roomLight= new Light();
    Fan* fan= new Fan();

    RemoteController* remote= new RemoteController();

    remote->setCommand(0,new LightCommand(roomLight));
    remote->setCommand(1,new FanCommand(fan));
    remote->pressButton(0);
    remote->pressButton(0);

    cout<<"------TOGGLING FAN BUTTONS 1---------"<<endl;
    remote->pressButton(1);

    cout<<"----- TOGGLING LIGHT BUTTONS 0------"<<endl;
    remote->pressButton(1);

    cout<<"----PRESSING UNASSIGNED BUTTON 2-----"<<endl;
    remote->pressButton(2);
    cout<<endl;
    return 0;

}