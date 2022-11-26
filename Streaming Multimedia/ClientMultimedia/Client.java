package server;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

  public void sendRequestToServer(){

    try{ 
        Socket s=new Socket("localhost",6666);  
        DataOutputStream out=new DataOutputStream(s.getOutputStream()); 
        System.out.println("Acceder:"); 
        Scanner scan= new Scanner(System.in);
        String msg= (String)scan.nextLine();
        out.writeUTF(msg);  
        out.flush();  
        out.close();  
        s.close();    
    }catch(Exception e){System.out.println(e);}
  }


    
    public static void main(String[] args) throws Exception{
        
      Client me= new Client();
      me.sendRequestToServer();
        
    }
}