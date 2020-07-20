import java.io.*;
import java.net.*;
class Server
{
	public static void main(String a[]) throws IOException
	{
		DatagramPacket packin=null;
		DatagramPacket packout=null;
		DatagramSocket serversocket=null;
		byte[] buffin,buffout;
		
		serversocket=new DatagramSocket(50000);
		buffin=new byte[1000];
		packin=new DatagramPacket(buffin,buffin.length);
		serversocket.receive(packin);
		String d=new String(packin.getData(),0,packin.getLength());
		System.out.println(d+"\n");
		String lsfile="C:/Users/Pankaj/Desktop/Server";
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
			s.append(se);
		}
		buffout=(s.toString()).getBytes();
		packout=new DatagramPacket(buffout,0,buffout.length,packin.getAddress(),packin.getPort());
		serversocket.send(packout);
		String res="File has been sent to you ";
		buffout=res.getBytes();
		packout=new DatagramPacket(buffout,0,buffout.length,packin.getAddress(),packin.getPort());
		serversocket.send(packout);
	}
}
