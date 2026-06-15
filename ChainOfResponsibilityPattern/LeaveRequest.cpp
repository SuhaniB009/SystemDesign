#include<iostream>
#include<string>
using namespace std;

class LeaveHandler{
   protected:
   LeaveHandler* next;

   public:
   LeaveHandler(){
    this->next=nullptr;
   }
   void setNext(LeaveHandler* next){
     this->next=next;
   }
   virtual void approveLeave(string employeeName, int days)=0;
};

class TeamLead:public LeaveHandler{
    public:
    void approveLeave(string employeeName, int days)override{
        if(days <= 2){
            cout << "Team Lead approved " << days << " days leave for " << employeeName << ".\n";
        }
        else{
            if(next!=nullptr){
                next->approveLeave(employeeName, days);
            }
            else{
                cout << "Leave request of " << days << " days for " << employeeName << " cannot be fulfilled.\n";
            }
        }
    }
};

class Manager:public LeaveHandler{
    public:
    void approveLeave(string employeeName, int days)override{
        if(days <= 5){
            cout << "Manager approved " << days << " days leave for " << employeeName << ".\n";
        }
        else{
            if(next!=nullptr){
                next->approveLeave(employeeName, days);
            }
            else{
                cout << "Leave request of " << days << " days for " << employeeName << " cannot be fulfilled.\n";
            }
        }
    }
};

class Director:public LeaveHandler{
    public:
    void approveLeave(string employeeName, int days)override{
        if(days <= 10){
            cout << "Director approved " << days << " days leave for " << employeeName << ".\n";
        }
        else{
            if(next!=nullptr){
                next->approveLeave(employeeName, days);
            }
            else{
                cout << "Leave request of " << days << " days for " << employeeName << " was REJECTED. \n";
            }
        }
    }
};

int main(){
    LeaveHandler* teamLead = new TeamLead();
    LeaveHandler* manager = new Manager();
    LeaveHandler* director = new Director();
    teamLead->setNext(manager);
    manager->setNext(director);

    cout << "\n--- Leave Requests ---\n";
    teamLead->approveLeave("Suhani", 1);   
    teamLead->approveLeave("Rohan", 4);     
    teamLead->approveLeave("Aman", 8);       
    teamLead->approveLeave("Priya", 15);     

    return 0;
}