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
			System.out.println("Server is not active");
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
		
		
		//Receiving data using stop and wait protocol
		boolean flag=false;
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
            
            //Inet6Address IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            
            
            //CLIENT: sending ACK
            sendData = Helper.int2ByteArray(ack);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
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
			System.out.println("\n\nFile has been sent to your using Stop and Wait protocol");
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