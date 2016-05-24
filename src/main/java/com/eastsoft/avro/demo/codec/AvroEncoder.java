package com.eastsoft.avro.demo.codec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;

/**
 * AvroEncoder<br>
 * encode a avro GenericRecord object to byte array
 * @author ljt
 * @date 2014-8-1 09:54:04
 *
 */
public class AvroEncoder {

	/**
	 * encode a avro GenericRecord object to byte array 
	 * @param record
	 * @return
	 * @throws IOException
	 */
	public byte[] encode(GenericRecord record) throws IOException {
		DatumWriter<Object> datumWriter = new GenericDatumWriter<Object>(
				record.getSchema());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Encoder encoder = EncoderFactory.get()
				.binaryEncoder(outputStream, null);
		datumWriter.write(record, encoder);
		encoder.flush();
		return outputStream.toByteArray();
	}
}
