import java.io.Serializable;
public class ACKpacket implements Serializable
{
	int p;
	public ACKpacket(int p)
	{
		this.p=p;
	}
	public int  getpacket()
	{
		return p;
	}
	public void setpacket(int p)
	{
		this.p=p;
	}
}
