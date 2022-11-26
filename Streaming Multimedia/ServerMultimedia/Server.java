package server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server{


    public void receiveRequestFromClient(){
        try{ 
            ServerSocket socketclient=new ServerSocket(6666);  
            Socket server=socketclient.accept();//establishes connection   
            DataInputStream receive=new DataInputStream(server.getInputStream());  
            String  str=(String)receive.readUTF();  
            System.out.println("Type a Streamer= "+str);
          

            if(str=="Audio"){
                this.showAllFileinDirectory(str);
            }
            if(str=="Video"){
                this.showAllFileinDirectory(str);
            }
            if(str=="Photo"){
                this.showAllFileinDirectory(str);
            }
            socketclient.close(); 
        }
        catch(Exception e){System.out.println(e);}
    }


    public void showAllFileinDirectory(String typeToShow) throws Exception{

        // Scanner typescan= new Scanner(System.in);
        // String typeToShow= (String)typescan.nextLine();
        File dir  = new File("D:\\Bossy\\L2\\S3\\ProgrammationSysteme Mr Naina\\Streaming Multimedia\\"+ typeToShow +"\\");
        File[] liste = dir.listFiles();
          for(File item : liste){
            if(item.isFile())
            { 
              System.out.format("Nom du fichier: %s%n", item.getName()); 
            } 
          }
    }

    public void showAllDirectoryinType(String directoryToShow) throws Exception{

        // Scanner typescan= new Scanner(System.in);
        // String directoryToShow= (String)typescan.nextLine();
        File dir  = new File("D:\\Bossy\\L2\\S3\\ProgrammationSysteme Mr Naina\\Streaming Multimedia\\"+ directoryToShow +"\\");
        File[] liste = dir.listFiles();
          for(File item : liste){
            if(item.isDirectory())
            { 
              System.out.format("Nom du répertoir: %s%n", item.getName()); 
            } 
          }
    }



    public static void main(String[] args) throws Exception{
        
        Server myserver= new Server();
        myserver.showAllDirectoryinType("Video");
        myserver.receiveRequestFromClient();
    }
}