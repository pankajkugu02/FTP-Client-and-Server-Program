import java.io.*;
import java.net.*;
import java.util.*;
class Server
{
	public static final int TIMER=40;
	public static int packsize=4;
	public static int wsize=2;
	public static int wfor=0;
	public static int lsent=0;
	
	static int packet = 0;
	static int expectedAck = 0;
	static final int timeout = 40;
	static int packetLoss = 0;
	public static void main(String a[]) throws Exception
	{
		DatagramPacket packin=null;
		DatagramPacket packout=null;
		DatagramSocket serversocket=null;
		byte[] buffin,buffout;
		
		serversocket=new DatagramSocket(8888);
		
		//Receiving message from the client

		buffin=new byte[1000];
		packin=new DatagramPacket(buffin,buffin.length);
		serversocket.receive(packin);
		String d=new String(packin.getData(),0,packin.getLength());
		System.out.println(d+"\n");
		
		//Sending directory to the client

		String lsfile="F:\\FTP using go back-n and IPV6\\box1";
		File f=new File(lsfile);
		File fl[]=f.listFiles();
		StringBuilder s=new StringBuilder("\n");
		for(int i=0;i<fl.length;i++)
		{
			if((fl[i].toString()).endsWith(".txt"))
			{
				s.append(fl[i].getName()+" "+fl[i].length()+"Bytes \n");
			}
		}
		s.append("\nEnter the file name which you want :");
		buffout=(s.toString()).getBytes();
		packout=new DatagramPacket(buffout,0,buffout.length,packin.getAddress(),packin.getPort());
		serversocket.send(packout);
		
		//Getting the name of the requested file  from the client
		buffin=new byte[100000];
		packin=new DatagramPacket(buffin,buffin.length);
		serversocket.receive(packin);
		String file=new String(packin.getData(),0,packin.getLength());
		System.out.println("Requested File is :"+file);
		boolean flag=false;
		int ind=-1;
		for(int i=0;i<fl.length;i++)
		{
			if(((fl[i].getName()).toString()).equalsIgnoreCase(file))
			{
				ind=i;
				flag=true;
			}
		}
		if(!flag)
		{
			System.out.println("ERROR");
			return;
		}
		
		File reqf=new File(fl[ind].getAbsolutePath());
		FileReader fr=new FileReader(reqf);
		BufferedReader brf=new BufferedReader(fr); 
		String se=null;
		s=new StringBuilder("");
		while((se=brf.readLine())!=null)
		{
			s.append(se+"\n");
		}
		
		
		
		
		
		//Sending file using Go Back N Protocol
		
		System.out.println("\nSending File using Go back N Protocol ");
		byte[] fbytes=(s.toString()).getBytes();
		ArrayList<Packet> sent=new ArrayList<Packet>();
		int lastseq=(int)Math.ceil((double)fbytes.length/packsize);
		
		long start = System.nanoTime();
		
		while(true)
		{
			
			while(lsent<lastseq&&lsent-wfor<wsize)
			{
				byte[] fpacket=new byte[packsize];
				
				fpacket=Arrays.copyOfRange(fbytes,lsent*packsize,lsent*packsize+packsize);
				Packet sp=new Packet(lsent,fpacket,(lsent==lastseq-1)?true:false);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(outputStream);
				os.writeObject(sp);
				byte[] sdata = outputStream.toByteArray();
				DatagramPacket p=new DatagramPacket(sdata,sdata.length,packin.getAddress(),packin.getPort());
				sent.add(sp);
				serversocket.send(p);
				
				lsent++;
			}
			byte[] ackbuff=new byte[40];
			DatagramPacket ack=new DatagramPacket(ackbuff,ackbuff.length);
			try
			{
				serversocket.setSoTimeout(TIMER);
				serversocket.receive(ack);
				ByteArrayInputStream in = new ByteArrayInputStream(ack.getData());
				ObjectInputStream is = new ObjectInputStream(in);
				ACKpacket ap=(ACKpacket)is.readObject();
				if(ap.getpacket()==lastseq)
				{
					break;
				}
				wfor=Math.max(wfor,ap.getpacket());
			}
			catch(SocketTimeoutException e)
			{
				for(int i=wfor;i<lsent;i++)
				{
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ObjectOutputStream os = new ObjectOutputStream(outputStream);
					os.writeObject(sent.get(i));
					byte[] sdata = outputStream.toByteArray();
					DatagramPacket p=new DatagramPacket(sdata,sdata.length,packin.getAddress(),packin.getPort());
					serversocket.send(p);
				}
			}
		}
		long end = System.nanoTime();
		System.out.println("\n\nFile sending process by Go back n protocol is completed and approx time taken by it is "+((end-start)/1000000)+" milli seconds");
		
    }
}