package com.guo.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 工具类
 * 
 */
public class CommonUtils {

	private static final Logger log = Logger.getLogger(CommonUtils.class);

	private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat FORMAT_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 输出文件
	 * 
	 * @param file
	 * @param s
	 * @throws Exception
	 */
	public static void toFile(String file, List<String> lines) throws Exception {
		if (lines == null || lines.isEmpty()) {
			return;
		}
		FileOutputStream fos = new FileOutputStream(file, true);
		PrintWriter pw = new PrintWriter(fos);
		for (String line : lines) {
			pw.append(line + "\r\n");
		}
		pw.flush();
		pw.close();
		fos.close();
	}

	/**
	 * 输出文件
	 */
	public static void toFile(OutputStream out, List<String> lines) throws Exception {
		if (lines == null || lines.isEmpty()) {
			return;
		}
		java.io.OutputStreamWriter reader = new OutputStreamWriter(out, "GBK");
		for (String line : lines) {
			reader.append(line + "\r\n");
		}
		reader.flush();
		reader.close();
	}

	public static void addFile(File file, OutputStream zos) throws IOException {
		InputStream in = new FileInputStream(file);
		byte[] b = new byte[1024000];
		int length;
		while ((length = in.read(b)) > -1) {
			zos.write(b, 0, length);
			zos.flush();
		}
		in.close();
	}

	public static void zipFiles(ZipOutputStream out, File f, String base) throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zipFiles(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}

	/**
	 * 返回时间
	 * 
	 * @param date
	 * @return
	 */
	public static String parseDate(Date date) {
		if (date == null) {
			return "";
		}
		return FORMAT.format(date);
	}

	/**
	 * 返回时间
	 * 
	 * @param date
	 * @return
	 */
	public static String format_yyyyMMdd(Date date) {
		if (date == null) {
			return "";
		}
		return FORMAT_yyyyMMdd.format(date);
	}

	public static int getIndex(List<Integer> counts, int count) {
		int r = (int) (count * Math.random());
		int sum = 0;
		int size = counts.size();
		int index = 0;
		for (int i = 0; i < size; i++) {
			if (sum <= r && r < sum + counts.get(i)) {
				index = i;
				break;
			}
			sum = sum + counts.get(i);
		}
		return index;
	}

	/**
	 * 输出文件
	 * 
	 * @param file
	 * @param s
	 * @throws Exception
	 */
	public synchronized static void toFile(String file, String s) throws Exception {
		if (s == null || s.length() == 0) {
			return;
		}
		File parent = new File(file).getParentFile();
		if (!parent.exists()) {
			if (!parent.mkdirs()) {
				throw new RuntimeException("创建[" + parent.getAbsolutePath() + "]目录失败.");
			}
		}
		FileOutputStream fos = new FileOutputStream(file, true);
		PrintWriter pw = new PrintWriter(fos);
		// pw.append("["+getTime()+"]");
		pw.append(s + "\r\n");
		pw.flush();
		pw.close();
		fos.close();
	}

	public static String subString(String begin, String end, String content) {
		if (StringUtils.isEmpty(content)) {
			return content;
		}
		int index_s = 0;
		if (begin != null) {
			index_s = content.indexOf(begin) + begin.length();
		}
		String _end = content.substring(index_s, content.length());
		int index_e = 0;

		if (end == null) {
			index_e = content.length();
		} else {
			index_e = _end.indexOf(end);
		}
		return _end.substring(0, index_e);
	}

	public static void main(String args[]) throws Exception {

	}
	public static String parseNumber(String s, String t) {
		int index = s.indexOf(t) + t.length();
		if (index == -1) {
			return "";
		}
		String cc = "0123456789";
		String c;
		String ss = "";
		for (int i = index; i < s.length(); i++) {
			c = s.charAt(i) + "";
			if (cc.contains(c)) {
				ss = ss + c;
			} else {
				break;
			}
		}
		return ss;
	}
}
