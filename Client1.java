import java.io.*;
import java.util.*;
import java.net.*;
import java.io.File;
import java.io.FileOutputStream;
class Client
{
	static ArrayList<byte[]> packets = new ArrayList();
	static int previousAck = 0;
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
			DatagramPacket se=new DatagramPacket(sdata,sdata.length,ip,50000);
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
			System.out.println("\nFile has been received in directory Go back n protocol");	
		flag=false;
		
		
		
		
		
		
		//Receiving data using stop and wait protocol
		
		int ack = 0;
		byte[] previousData = new byte[512];
        
        while (true)
        {
            byte[] receiveData = new byte[512];
            byte[] sendData = new byte[512];
            
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            try
            {
                clientsocket.receive(receivePacket);
            }
            catch (Exception e)
            {
                System.out.println("SERVER: Inactive for too long (15 seconds). Goodbye.");
                clientsocket.close();
                
                break;
            }
            
            byte[] receivedData = receivePacket.getData();
            
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            
            
            //CLIENT: sending ACK
            sendData = Helper.int2ByteArray(ack);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientsocket.send(sendPacket);
            sendData = new byte[512];
            
            if (!Arrays.equals(receivedData, previousData))
            {
                int length = Helper.byteArray2Int(new byte[] {receivedData[508], receivedData[509], receivedData[510], receivedData[511]});
                packets.add(Arrays.copyOfRange(receivedData, 0, length));
                previousAck = ack;
                ack = (ack + 1) % 2;
                if (length < 508)
                    break;
            }
            else
            {
                ack = previousAck;
            }
            previousData = receivedData;
            
            
        }
		createDirectory("stopandwait");
        try
        {
            FileOutputStream os = new FileOutputStream("stopandwait/"+file);
			flag=true;
            for (int i = 0; i < packets.size(); i++)
            {
                os.write(packets.get(i));
            }
            os.close();
        }
        catch (Exception ex)
        {
            System.out.println("Problem occured writing to file");
        }
		if(flag)
			System.out.println("\n\nFile has been received in your directory Stop and Wait protocol");
	}
	private static void createDirectory(String directoryName)
    {
        File dir = new File(directoryName);
        dir.mkdir();
    }
}
