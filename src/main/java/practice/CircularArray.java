package practice;// Java program to demonstrate use of circular
// array using extra memory space
import java.util.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class CircularArray<E> implements Iterable<E>{
    int size; //number of objects in CircularArray
    int cur; //current index pointing to (used for next())
    Object[] array; //the array we're going to make circular


    public CircularArray(Collection<? extends E> c) {
        array = c.toArray();
        size = c.size();
        cur = 0;
    }

    public CircularArray() {
        array = new Object[0];
        cur = 0;
        size = 0;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCur() {
        System.out.println(cur);
        return cur;
    }

    public void setCur(int cur) {
        this.cur = cur%size;
        System.out.println(this.cur);
    }

    public Object[] getArray() {
        return array;
    }

    public void setArray(Object[] array) {
        this.array = array;
    }


    public Object get(int index) { //get value inside index
        if (index == -1) {
            index = size-1;
        } else if (index == size) {
            index = 0;
        }

        cur = index; //set cur to +1 cyclically
        System.out.println(array[cur]);
        return array[cur]; //return object
    }

    //go to next object of CircularArray, cycle to front if at end
    public void next1() {
        //if was at -1, now at 0
        if (cur == size - 1) {
            //if you were at last index then now at first index
            cur = 0;
        } else {
            cur++;
        }
     //   System.out.println(cur);
    }

    //go to next object of CircularArray, cycle to front if at end
    public void back() {
        //if was at 0 (1st index) now at last index = size - 1
        if (cur == 0) {
            cur = size - 1;
        } else {
            cur--;
        }
        System.out.println(cur);
    }

    public void delete(int index) {
        Object[] newArray = new Object[size - 1];
        //remove an item, need to decrease size and move all elements after o down 1 index
        for (int i = 0; i < index; i++) {
            newArray[i] = array[i];
        }
        if(index<size-1) {
            newArray[index] = array[index + 1]; //skip deleted index
            for (int j = index + 1; j < size-1; j++) { //put items in newArray except index
                newArray[j] = array[j + 1];
            }
            cur = index; //point to where deleted item
        } else{
            cur = size - 1;
        }
        size--;
        array = newArray;
    }

    public void add(Object o) {
        Object[] newArray = new Object[size+1];
        //copy over old items
        for(int i = 0; i<size;i++) {
            newArray[i] = array[i];
        }
        newArray[size] = o; //add new item to end
        size++;
        array = newArray;
    }
    public void addAll(Object... objects) {
        Object[] newArray = new Object[size + objects.length];
        //copy over old items
        for(int i = 0; i<size;i++) {
            newArray[i] = array[i];
        }
        int j =0; //objects pointer;
        for(int i = size; i<objects.length;i++) {
            newArray[i] = objects[j];
            j++;
        }
        size = size+objects.length;
        array = newArray;
    }

    public boolean exist(int index){
        //check if there is anything at that index
        if(index>=0 && index<size && array[index]!=null){
            System.out.println(true);
            return true;
        } else {
            System.out.println(false);
            return false;
        }
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> iterator = new Iterator<E>() {

            int loops = 1; //# loops
            int current = 0; //current count (can be larger than size and cur), will count up to loops*size

            @Override
            public boolean hasNext() {
                if((current<loops*size-1)) {
                    return true;
                }else{
                    return false;
                }
            }

            //set # loops through
            public void setLoops(int i){
                loops = i;
            }

            @Override
            public E next() {
               next1();
               current++;
               return (E) array[cur];
            }

            @Override
            public void remove() {
                delete(cur);
            }
        };
        return iterator;
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Iterable.super.spliterator();
    }
}