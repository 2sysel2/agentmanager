/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw05sys;

/**
 *
 * @author Jaromir Sys
 */
public class HomeWork implements Runnable{
    
    public static int count = 0;
    
    public static void main(String[] args) {
        
        HomeWork hw1= new HomeWork();
        HomeWork hw2 = new HomeWork();
        HomeWork hw3 = new HomeWork();
        
        hw1.setName("1");
        hw2.setName("2");
        hw3.setName("3");
        hw1.run();       
        hw2.run();       
        hw3.run();
        
        
    }

    @Override
    public void run() {
        synchronized(this){
            while(count<=50){
                System.out.println("thread "+Thread.currentThread().getName()+" :"+ count++);
            }
        }
        
    }
    
    public void setName(String name){
        Thread.currentThread().setName(name);
    }
}
