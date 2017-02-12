package com.guo.base.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import com.guo.base.util.ZLibUtils;

public class CustomGetMethod extends org.apache.commons.httpclient.methods.GetMethod {

	public CustomGetMethod(String uri) {

		super(uri);

	}

	/**
	 * Get response as string whether response is GZipped or not
	 * 
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	@Override
	public String getResponseBodyAsString() throws IOException {

		GZIPInputStream gzin;
		//System.out.println("###############" + getResponseHeader("Content-Encoding") + "###############");
		if (getResponseBody() != null || getResponseStream() != null) {

			if ((getResponseHeader("Content-Encoding") != null
					&& getResponseHeader("Content-Encoding").getValue().toLowerCase().indexOf("gzip") > -1)) {

				// For GZip response

				InputStream is = getResponseBodyAsStream();

				gzin = new GZIPInputStream(is);

				InputStreamReader isr = new InputStreamReader(gzin, getResponseCharSet());

				java.io.BufferedReader br = new java.io.BufferedReader(isr);

				StringBuffer sb = new StringBuffer();

				String tempbf;

				while ((tempbf = br.readLine()) != null) {
					sb.append(tempbf);
					sb.append("\r\n");
				}
				isr.close();
				gzin.close();
				return sb.toString();
			}
			if ((getResponseHeader("Content-Encoding") != null
					&& getResponseHeader("Content-Encoding").getValue().toLowerCase().indexOf("deflate") > -1)) {

				InflaterInputStream iis = new InflaterInputStream(this.getResponseBodyAsStream());
				byte[] ret = null;
				try {
					ret = ZLibUtils.decompress(iis);
				} catch (DataFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String outputStr = new String(ret);

				return outputStr.toString();

			} else {
				// For deflate response
				return super.getResponseBodyAsString();
			}
		} else {
			return super.getResponseBodyAsString();

		}
	}

	private static final int BUFFER_SIZE = 4 * 1024;

	public byte[] compress(byte[] data, Level level) throws IOException {

		Deflater deflater = new Deflater();
		// set compression level
		deflater.setLevel(level.getLevel());
		deflater.setInput(data);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

		deflater.finish();
		byte[] buffer = new byte[BUFFER_SIZE];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer); // returns the generated
													// code... index
			outputStream.write(buffer, 0, count);
		}
		byte[] output = outputStream.toByteArray();
		outputStream.close();
		return output;
	}

	public byte[] decompress(byte[] data) throws IOException, DataFormatException {

		Inflater inflater = new Inflater();
		inflater.setInput(data);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[BUFFER_SIZE];
		while (!inflater.finished()) {
			int count = inflater.inflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		byte[] output = outputStream.toByteArray();
		outputStream.close();
		return output;
	}

	/**
	 * Compression level
	 */
	public static enum Level {

		/**
		 * Compression level for no compression.
		 */
		NO_COMPRESSION(0),

		/**
		 * Compression level for fastest compression.
		 */
		BEST_SPEED(1),

		/**
		 * Compression level for best compression.
		 */
		BEST_COMPRESSION(9),

		/**
		 * Default compression level.
		 */
		DEFAULT_COMPRESSION(-1);

		private int level;

		Level(

				int level) {
			this.level = level;
		}

		public int getLevel() {
			return level;
		}
	}

}
