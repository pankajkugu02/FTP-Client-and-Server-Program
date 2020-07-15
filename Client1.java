import java.io.*;
import java.util.Scanner;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.InetAddress;
class Client1
{
	public static void main(String a[]) throws IOException
	{
		byte buff[]=null;

		//Client socket is being created

		DatagramSocket clientsocket=new DatagramSocket();
		InetAddress ip=InetAddress.getLocalHost();
		String da="Hello Server";
		buff=da.getBytes();

		//Below Packet is created to send message to the server

		DatagramPacket p=new DatagramPacket(buff,buff.length,ip,789);
		clientsocket.send(p);
		byte[] buff1=new byte[9999];

		//Below Packet is created to recieve response from the server

		DatagramPacket rec=new DatagramPacket(buff1,buff1.length);
		clientsocket.receive(rec);
		System.out.println("Server has sent - " +new String(rec.getData()));
			
	}
}