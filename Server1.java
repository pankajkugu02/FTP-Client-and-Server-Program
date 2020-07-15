import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.io.IOException;
import java.net.InetAddress;
class Server1
{
	public static void main(String a[]) throws IOException
	{
		  byte[] da = new byte[9999];

		  //Packet created to recieve message from server
		  DatagramPacket recda = null;
	          recda = new DatagramPacket(da, da.length);

		  //Server Socket is being created
		  DatagramSocket serversocket = new DatagramSocket(789);
  		   
		  serversocket.receive(recda); 
  		  System.out.println("Client has sent :-" + new String(recda.getData())); 
		  byte[] re=null;
		  String p="Hello Client";
		  re=p.getBytes();

		  //Packet is created to send response to the Client

		  DatagramPacket res=new DatagramPacket(re,re.length,recda.getAddress(),recda.getPort());
		  serversocket.send(res);	  
    	}
}