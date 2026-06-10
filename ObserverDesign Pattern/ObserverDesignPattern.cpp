#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;


class ISubscriber{
    public:
    virtual void update()=0;
    virtual ~ISubscriber(){}
};
class IChannel{
    public:
    virtual void subscribe(ISubscriber* subscriber)=0;
    virtual void unsubscribe(ISubscriber* subscriber)=0;
    virtual void notifySubscribers()=0;
    virtual ~IChannel() {}
};

class Channel:public IChannel{
    private:
    vector<ISubscriber*> ListofSubscribers;
    string ChannelName;
    string LatestVideo;

    public:
    Channel(const string& ChannelName){
        this->ChannelName=ChannelName;
    }
    void subscribe(ISubscriber* subscriber)override{
        if(find(ListofSubscribers.begin(),ListofSubscribers.end(),subscriber)==ListofSubscribers.end()){
            ListofSubscribers.push_back(subscriber);
        }
    }
    void unsubscribe(ISubscriber* subscriber) override {
        auto it = find(ListofSubscribers.begin(), ListofSubscribers.end(), subscriber);
        if (it != ListofSubscribers.end()) {
            ListofSubscribers.erase(it);
        }
    }
    void notifySubscribers()override{
        for(ISubscriber* sub: ListofSubscribers){
            sub->update();
        }
    }
    void uploadNewVideo(const string& title){
        LatestVideo=title;
        cout<<"\n[" <<ChannelName<<" uploaded "<<LatestVideo<<"]\n";
        notifySubscribers();
    }
    string getVideoData(){
         return " checkout our new video : "+ LatestVideo + "\n";
    }

};

class Subscriber:public ISubscriber{
    private:
    Channel* channel;
    string name;
    public:
    Subscriber(const string& name, Channel* channel){
        this->name=name;
        this->channel=channel;
    }
    void update()override{
        cout<<"Hey "<<name<<","<<channel->getVideoData();
    }
};
int main(){
    Channel* channel=new Channel("SB");
    Subscriber* s1= new Subscriber("Suhani",channel);
    Subscriber* s2= new Subscriber("Varun",channel);

    channel->subscribe(s1);
    channel->subscribe(s2);
    channel->uploadNewVideo("Observer Pattern Video");
    channel->unsubscribe(s2);
    channel->uploadNewVideo("Observer Design Video Part II");
    
    return 0;
}