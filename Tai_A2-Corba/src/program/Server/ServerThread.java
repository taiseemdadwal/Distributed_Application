package program.Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerThread extends Thread{
	
private ClinicServer server;
	
	public ServerThread(ClinicServer server) {
		this.server = server;
		
	}
	@Override
	public void run() {
		DatagramSocket socket = null;
		try
		{
		socket = new DatagramSocket(server.getUDPPort());
		byte [] buffer = new byte[10000];
		while(true) {
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			socket.receive(request);
			String data = new String(request.getData());
			String[] requestParts = data.split(":");
			String response = "";
			if(requestParts[0].equals("RecordType")) {
				response = server.getCountByServer((requestParts[1].trim()));
			}
			
			DatagramPacket reply = new DatagramPacket(response.getBytes(), response.length(), request.getAddress(), request.getPort());
			socket.send(reply);
		}
		}
		catch(Exception err) {
			err.printStackTrace();
		}
		finally{
			socket.close();
		}

	}

}
