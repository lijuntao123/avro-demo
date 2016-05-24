package com.eastsoft.avro.demo.rpc;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Protocol;
 /**
  * 获取client与server之间通信的协议
  * @author ljt
  * @date 2014-7-31 13:27:07
  *
  */
public class Utils {
    public static Protocol getProtocol() {
        Protocol protocol = null;
        try {
        	protocol = Protocol.parse(new File("src/main/conf/protocol.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return protocol;
    }
}
