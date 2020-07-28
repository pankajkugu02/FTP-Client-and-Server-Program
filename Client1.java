import java.io.*;
import java.util.*;
import java.net.*;
class Client
{
	public static void main(String args[]) throws Exception
	{
		DatagramPacket packout = null;
		DatagramPacket packin= null;
		DatagramSocket clientsocket = null;
        	byte[] buffin, buffout;
		
		InetAddress ip=InetAddress.getLocalHost();
		
		//Sending request packet to send the directory from where server will send the file

		clientsocket=new DatagramSocket();
		String m="Client ->>Server please send the directory ";
		buffout=m.getBytes();
		packout=new DatagramPacket(buffout,0,buffout.length,ip,50000);
		clientsocket.send(packout);
		
		//Receiving Packet containing the Directory from the server and responding the server for required file

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
		
		// Go Back N Protocol Implementation

		byte[] rdata=new byte[1000];
		int wfor=0;
		ArrayList<Packet> received=new ArrayList<Packet>();
		boolean end=false;
		while(!end)
		{
			DatagramPacket rpacket = new DatagramPacket(rdata, rdata.length);
			clientsocket.receive(rpacket);
			ByteArrayInputStream in = new ByteArrayInputStream(rpacket.getData());
			ObjectInputStream is = new ObjectInputStream(in);
			Packet p=(Packet)is.readObject();
			if(p.getseq()==wfor&&p.islast())
			{
				wfor++;
				received.add(p);
				end=true;
			}
			else if(p.getseq()==wfor)
			{
				wfor++;
				received.add(p);
			}
			ACKpacket ack=new ACKpacket(wfor);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			os.writeObject(ack);
			byte[] sdata = outputStream.toByteArray();
			DatagramPacket se=new DatagramPacket(sdata,sdata.length,ip,50000);
			clientsocket.send(se);
		}
		
		BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		for(Packet p : received)
		{
			for(byte b: p.getdata())
			{
				pw.write((char)b);
			}
		}
		
		pw.close();
		packin=new DatagramPacket(buffin,buffin.length);
		clientsocket.receive(packin);
		System.out.println(new String(packin.getData(),0,packin.getLength()));
	}
}
}
