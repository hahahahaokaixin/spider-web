package com.guo.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * Created by guoyu on 2016/4/14.
 */
public class FastDFSUtil {
	private static Logger LOGGER = Logger.getLogger(FastDFSUtil.class);

	static {
		try {
			ClientGlobal.init(Constant.CONNECT_TIMEOUT, Constant.NETWORK_TIMEOUT, Constant.CHARSET, Constant.FAST_HTTP_PORT,
					Constant.ANTI_STEAL_TOKEN, Constant.KEY, Constant.TRACKER_SERVER);

			LOGGER.info("########初始化DFS配置成功########");
		} catch (Exception e) {
			LOGGER.error("########初始化DFS配置异常########", e);
		}
	}

	public static StorageClient getStorageClient() {
		try {
			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageClient storageClient = new StorageClient(trackerServer, null);
			return storageClient;
		} catch (Exception e) {
			LOGGER.error("########初始化DFS配置异常########", e);
		}
		return null;
	}

	/**
	 * 上传文件到DFS的方法
	 *
	 * @param file
	 *            文件
	 * @param fileExtName
	 *            文件扩展名
	 * @param nameValuePair
	 * @return url
	 */
	public static String upload(File file, String fileExtName, Map<String, String> nameValuePair) throws Exception {
		String url = "";

		try {

			NameValuePair nameValuePairs[] = new NameValuePair[nameValuePair.size()];
			Set<String> keys = nameValuePair.keySet();
			int i = 0;
			for (String k : keys) {
				nameValuePairs[i] = new NameValuePair(k, nameValuePair.get(k));
				i++;
			}

			byte[] b = fileToBetyArray(file);

			String fileIds[] = FastDFSUtil.getStorageClient().upload_file(b, fileExtName, nameValuePairs);

			// LOGGER.info("########DFS上传文件成功,组名：" + fileIds[0] + ",路径: " +
			// fileIds[1] + "########");

			url = fileIds[0] + "/" + fileIds[1];
		} catch (Exception e) {
			url = null;
			LOGGER.error("########DFS上传文件异常########", e);
		}

		return url;
	}

	/**
	 * 上传文件到DFS的方法
	 *
	 * @param file
	 *            文件
	 * @param fileExtName
	 *            文件扩展名
	 * @param nameValuePair
	 * @return url
	 */
	public static String[] uploads(File file, String fileExtName, Map<String, String> nameValuePair) throws Exception {

		try {

			NameValuePair nameValuePairs[] = new NameValuePair[nameValuePair.size()];
			Set<String> keys = nameValuePair.keySet();
			int i = 0;
			for (String k : keys) {
				nameValuePairs[i] = new NameValuePair(k, nameValuePair.get(k));
				i++;
			}

			byte[] b = fileToBetyArray(file);

			String fileIds[] = FastDFSUtil.getStorageClient().upload_file(b, fileExtName, nameValuePairs);

			// /LOGGER.info("########DFS上传文件成功,组名：" + fileIds[0] + ",路径: " +
			// fileIds[1] + "########");

			return fileIds;
		} catch (Exception e) {

			LOGGER.error("########DFS上传文件异常########", e);
		}
		return null;

	}

	/**
	 * 上传文件到DFS的方法
	 *
	 * @param filePath
	 *            本地文件路径
	 * @param fileExtName
	 *            文件扩展名
	 * @return url
	 */
	public static String upload(String filePath, String fileExtName) throws Exception {
		String url = "";

		try {
			Map<String, String> nameValuePair = new HashMap<String, String>();
			nameValuePair.put("fileName", filePath);
			nameValuePair.put("fileExtName", fileExtName);

			NameValuePair nameValuePairs[] = new NameValuePair[nameValuePair.size()];
			Set<String> keys = nameValuePair.keySet();
			int i = 0;
			for (String k : keys) {
				nameValuePairs[i] = new NameValuePair(k, nameValuePair.get(k));
				i++;
			}

			String fileIds[] = FastDFSUtil.getStorageClient().upload_file(filePath, fileExtName, nameValuePairs);

			// LOGGER.info("########DFS上传文件成功,组名：" + fileIds[0] + ",路径: " +
			// fileIds[1] + "########");

			url = fileIds[0] + "/" + fileIds[1];
		} catch (Exception e) {
			url = null;
			LOGGER.error("########DFS上传文件异常########", e);
		}

		return url;
	}

