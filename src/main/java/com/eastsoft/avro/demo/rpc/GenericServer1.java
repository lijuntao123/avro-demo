package com.eastsoft.avro.demo.rpc;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.Protocol;
import org.apache.avro.Protocol.Message;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.ipc.HttpServer;
import org.apache.avro.ipc.generic.GenericResponder;

/**
 * GenericResponder
 * 
 * @author ljt
 * @date 2014-8-26 10:52:47
 * 
 */
public class GenericServer1 extends GenericResponder {
	private Protocol protocol = null;
	private int port;

	public static void main(String[] args) {

		int port = 22223;
		new GenericServer1(Utils.getProtocol(), port).run();
	}

	public GenericServer1(Protocol protocol, int port) {
		super(protocol);
		this.protocol = protocol;
		this.port = port;
	}

	// * 客户端请求到来时，调用该方法
	@Override
	public Object respond(Message message, Object request) throws Exception {
		return mRespond3(message, request);
	}

	@Override
	public List<ByteBuffer> respond(List<ByteBuffer> buffers)
			throws IOException {
		return super.respond(buffers);
	}

	public Object mRespond3(Message message, Object request) {
		GenericRecord req = (GenericRecord) request;
		System.out.println("request==" + request);
		GenericRecord responseData = new GenericData.Record(
				message.getResponse());
		List<GenericRecord> virList = new ArrayList<GenericRecord>();
		GenericRecord virtualDev = new GenericData.Record(
				protocol.getType("VirtualDev"));
		virtualDev.put("id", "111");
		virtualDev.put("name", "空调1");
		virtualDev.put("typeId", "3");
		virtualDev.put("roomCode", "101");

		List<GenericRecord> devices = new ArrayList<GenericRecord>();
		GenericRecord plcDevice = null;
		for (int i = 0; i < 5; i++) {
			plcDevice = new GenericData.Record(protocol.getType("PlcDev"));
			plcDevice.put("aid", (long) i);
			plcDevice.put("name", "红外" + i);
			plcDevice.put("roomCode", "room" + i);
			plcDevice.put("sid", i + 2);
			plcDevice.put("category", 1234567L + i);
			plcDevice.put("categorySubjoin", 10 + i);
			plcDevice.put("channel", 1);
			
			devices.add(plcDevice);
		}
		virtualDev.put("plcDevices", devices);

		virList.add(virtualDev);
		responseData.put("virtualDevices", virList);

		return responseData;
	}

	

	public void run() {
		try {
			HttpServer server = new HttpServer(this, port);

			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
