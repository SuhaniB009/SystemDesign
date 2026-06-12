#include<iostream>
using namespace std;

class IReports{
   public:
   virtual string getJsonData(const string &data)=0;
   virtual ~IReports(){}
};

class XmlDataProvider{
    public:
    string getXmlData(const string& data){ //"Suhani:18"
      size_t sep= data.find(':');
      string name= data.substr(0,sep);
      string id= data.substr(sep+1);

      return "<user>"
             "<name>"+name+"</name>"
             "<id>"+id+"</id>"
             "</user>";
    }
};

class XmlDataProviderAdapter:public IReports{
    XmlDataProvider* xmlProvider; 
    public:
    XmlDataProviderAdapter(XmlDataProvider* xmlProvider){
        this->xmlProvider=xmlProvider;
    }
    string getJsonData(const string& data)  {
        string xmlData= xmlProvider->getXmlData(data);
        size_t startname= xmlData.find("<name>")+6;
        size_t endname= xmlData.find("</name>");
        string name = xmlData.substr(startname,endname-startname);

        size_t startid= xmlData.find("<id>")+4;
        size_t endid=xmlData.find("</id>");
        string  id =xmlData.substr(startid,endid-startid);

        return "{\"name\":\""+name+"\", \"id\":"+id +"}";
    }
};

class Client{
    public :
    void getReport(IReports* report,string rawData){
        cout<<"Proceed JSON:"<<report->getJsonData(rawData)<<endl;
    }
};

int main(){
    XmlDataProvider* xmlDataProvider= new XmlDataProvider();
    IReports* report= new XmlDataProviderAdapter(xmlDataProvider);
    string rawData="Suhani:18";
    Client* client= new Client();
    client->getReport(report,rawData);

    delete xmlDataProvider;
    delete report;
    return 0;
    

}