	/**
	 * 上传文件到DFS的方法
	 *
	 * @param filePath
	 *            本地文件路径
	 * @param fileExtName
	 *            文件扩展名
	 * @return String[]
	 */
	public static String[] uploads(String filePath, String fileExtName) throws Exception {

		try {
			Map<String, String> nameValuePair = new HashMap<String, String>();
			nameValuePair.put("fileName", filePath);
			nameValuePair.put("fileExtName", fileExtName);

			NameValuePair nameValuePairs[] = new NameValuePair[nameValuePair.size()];
			Set<String> keys = nameValuePair.keySet();
			int i = 0;
			for (String k : keys) {
				nameValuePairs[i] = new NameValuePair(k, nameValuePair.get(k));
				i++;
			}

			String fileIds[] = FastDFSUtil.getStorageClient().upload_file(filePath, fileExtName, nameValuePairs);

			// LOGGER.info("########DFS上传文件成功,组名：" + fileIds[0] + ",路径: " +
			// fileIds[1] + "########");

			return fileIds;
		} catch (Exception e) {

			LOGGER.error("########DFS上传文件异常########", e);
		}
		return null;

	}

	/**
	 * 上传文件到DFS的方法
	 *
	 * @param file
	 *            文件,默认扩展名: .jpg
	 * @return url
	 */
	public static String uploadFile(File file) throws Exception {
		String url = "";

		try {

			byte[] b = fileToBetyArray(file);

			String fileIds[] = FastDFSUtil.getStorageClient().upload_file(b, "jpg", null);

			// LOGGER.info("########DFS上传文件成功,组名：" + fileIds[0] + ",路径: " +
			// fileIds[1] + "########");

			url = fileIds[0] + "/" + fileIds[1];
		} catch (Exception e) {
			url = null;
			LOGGER.error("########DFS上传文件异常########", e);
		}

		return url;
	}

	public static String uploadFile(File file, String extName) throws Exception {
		String url = "";

		try {

			byte[] b = fileToBetyArray(file);

			String fileIds[] = FastDFSUtil.getStorageClient().upload_file(b, extName, null);

			// LOGGER.info("########DFS上传文件成功,组名：" + fileIds[0] + ",路径: " +
			// fileIds[1] + "########");

			url = fileIds[0] + "/" + fileIds[1];
		} catch (Exception e) {
			url = null;
			LOGGER.error("########DFS上传文件异常########", e);
		}

		return url;
	}

	/**
	 * 上传文件到DFS的方法
	 *
	 * @param file
	 *            文件,默认扩展名: .jpg
	 * @return String[]
	 */
	public static String[] uploadFiles(File file) throws Exception {

		try {

			byte[] b = fileToBetyArray(file);

			String fileIds[] = FastDFSUtil.getStorageClient().upload_file(b, "jpg", null);

			// LOGGER.info("########DFS上传文件成功,组名：" + fileIds[0] + ",路径: " +
			// fileIds[1] + "########");

			return fileIds;
		} catch (Exception e) {

			LOGGER.error("########DFS上传文件异常########", e);
		}
		return null;

	}

	/**
	 * 上传文件到DFS的方法
	 *
	 * @param file
	 *            文件,默认扩展名: .jpg
	 * @return url
	 */
	public static String[] uploadFiles(byte[] b, String ext) throws Exception {

		try {

			String fileIds[] = FastDFSUtil.getStorageClient().upload_file(b, ext, null);
			return fileIds;
		} catch (Exception e) {

			LOGGER.error("########DFS上传文件异常########", e);
		}
		return null;

	}

	/**
	 * File 转 Byte[]
	 */
	private static byte[] fileToBetyArray(File file) {
		FileInputStream fileInputStream = null;
		byte[] bFile = null;
		try {
			bFile = new byte[(int) file.length()];
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);

			// LOGGER.info("########文件创建成功########");
		} catch (Exception e) {
			LOGGER.error("########文件创建异常########", e);
		} finally {
			try {
				fileInputStream.close();

			} catch (IOException e) {
				LOGGER.error("########IO关闭异常########", e);
			}
		}

		return bFile;
	}

	public static void main(String[] args) throws Exception {
		// String conf_filename = "D:\\workspace\\BDS
		// project\\fastdfs_test\\config\\fdfs.conf";

		String local_filename = "D:\\20160406231404660.png";
		// D:\BDS\IHGRC Jojo\会员图片\答题记录20160329\新建文件夹
		File file = new File(local_filename);
		Map<String, String> nameValuePair = new HashMap<String, String>();
		nameValuePair.put("fileName", local_filename);
		nameValuePair.put("fileExtName", "png");

		/*
		 * ClientGlobal.init(Constant.CONNECT_TIMEOUT, Constant.NETWORK_TIMEOUT,
		 * Constant.CHARSET, Constant.HTTP_PORT, Constant.ANTI_STEAL_TOKEN,
		 * Constant.KEY, Constant.TRACKER_SERVER);
		 */

		String url = FastDFSUtil.upload(local_filename, "png");
		System.out.println(url);

		String url1 = FastDFSUtil.uploadFile(file);
		System.out.println(url1);
	}
}
