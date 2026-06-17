//package IteratorDesignPattern;

import java.util.*;

interface Iterator<T>{
    boolean hasNext();
    T Next();
}
interface Iterable<T>{
    Iterator<T> getIterator();
}
class LinkedList implements Iterable<Integer>{
    public int data;
    public LinkedList next;
    public LinkedList(int value){
        data=value;
        next=null;
    }
    public Iterator<Integer> getIterator(){
         return new LinkedListIterator(this);
    }
}
class BinaryTree implements Iterable<Integer>{
    public int data;
    public BinaryTree left;
    public BinaryTree right;
    public BinaryTree(int value){
        data=value;
        left=null;
        right=null;
    }
    public Iterator<Integer> getIterator(){
        return new BinaryTreeIterator(this);
    }
}
class Song{
    public String name;
    public String artist;
    public Song(String n,String a){
        name=n;
        artist=a;
    }
}
class Playlist implements Iterable<Song>{
    public List<Song> myPlaylist= new ArrayList<>();
    public void addSong(Song s){
        myPlaylist.add(s);
    }
    public Iterator<Song> getIterator(){
        return new PlaylistIterator(myPlaylist);
    }
}
class LinkedListIterator implements Iterator<Integer>{
    private LinkedList current;
    public LinkedListIterator(LinkedList head){
        current= head;
    }
    public boolean hasNext(){
        return current!=null;
    }
    public Integer Next(){
        int val=current.data;
        current=current.next;
        return val;
    }
}
class BinaryTreeIterator implements Iterator<Integer>{
    private Deque<BinaryTree> stk= new ArrayDeque<>();
    public BinaryTreeIterator(BinaryTree root) {
        pushLeft(root);
    }
    private void pushLeft(BinaryTree node){
        while(node!=null){
            stk.push(node);
            node=node.left;
        }
    }
    public boolean hasNext(){
       return !stk.isEmpty();
    }
    public Integer Next(){
       BinaryTree node=stk.pop();
       int val=node.data;
       if(node.right!=null){
        pushLeft(node.right);
       }
       return val;
    }
}
class PlaylistIterator implements Iterator<Song>{
    private List<Song> vec;
    private int index=0;
    public PlaylistIterator(List<Song> v){
        vec=v;
    }
    public boolean hasNext(){
        return index<vec.size();
    }
    public Song Next(){
        return vec.get(index++);
    }
}
public class IteratorPattern {
    public static void main(String[] args){
        LinkedList list = new LinkedList(1);
        list.next = new LinkedList(2);
        list.next.next = new LinkedList(3);
        Iterator<Integer> iterator1= list.getIterator();
        System.out.print("LinkedList contents: ");
        while (iterator1.hasNext()) {
            System.out.print(iterator1.Next() + " ");
        }
        System.out.println();

        BinaryTree root = new BinaryTree(2);
        root.left  = new BinaryTree(1);
        root.right = new BinaryTree(3);
        Iterator<Integer> iterator2 = root.getIterator();
        System.out.print("BinaryTree inorder: ");
        while (iterator2.hasNext()) {
            System.out.print(iterator2.Next() + " ");
        }
        System.out.println();
   
    Playlist playlist = new Playlist();
        playlist.addSong(new Song("song1", "abc"));
        playlist.addSong(new Song("song2", "xyz"));
        Iterator<Song> iterator3 = playlist.getIterator();
        System.out.println("Playlist songs:");
        while (iterator3.hasNext()) {
            Song s = iterator3.Next();
            System.out.println("  " + s.name + " by " + s.artist);
        }

    }
}
