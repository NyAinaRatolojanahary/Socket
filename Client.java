package client;

import javax.sound.midi.Soundbank;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.util.Scanner;

public class Client {

    Socket socket;
    DataOutputStream outputStream;
    DataInputStream inputStream;

    public void setInputStream(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setOutputStream(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataInputStream getInputStream() {
        return inputStream;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public Client(String host, int port) throws Exception {
        setSocket(new Socket(host, port));
        setInputStream(new DataInputStream(getSocket().getInputStream()));
        setOutputStream(new DataOutputStream(getSocket().getOutputStream()));
    }

    public Client(Socket socket) throws Exception {
        setSocket(socket);
        setInputStream(new DataInputStream(getSocket().getInputStream()));
        setOutputStream(new DataOutputStream(getSocket().getOutputStream()));
    }


//Lit les bits du fichier et l'envoie au server pour les repartitions
    public void sendFileName(File file) throws Exception { 
        int length = file.getName().getBytes().length;
        getOutputStream().writeInt(length);
        getOutputStream().write(file.getName().getBytes());
    }

//envoie les contenus du fichier
    public void sendContentFile(File file) throws Exception {
        int lenght = 0;
        int offset = 0;
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[4 * 1024];
        while ((lenght = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, offset, lenght);
            outputStream.flush();
        }
        fileInputStream.close();
    }

//envoie le fichier en entier
    public void sendFile(File file) throws Exception {
        sendFileName(file);
        sendContentFile(file);
    }

    public static void main(String[] args) {
        try{
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String path = "D:/Bossy/L2/S3/ProgrammationSysteme Mr Naina/FileTransfertSocket/";
                System.out.println("\t Welcome To ShareMdg");
                System.out.println("1: Share a new file");
                System.out.println("2: Download File shared");
                System.out.println("3: Exit the program");
                System.out.println("Enter your option : ");

                int choice = Integer.parseInt(scanner.nextLine());

                if(choice < 2) {
                    System.out.println("Notice: (Your file must be located in this path D:/Bossy/L2/S3/ProgrammationSysteme Mr Naina/FileTransfertSocket/SourceFile) \n Enter the file name :");
                    String fileName = scanner.nextLine();
                    File file = new File(path +"/SourceFile/"+ fileName);
                    if(file.exists()) {
                        Client client = new Client("localhost", 4000);
                        client.outputStream.writeInt(choice);
                        client.sendFile(new File(path+"/SourceFile/"+fileName));
                        client.getSocket().close();
                        System.out.println("File sent Successfully");
                    } else {
                        System.out.println("File does not exist");
                    }
                } else if (choice < 3) {
                    System.out.println("Notice: (Your file will be located in this path D:/Bossy/L2/S3/ProgrammationSysteme Mr Naina/FileTransfertSocket/SourceFile/DownloadFile) \n Enter the file name :");
                    String fileName = scanner.nextLine();
                    Client client = new Client("localhost", 4000);
                    client.outputStream.writeInt(choice);
                    client.sendFileName(new File(fileName));
                } else if (choice < 4) {
                    System.out.println("Program exited");
                    return;
                }
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
            error.printStackTrace();
        }
    }
}
