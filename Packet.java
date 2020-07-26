import java.io.Serializable;
import java.util.Arrays;


public class Packet implements Serializable 
{

	public int seq;
	
	public byte[] data;
	
	public boolean last;

	public Packet(int seq, byte[] data, boolean last) {
		super();
		this.seq = seq;
		this.data = data;
		this.last = last;
	}

	public int getseq() {
		return seq;
	}

	public void setseq(int seq) {
		this.seq = seq;
	}

	public byte[] getdata() {
		return data;
	}

	public void setdata(byte[] data) {
		this.data = data;
	}

	public boolean islast() {
		return last;
	}

	public void setlast(boolean last) {
		this.last = last;
	}

	@Override
	public String toString() {
		return "UDPPacket [seq=" + seq + ", data=" + Arrays.toString(data)
				+ ", last=" + last + "]";
	}
	
}