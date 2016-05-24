package com.eastsoft.avro.demo.codec;

import org.apache.avro.Schema;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

/**
 * AvroDecoder<br>
 * decode a byte array to a avro T object
 * 
 * @author ljt
 * @date 2014-8-1 09:49:10
 * 
 */
public class AvroDecoder {
	/**
	 * decode a byte array to a object of T
	 * 
	 * @param data
	 *            byte array
	 * @param schema
	 *            avro schema
	 * @return a object of T
	 * @throws Exception
	 */
	public <T> T decode(byte[] data, Schema schema) throws Exception {
		SpecificDatumReader<T> datumReader = new SpecificDatumReader<T>(schema);
		Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);
		T result = datumReader.read(null, decoder);
		return result;
	}

}
