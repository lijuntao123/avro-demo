package com.eastsoft.avro.demo.rpc;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.Protocol;
import org.apache.avro.Protocol.Message;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.ipc.HttpTransceiver;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.generic.GenericRequestor;
import org.apache.avro.ipc.specific.SpecificRequestor;

//import com.eastsoft.avro.demo.proto2.GetDevicesReq;
//import com.eastsoft.avro.demo.proto2.GetDevicesResp;
//import com.eastsoft.avro.demo.proto2.WebProtocol;

/**
 * 客户端
 * 
 * @author ljt
 * @date 2014-7-31 13:21:05
 * 
 */
public class Client {
	private Protocol protocol = null;
	private String host = null;
	private int port = 0;
	private int size = 0;
	private int count = 0;

	public Client(Protocol protocol, String host, int port, int size, int count) {
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.size = size;
		this.count = count;
	}
	
	public static void main(String[] args) {

		String host = "129.1.77.2";
		int port = 22223;
		int size = 10;
		int count = 1;
		new Client(Utils.getProtocol(), host, port, size, count).run();

	}
	
	public long run() {
		long res = 0;
		try {
			res = sendMessage();
//			sendMessageSpecific();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

//	/**
//	 * generic code
//	 */
//	public void sendMessageSpecific(){
//		try {
//			Transceiver t = new HttpTransceiver(new URL("http://" + host + ":"
//					+ port));
//			WebProtocol proxy=(WebProtocol)SpecificRequestor.getClient(WebProtocol.class, t);
//			
//			GetDevicesReq getDevicesReq=new GetDevicesReq();
////			List
//			getDevicesReq.setAids(null);
//			GetDevicesResp getDevicesResp= proxy.pullDevice(getDevicesReq);
//			System.out.println("result="+getDevicesResp);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * do not generic code
	 * @return
	 * @throws Exception
	 */
	public long sendMessage() throws Exception {

		Message message=protocol.getMessages().get("pullDevice");	
		GenericRecord request = new GenericData.Record(message.getRequest());		
		GenericRecord requestData = new GenericData.Record(protocol.getType("GetDevicesReq"));
		
		List<Long> aids = new ArrayList<Long>();
		aids.add(1234L);
		requestData.put("aids", aids);
		request.put("getDevicesReq", requestData);
		Transceiver t = new HttpTransceiver(new URL("http://" + host + ":"
				+ port));
		GenericRequestor requestor = new GenericRequestor(protocol, t);
		GenericRecord resp = (GenericRecord) requestor.request(message.getName(),
				request);// 发送请求并得到响应结果
		System.out.println("msg=" + resp);
		List<GenericRecord> devices=(List<GenericRecord>) resp.get("virtualDevices");
		System.out.println("devices="+devices);
		
		return 0;
	}
	
	

	

}
