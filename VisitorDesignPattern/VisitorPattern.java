package VisitorDesignPattern;

import java.nio.file.FileSystem;

class TextFile extends FileSystemItem{
    private String content;
    public TextFile(String filename,String filecontent){
        super(filename);
        this.content=filecontent;
    }
    public String getContent(){
        return content;
    }
    @Override
    public void accept(FileSystemVisitor visitor){
         visitor.visit(this);
    }
}
class ImageFile extends FileSystemItem{
    public ImageFile(String filename){
        super(filename);
    }
    @Override
    public void accept(FileSystemVisitor v){
        v.visit(this);
    }
}
class VideoFile extends FileSystemItem {
    public VideoFile(String fileName) {
        super(fileName);
    }
    
    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
    }
}

interface FileSystemVisitor{
    void visit(TextFile file);
    void visit (ImageFile file);
    void visit (VideoFile file);
}
abstract class FileSystemItem{
    protected String name;
    public FileSystemItem(String itemName){
        name=itemName;
    }
    public String getName(){
        return name;
    }
    public abstract void accept(FileSystemVisitor visitor);
}
class SizeCalculationVisitor implements FileSystemVisitor {
    @Override
    public void visit(TextFile file) {
        System.out.println("Calculating size for TEXT file: " + file.getName());
    }
    
    @Override
    public void visit(ImageFile file) {
        System.out.println("Calculating size for IMAGE file: " + file.getName());
    }
    
    @Override
    public void visit(VideoFile file) {
        System.out.println("Calculating size for VIDEO file: " + file.getName());
    }
}
class CompressionVisitor implements FileSystemVisitor {
    @Override
    public void visit(TextFile file) {
        System.out.println("Compressing TEXT file: " + file.getName());
    }
    
    @Override
    public void visit(ImageFile file) {
        System.out.println("Compressing IMAGE file: " + file.getName());
    }
    
    @Override
    public void visit(VideoFile file) {
        System.out.println("Compressing VIDEO file: " + file.getName());
    }
}
class VirusScanningVisitor implements FileSystemVisitor {
    @Override
    public void visit(TextFile file) {
        System.out.println("Scanning TEXT file: " + file.getName());
    }
    
    @Override
    public void visit(ImageFile file) {
        System.out.println("Scanning IMAGE file: " + file.getName());
    }
    
    @Override
    public void visit(VideoFile file) {
        System.out.println("Scanning VIDEO file: " + file.getName());
    }
}
public class VisitorPattern{
    public static void main(String []args){
        FileSystemItem img1= new ImageFile("sample.jpeg");
        img1.accept(new SizeCalculationVisitor());
        img1.accept(new CompressionVisitor());
        img1.accept(new VirusScanningVisitor());

        FileSystemItem vid1 = new VideoFile("test.mp4");
        vid1.accept(new CompressionVisitor());

    }
}