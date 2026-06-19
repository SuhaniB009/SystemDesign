#include<iostream>
#include<vector>
#include<string>
#include<unordered_map>
#include <random>
#include <memory>
#include <chrono>

using namespace std;

class AsteroidFlyWeight{
   private:
        int length;
        int width;
        int weight;
        string color;
        string texture;
        string material;
   public:
        AsteroidFlyWeight(int l,int w,int wt,string col,string t,string m){
            this->length=l;
            this->width=w;
            this->weight=wt;
            this->color=col;
            this->texture=t;
            this->material=m;
        }
        void render(int posX,int posY,int velocityX,int velocityY){
            cout<<"Rendering "<<color<<", "<<texture<<", "<<material<<", "
            <<"asteroid at position ( "<<posX<<", "<<posY<<" ) Size: "
            <<length <<"x"<<width
            <<"Velocity: ("<<velocityX<<", "<<velocityY<<")"<<endl;
        }
        static size_t getMemoryUsage(){
            return sizeof(int)*3+sizeof(string)*3+32*3;
        }
};
class AsteroidFactory{
    private:
        static unordered_map<string,AsteroidFlyWeight*> flyweights;
    public:
        static AsteroidFlyWeight* getAsteroid(int length,int width, int weight,string color,string texture,string material){
            string key= to_string(length)+"_"+to_string(width)+"_"+to_string(weight)+"_"+color+"_"+texture+"_"+material;
            if(flyweights.find(key)==flyweights.end()){
               flyweights[key]=new AsteroidFlyWeight(length,width,weight,color,texture,material);
            }
            return flyweights[key];
        }
        static int getFlyWeigthCount(){
            return flyweights.size();
        }
        static size_t getTotalFlyweightMemory(){
            return flyweights.size()* AsteroidFlyWeight::getMemoryUsage();
        }
        static void cleanup(){
            flyweights.clear();
        }
};
unordered_map<string,AsteroidFlyWeight*> AsteroidFactory::flyweights;
class AsteroidContext{
    private:
        AsteroidFlyWeight* flyweight;
        int posX,posY;
        int velocityX,velocityY;
    public:
        AsteroidContext(AsteroidFlyWeight* fw,int posX,int posY,int velX,int velY){
        this->flyweight = fw;
        this->posX = posX;
        this->posY = posY;
        this->velocityX = velX;
        this->velocityY = velY;
        }
        void render(){
            flyweight->render(posX,posY,velocityX,velocityY);
        }
        static size_t getMemoryUsage(){
            return sizeof(AsteroidFlyWeight*)+sizeof(int)*4;
        }
};
class SpaceGameWithFlyweight{
    private:
    vector<AsteroidContext*>asteroids;
    public:
        void spawnAsteroids(int count){
            cout<<"---Spawning "<<count<<" Asteroids-----"<<endl;
            vector<string> colors={"Red","Yellow","Blue"};
            vector<string> texture={"Rocky","Metallic","Icy"};
            vector<string> materials={"Iron","Stone","Ice"};
            int sizes[]={25,35,45};
            for(int i=0;i<count;i++){
                int type=i%3;
                AsteroidFlyWeight* flyweight=AsteroidFactory::getAsteroid(sizes[type],sizes[type],sizes[type]*10,
                colors[type],texture[type],materials[type]
                );
                asteroids.push_back(new AsteroidContext(flyweight,
                    100+i%50,
                    200+i%30,
                    1,
                    2
                ));
 
            }
            cout<<"Created "<<asteroids.size()<<" asteroids contexts "<<endl;
            cout<<"Total flyweight objects: "<<AsteroidFactory::getFlyWeigthCount()<<endl;
        }
        void renderAll(){
            cout<<"---Rendering first 5 asteroids---"<<endl;
            for(int i=0;i<min(5,(int)asteroids.size());i++){
                asteroids[i]->render();
            }
        }
        size_t calculateMemoryUsage(){
            size_t contextMemory=asteroids.size()* AsteroidContext::getMemoryUsage();
            size_t flyweightMemory=AsteroidFactory::getTotalFlyweightMemory();
            return contextMemory+flyweightMemory;
        }   
        int getAsteroidCount(){
            return asteroids.size();
        }
};
int main(){
    const int ASTEROID_COUNT=1000000;
    SpaceGameWithFlyweight* game= new SpaceGameWithFlyweight();
    game->spawnAsteroids(ASTEROID_COUNT);
    game->renderAll();
    size_t totalMemory=game->calculateMemoryUsage();
    cout<<"---Memory Usage---"<<endl;
    cout<<"Total asteroids: "<<ASTEROID_COUNT<<endl;
    cout<<"Memory per asteroids: "<<AsteroidContext::getMemoryUsage()<<endl;
    cout << "Total memory used: " << totalMemory << " bytes" << endl;           
    cout << "Memory in MB: " << totalMemory / (1024.0 * 1024.0) << " MB" << endl;   
    return 0;  
}