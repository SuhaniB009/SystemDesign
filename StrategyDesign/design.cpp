#include <iostream>
using namespace std;

class TalkableRobot{
    public:
    virtual void talk()=0;
    virtual ~TalkableRobot(){};
};
class NormalTalk:public TalkableRobot{
    public: 
    void talk(){
        cout<<"Talking normally"<<endl;
    }
};
class Notalk: public TalkableRobot{
    public:
    void talk(){
        cout<<"Cannot Talk"<<endl;
    }
};

class WalkableRobot{
    public:
    virtual void walk()=0;
    virtual ~WalkableRobot(){};
};
class NormalWalk: public WalkableRobot{
    public:
    void walk(){
        cout<<"Walking Normally"<<endl;
    }
};
class NoWalk: public WalkableRobot{
    public:
    void walk(){
        cout<<"Cannot walk"<<endl;
    }
};

class FlyableRobot {
    public:
    virtual void fly()=0;
    virtual ~FlyableRobot (){};
};
class NormalFly:public FlyableRobot {
    public:
    void fly(){
        cout<<"Normal fly"<<endl;
    }
};
class Nofly : public FlyableRobot {
    public:
    void fly(){
        cout<<"Cannot fly"<<endl;
    }
};

class Robot{
    protected:
    TalkableRobot* talkbehaviour;
    WalkableRobot* walkbehaviour;
    FlyableRobot* flybehaviour;
    
    public:
    Robot(TalkableRobot *t,WalkableRobot *w, FlyableRobot *f){
        this->walkbehaviour=w;
        this->flybehaviour=f;
        this->talkbehaviour=t;
    }

    void walk(){
        walkbehaviour->walk();
    }
    void talk(){
        talkbehaviour->talk();
    }
    void fly(){
        flybehaviour->fly();
    }
    virtual void Projection()=0;
};
class CompanionRobot: public Robot{
    public:
    CompanionRobot(TalkableRobot *t,WalkableRobot *w,FlyableRobot *f):Robot(t,w,f){}
    void Projection(){
        cout<<"Displaying friendly companion features"<<endl;
    }
};
class WorkerRobot:public Robot{
    public:
    WorkerRobot(TalkableRobot *t,WalkableRobot *w,FlyableRobot *f):Robot(t,w,f){}
    void Projection(){
        cout<<"Displaying worker efficiency stats"<<endl;
    }
};

int main(){
    Robot* robot1= new CompanionRobot(new NormalTalk(),new NormalWalk(),new NormalFly());
    robot1->talk();robot1->walk();robot1->fly();robot1->Projection();

    cout<<"---------"<<endl;

    Robot* robot2= new WorkerRobot(new Notalk(),new NoWalk(),new Nofly());
    robot2->talk();robot2->walk();robot2->fly();robot2->Projection();

    return 0;
}
