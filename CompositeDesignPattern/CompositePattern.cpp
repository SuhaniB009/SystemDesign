#include<iostream>
#include<vector>
#include<string>
#include <stdexcept>

using namespace std;

class FileSystemItem{
public:
    virtual void ls(int indent=0)=0;
    virtual void openAll(int indent=0)=0;
    virtual int getSize()=0;
    virtual string getName()=0;
    virtual FileSystemItem* cd(const string& name)=0;
    virtual bool isFolder()=0;
    virtual ~FileSystemItem(){}
};

class file:public FileSystemItem{
    string name;
    int size;
    public:
    file(const string& name,int size){
        this->name=name;
        this->size=size;
    }
    void ls(int indent=0){
        cout<<string(indent,' ')<<name<<"\n";
    }
    void openAll(int indent=0){
        cout<<string(indent,' ')<<name<<"\n";
    }
    int getSize(){
        return size;
    }
    string getName(){
        return name;
    }
    FileSystemItem* cd (const string& ){
        return nullptr;
    }
    bool isFolder(){
        return false;
    }
};

class Folder:public FileSystemItem{
    vector<FileSystemItem*> children;
    string name;

    public:
    Folder(const string& n){
        name=n;
    }
    ~Folder(){
        for(auto c:children)delete c;
    }
    void add(FileSystemItem* item){
        children.push_back(item);
    }
    void ls(int indent=0)override{
        for(auto c:children){
            if(c->isFolder()){
                cout<<string(indent,' ')<<"+ "<<c->getName()<<"\n";
            }else{
                cout<<string(indent,' ')<<c->getName()<<"\n";
            }
        }
    }
    void  openAll(int indent=0)override{
        cout<<string(indent,' ')<<"+ "<<name<<"\n";
        for(auto c:children){
            c->openAll(indent+4);
        }
    }
    int getSize()override{
        int total=0;
        for(auto c:children){
            total+=c->getSize();
        }
        return total;
    }

    FileSystemItem* cd(const string& target)override{
        for(auto c:children){
            if(c->isFolder() && c->getName()==target){
                return c;
            }
        }
        return nullptr;
    }
    string getName()override{
        return name;
    }
    bool isFolder()override{
        return true;
    }
};

int main(){
    Folder* root = new Folder("root");

    root->add(new file("file1.txt",1));
    root->add(new file("file2.txt",1));
    Folder* docs=new Folder("docs");
    docs->add(new file("resume.pdf",1));
    docs->add(new file("notes.txt",1));

    root->add(docs);

    Folder* images= new Folder("images");
    images->add(new file("photo.jpeg",1));
    root->add(images);

    root->ls();
    docs->ls();
    root->openAll();

    FileSystemItem* cwd = root->cd("docs");
    if (cwd != nullptr) {
        cwd->ls();
    } else {
        cout << "\n Could not cd into docs \n";
    }

     cout << root->getSize();

    delete root;
    return 0;
}