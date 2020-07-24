import java.net.*;
import java.io.*;
public class ftpclient
{
            public static void main(String args[])throws Exception
            {         
                        byte b[]=new byte[1024];
                        FileInputStream f=new FileInputStream("L:/input.txt");
                        DatagramSocket dsoc=new DatagramSocket(2000);
                        int i=0;
                        while(f.available()!=0)
                        {
                                    b[i]=(byte)f.read();
                                    i++;
                        } 
	                    System.out.println("input.txt->file are read from client side");                   
                        f.close();
	                    //ip=InetAddress.getLocalHost()//
                        dsoc.send(new DatagramPacket(b,i,InetAddress.getLocalHost(),1000));
            }

}