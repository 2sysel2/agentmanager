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
        
        HomeWork hw = new HomeWork();
        
        new Thread(hw,"1").start();
        new Thread(hw,"2").start();
        new Thread(hw,"3").start();
        //new Thread(hw,"4").start();
        //new Thread(hw,"5").start();

        
    }

    @Override
    public void run() {
        
        while(true){
            synchronized(this){
                if(count<=50)
                System.out.println("thread "+Thread.currentThread().getName()+" :"+ count++);
                else break;
            }
            for(long l = 0;l<100000;l++);
        }
        
        
    }
}
