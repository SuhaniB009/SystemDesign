
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.io.IOException;
import java.io.FileWriter;
interface DocumentElement{
    public abstract String render();
}

class TextElement implements DocumentElement{
    private String text;
    public TextElement(String text){
        this.text=text;
    }
    public String render(){
       return text;
    }
}

class ImageElement implements DocumentElement{
    private String imagePath;
    public ImageElement (String imagePath){
        this.imagePath=imagePath;
    }
    public String render(){
        return "[Image: "+imagePath+"]";
    }
}

class NewLineElement implements DocumentElement{
    public String render(){
        return "\n";
    }
}

class TabSpaceElement implements DocumentElement{
    public String render(){
        return "\n";
    }
}

class Document{
    private List<DocumentElement> docElements= new ArrayList<>();
    public void addElement(DocumentElement element){
        docElements.add(element);
    }
    public String render(){
        StringBuilder result =new StringBuilder();
        for(DocumentElement element:docElements){
            result.append(element.render());
        }
        return result.toString();
    }
}

interface Persistance{
    void save(String data);
}
class SaveToFile implements Persistance{
    public void save(String data){
        try{
          FileWriter writer = new FileWriter("document.txt");
          writer.write(data);
          writer.close();
          System.out.println("Document saved to document.txt");
        }
        catch(IOException e){
          System.out.println("Error: Unable to open file for writing");
        }
    }
}
class SaveToDB implements Persistance{
    public void save(String data){
        //Save to DB
    }
}

class DocumentEditor{
    private Document document;
    private Persistance storage;
    private String renderedDocument="";

    public DocumentEditor(Document document,Persistance storage){
       this.document=document;
       this.storage=storage;
    }
    public void addText(String text){
        document.addElement(new TextElement(text));
    }
    public void addImage(String imagePath){
        document.addElement(new ImageElement(imagePath));
    }
    public void addNewLine() {
        document.addElement(new NewLineElement());
    }

    public void addTabSpace() {
        document.addElement(new TabSpaceElement());
    }
    public String render(){
        if(renderedDocument.isEmpty()){
            return document.render();
        }
        return renderedDocument;
    }
    public void SaveDocument(){
        storage.save(renderedDocument);
    }

}
public class GoodDesign {
    public static void main(String[] args){
        Document document= new Document();
        Persistance storage=new SaveToFile();

        DocumentEditor docEditor = new DocumentEditor(document, storage);
        docEditor.addText("Hello world!!");
        docEditor.addNewLine();
        docEditor.addTabSpace();
        docEditor.addText("Indented text after tab space");
        docEditor.addNewLine();
        docEditor.addImage("picture.jpg");

        System.out.println(docEditor.render());
        docEditor.SaveDocument();
    }
}
