import java.io.*;
import java.util.Scanner;
import java.net.*;
class Client
{
	public static void main(String args[]) throws IOException
	{
		DatagramPacket packout = null;
		DatagramPacket packin= null;
		DatagramSocket clientsocket = null;
        byte[] buffin, buffout;
		
		InetAddress ip=InetAddress.getLocalHost();
		clientsocket=new DatagramSocket();
		String m="Server please send the directory ";
		buffout=m.getBytes();
		packout=new DatagramPacket(buffout,0,buffout.length,ip,50000);
		clientsocket.send(packout);
		buffin=new byte[1000];
		packin =new DatagramPacket(buffin,buffin.length);
		clientsocket.receive(packin);
		String data=new String(packin.getData(),0,packin.getLength());
		System.out.println(data);
		
		Scanner inp=new Scanner(System.in);
		String file=inp.nextLine();
		buffout=file.getBytes();
		packout=new DatagramPacket(buffout,0,buffout.length,ip,50000);
		clientsocket.send(packout);
		
		buffin=new byte[1000];
		packin=new DatagramPacket(buffin,buffin.length);
		clientsocket.receive(packin);
		String da=new String(packin.getData(),0,packin.getLength());
		BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		
		pw.write(da);
		pw.close();
		packin=new DatagramPacket(buffin,buffin.length);
		clientsocket.receive(packin);
		System.out.println(new String(packin.getData(),0,packin.getLength()));
	}
}
