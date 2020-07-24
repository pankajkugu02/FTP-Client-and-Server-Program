import java.net.*;
import java.io.*;
public class ftpserver
{
            public static void main(String args[])throws IOException
            {
                        byte b[]=new byte[3072];
                        DatagramSocket dsoc=new DatagramSocket(1000);
                        FileOutputStream f=new FileOutputStream("L:/output.txt");
                        while(true)
                        {
                                    DatagramPacket dp=new DatagramPacket(b,b.length);
                                    dsoc.receive(dp);
		System.out.println("output.txt created->data stored in output file"); 
                                    System.out.println(new String(dp.getData(),0,dp.getLength()));
		for(int i=0;i<b.length;i++)
		{
			f.write(b[i]);
		}
     		f.close();
	
                        }
            }
}