import java.io.*;
import java.util.*;
import java.net.*;
import java.io.File;
import java.io.FileOutputStream;
class Client
{
	
	private static int defport=8888;
	private static Inet6Address ip;
	private static DatagramSocket clientsocket= null;
	public static void Client() throws SocketException {
		
	}
	private static void initi() throws Exception
	{
		clientsocket= new DatagramSocket(defport);
		clientsocket.setReuseAddress(true);
	}
	static ArrayList<byte[]> packets = new ArrayList();
	static int previousAck = 0;
	public static void main(String args[]) throws Exception
	{
		DatagramPacket packout = null;
		DatagramPacket packin= null;
		
        	byte[] buffin, buffout;
		Scanner inp=new Scanner(System.in);
		System.out.print("Enter the address of the server : ");
		String address=inp.nextLine();
		if(connectipv6(address))
			System.out.println("Server is active now");
		else
			System.out.println("Server is not active now");
        	//getByAddress() method 
		String m="Client ->>Server please send the directory ";
		buffout=m.getBytes();
		packout=new DatagramPacket(buffout,0,buffout.length,ip,defport);
		clientsocket.send(packout);
		
		//Receiving Packet containing the Directory from the server and responding the server for required file

		buffin=new byte[1000];
		packin =new DatagramPacket(buffin,buffin.length);
		clientsocket.receive(packin);
		String data=new String(packin.getData(),0,packin.getLength());
		System.out.println(data);
		String file=inp.nextLine();
		buffout=file.getBytes();
		packout=new DatagramPacket(buffout,0,buffout.length,ip,defport);
		clientsocket.send(packout);
		
		// Receiving file using Go back n Protocol

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
			DatagramPacket se=new DatagramPacket(sdata,sdata.length,ip,defport);
			clientsocket.send(se);
		}
		createDirectory("gobackn");
		boolean flag=false;
        try
        {
			
            FileOutputStream pw = new FileOutputStream("gobackn/"+file);
			flag=true;
			for(Packet p : received)
			{
				for(byte b: p.getdata())
				{
					pw.write((char)b);
				}
			}
			pw.close();
        }
        catch (Exception ex)
        {
            System.out.println("Problem occured writing to file");
        }
		if(flag)
			System.out.println("\nFile has been sent  to your directory using Go back n protocol");	
		
		
		
		
		
		
		
	}
	private static void createDirectory(String directoryName)
    {
        File dir = new File(directoryName);
        dir.mkdir();
    }
	public static boolean connectipv6(String address) throws UnknownHostException,IOException,Exception
	{
		initi();
		ip = (Inet6Address) Inet6Address.getByName(address);
		if(ip.isReachable(5000)) {
			return true;
		}
		return false;
	}
}