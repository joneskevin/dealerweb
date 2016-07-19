/*******************************************************************************
 * 
 * 程序开发: 王健
 * 版权所有: 上海王者归来 
 * 公司主页: http://www.kingsh.com 
 * 电邮地件: wj@kingsh.com wjsquall@hotmail.com
 *  
 ******************************************************************************/

package com.ava.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import com.ava.resource.GlobalConstant;
import com.ava.util.encrypt.EncryptUtil;

public class FileUtil {

	public static String invaildChars[] = { "\\", "/", ":", "*", "?", "\"",
			"<", ">", "|" };

	/**
	 * 列出一个目录(FilePath)下的文件或目录 @ list_type == 0 列出下层子目录（只有一层） @ list_type == 1
	 * 列出文件（不包括目录名，只有文件名） @ 其他 列出所有�г�����
	 */
	public static ArrayList listFromDir(String FilePath, int list_type) {
		File path = new File(FilePath);
		String[] list1;
		ArrayList<Object> list2 = new ArrayList<Object>();
		String separator = GlobalConstant.FILE_SEPARATOR;
		if (path.isDirectory()) {
			list1 = path.list();
			for (int i = 0; i < list1.length; i++) {
				File f = new File(FilePath + separator + list1[i]);
				if (list_type == 0) {
					if (f.isDirectory()) {
						list2.add(list1[i]);
					}
				} else if (list_type == 1) {
					if (f.isFile()) {
						list2.add(list1[i]);
					}
				} else
					list2.add(list1[i]);
			}

		}

		return list2;
	}

	/** 得到一个目录(FilePath)下所有目录和子目录中文件大小的总和 */
	public static long getDirSize(String FilePath) {
		long total_size = 0;
		String s_dir = FilePath;
		String separator = GlobalConstant.FILE_SEPARATOR;
		try {
			File path = new File(s_dir);
			if (path.isDirectory()) {
				String[] list;
				list = path.list();
				for (int i = 0; i < list.length; i++) {
					File f = new File(s_dir + separator + list[i]);
					if (f.isFile()) {
						total_size += f.length();
					} else if (f.isDirectory()) {
						total_size += getDirSize(f.getAbsolutePath());
					}
					// System.out.println(list[i]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return total_size;

	}

	/** 得到一个目录(FilePath)的剩余空间 */
	public static long getDirSizeRemain(String FilePath, int total_size_M) {
		int total_size_B = total_size_M * 1024 * 1024;
		return total_size_B - getDirSize(FilePath);
	}

	/** 得到文件或目录的信息 */
	public static String getFileInfo(String file_path, int info_type) {
		if (file_path == null || file_path.length() == 0) {
			return null;
		}
		String result = new String("");
		File myFile = new File(file_path);

		if (info_type == 1) {
			java.util.Calendar c = java.util.Calendar.getInstance();
			Date d = new Date(myFile.lastModified());
			c.setTime(d);
			int year = c.get(java.util.Calendar.YEAR);
			int month = c.get(java.util.Calendar.MONTH) + 1;
			int day = c.get(java.util.Calendar.DAY_OF_MONTH);
			int hour = c.get(java.util.Calendar.HOUR_OF_DAY);
			int minute = c.get(java.util.Calendar.MINUTE);

			StringBuffer r = new StringBuffer(512);
			r.append(year);
			r.append("��");
			r.append(String.valueOf(100 + month).substring(1));
			r.append("��");
			r.append(String.valueOf(100 + day).substring(1));
			r.append("��");
			r.append(String.valueOf(100 + hour).substring(1));
			r.append(":");
			r.append(String.valueOf(100 + minute).substring(1));
			r.append("��");

			result = r.toString();
			// ������� 2002��10��30��09:57��
		}
		return result;
	}

	/**
	 * ͨ�����ļ�path�õ�通过给定的文件path得到路径名称，最后带分隔符
	 * 例如:/usr/local/tomcat/webapps/demosite/index.jsp
	 * 返回:/usr/local/tomcat/webapps/demosite/
	 */
	public static final String getPath(String file_path) {
		if (file_path == null || file_path.length() == 0) {
			return null;
		}
		int endIndex = file_path.lastIndexOf(GlobalConstant.FILE_SEPARATOR);
		if (endIndex > -1) {
			file_path = file_path.substring(0, endIndex + 1);
		} else {
			file_path = "";
		}
		return file_path;
	}

	/** �õ�得到一个文件的大小（字节数），文件不存在或出错返回-1 */
	public static long getFileSize(String file_path) {
		if (file_path == null || file_path.length() == 0) {
			return -1;
		}
		File myFile = new File(file_path);
		if (myFile.exists() && myFile.isFile())
			return myFile.length();
		else
			return -1;
	}

	/** 判断是否是一个目录 */
	public static boolean isDirectory(String file_path) {
		if (file_path == null || file_path.length() == 0) {
			return false;
		}
		File myFile = new File(file_path);
		if (myFile.exists() && myFile.isDirectory())
			return true;
		else
			return false;
	}

	/** 判断是否是存在的一个文件 */
	public static boolean isFile(String file_path) {
		if (file_path == null || file_path.length() == 0) {
			return false;
		}
		File myFile = new File(file_path);
		if (myFile.exists() && myFile.isFile())
			return true;
		else
			return false;
	}

	/** 得到一个目录下文件的个数,不包括子目录 */
	public static int getFileNum(String file_path) {
		if (file_path == null || file_path.length() == 0) {
			return -1;
		}
		int result = 0;
		File path = new File(file_path);
		String[] list1;
		String separator = GlobalConstant.FILE_SEPARATOR;
		if (path.isDirectory()) {
			list1 = path.list();
			for (int i = 0; i < list1.length; i++) {
				File f = new File(file_path + separator + list1[i]);
				if (f.isFile()) {
					result++;
				}
			}
		}
		return result;
	}

	/** 得到一个目录下文件的个数,包括子目录 */
	public static int getAllFileNum(String file_path) {
		if (file_path == null || file_path.length() == 0) {
			return -1;
		}
		int result = 0;
		File path = new File(file_path);
		String[] list1;
		String separator = GlobalConstant.FILE_SEPARATOR;
		if (path.isDirectory()) {
			list1 = path.list();
			for (int i = 0; i < list1.length; i++) {
				File f = new File(file_path + separator + list1[i]);
				if (f.isFile()) {
					result++;
				} else {
					result = result
							+ getAllFileNum(file_path + separator + list1[i]);
				}
			}
		}
		return result;
	}

	/**
	 * 文件搜索,搜索目录 file_path 下的所有文件(包括子目录)，找出文件名中包含字符串 str_search 的文件名（不包含路径）,并将它们
	 * 放入一个 ArrayList 中返回
	 */
	public static ArrayList fileSearch(String file_path, String str_search) {
		return fileSearch(file_path, str_search, 0);
	}

	/**
	 * 文件搜索,搜索目录 file_path 下的所有文件(包括子目录)，找出文件名中包含字符串 str_search 的文件名,并将它们 放入一个
	 * ArrayList 中返回 @ fileviewType = 0 返回的文件名不包含路径 @ fileviewType = 1
	 * 返回的文件名包含路径
	 */
	public static ArrayList fileSearch(String file_path, String str_search,
			int fileviewType) {
		if (file_path == null || file_path.length() == 0) {
			return null;
		}
		ArrayList<Object> searchResult = new ArrayList<Object>();
		File path = new File(file_path);
		String[] list1;
		// int list2_length = 0;
		String separator = GlobalConstant.FILE_SEPARATOR;
		if (path.isDirectory()) {
			list1 = path.list();
			if (list1 != null) {
				for (int i = 0; i < list1.length; i++) {
					File f = new File(file_path + separator + list1[i]);
					if (f.isDirectory()) {
						ArrayList tempResult = fileSearch(f.getPath(),
								str_search, fileviewType);
						if (!tempResult.isEmpty()) {
							for (int j = 0; j < tempResult.size(); j++) {
								searchResult.add(tempResult.get(j));
							}
						}
					}
				}
			}
			if (list1 != null) {
				for (int i = 0; i < list1.length; i++) {
					File f = new File(file_path + separator + list1[i]);
					if (f.isFile()) {
						if (list1[i].toLowerCase().indexOf(
								str_search.toLowerCase()) > -1) {
							if (fileviewType == 0) {
								searchResult.add(f.getName());
							} else {
								searchResult.add(f.getPath());
							}
						}

					}
				}
			}

		}
		return searchResult;
	}

	/** 创建一个新目录����Ŀ¼ */
	public static void mkDir(String FilePath) {
		if (FilePath == null || FilePath.length() == 0) {
			return;
		}
		try {
			File file = new File(FilePath);
			if (!file.exists()) {
				file.mkdirs();
				System.out.println("Sueecss making dir:" + FilePath);
			}
		} catch (Exception exception) {
			System.out.println("Error making dir:" + FilePath);
		}
	}

	/**
	 * 强制复制单个文件，如果目标文件已存在，则覆盖
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/moumoulrc.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/moumo/hello.jsp
	 * @return boolean
	 */
	public static void foceCopyFile(String file_path_orig, String file_path_dest) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(file_path_orig);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(file_path_orig); // 读入原文件
				mkDir(getPath(file_path_dest)); // 生成新目录
				FileOutputStream fs = new FileOutputStream(file_path_dest);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					// System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 复制单个文件，如果目标文件已存在，则不复制操作
	 * 
	 * @param file_path_orig
	 *            String 原文件路径 如：c:/moumoulrc.txt
	 * @param file_path_dest
	 *            String 复制后路径 如：f:/moumo/hello.jsp
	 */
	public static void copyFile(String file_path_orig, String file_path_dest) {
		if (!isFile(file_path_dest)) {
			foceCopyFile(file_path_orig, file_path_dest);
		}
	}

	/**
	 * 强制移动文件，如果目标文件已存在，则覆盖
	 * 
	 * @param file_path_orig
	 *            原始文件名（包括路径）
	 * @param file_path_dest
	 *            目标文件名（包括路径）
	 */
	public static boolean forceMoveFile(String file_path_orig,
			String file_path_dest) {
		delFile(file_path_dest);
		return moveFile(file_path_orig, file_path_dest);
	}

	/**
	 * 移动文件，如果目标文件已存在，则不移动操作
	 * 
	 * @param file_path_orig
	 *            原始文件名（包括路径）
	 * @param file_path_dest
	 *            目标文件名（包括路径）
	 */
	public static boolean moveFile(String file_path_orig, String file_path_dest) {
		if (file_path_orig == null || file_path_orig.length() == 0
				|| file_path_dest == null || file_path_dest.length() == 0) {
			return false;
		}
		if (file_path_orig.equals(file_path_dest)) {
			return false;
		}
		File file_orig = new File(file_path_orig);
		mkDir(getPath(file_path_dest));
		File file_dest = new File(file_path_dest);
		return file_orig.renameTo(file_dest);
	}

	/** 移动目录 */
	public static boolean moveDir(String file_path, String file_path_dest) {
		if (file_path == null || file_path.length() == 0) {
			return false;
		}
		// renameTo(File dest)
		if (isDirectory(file_path)) {
			// 创建目标目录����Ŀ��Ŀ¼
			mkDir(file_path_dest);
			// 删除历史目录
			delDir(file_path);
			return moveFile(file_path, file_path_dest);
		}
		return false;
	}

	/** 删除文件 */
	public static boolean delFile(String file_path) {
		if (file_path == null || file_path.length() == 0) {
			return false;
		}
		boolean result = false;
		File myFile = new File(file_path);
		if (myFile.exists() && myFile.isFile()) {
			if (myFile.delete())
				result = true;
		}
		return result;
	}

	/** 删除一个目录下的所有文件 */
	public static boolean delFiles(String filedir) {
		File path = new File(filedir);
		String[] list1;
		String separator = GlobalConstant.FILE_SEPARATOR;
		if (path.isDirectory()) {
			list1 = path.list();
			File f;
			for (int i = 0; i < list1.length; i++) {
				f = new File(filedir + separator + list1[i]);
				if (f.isFile()) {
					// filapath = filedir + separator + list1[i];
					// delFile(filapath);
					f.delete();
				}

			}
			return true;

		} else {
			return false;
		}
	}

	/** 删除目录 */
	public static boolean delDir(String file_path) {
		boolean result = false;
		File path = new File(file_path);
		if (path.isDirectory()) {
			if (path.delete())
				result = true;
		}

		return result;
	}

	public static String getLastModified(File f, String format) {
		/*
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 * if(format.equals("ymdh")) sdf = new SimpleDateFormat("yyyy-MM-dd
		 * hh"); else if(format.equals("ymdhm")) sdf = new
		 * SimpleDateFormat("yyyy-MM-dd hh:mm"); return sdf.format((new
		 * Date(f.lastModified())));
		 */
		return "";
	}

	/** 检查一个文件名中是否含有非法字符 */
	private static boolean isVaildFilename(String filename) {
		if (filename == null || filename.equals(""))
			return false;
		for (int i = 0; i < invaildChars.length; i++) {
			if (filename.indexOf(invaildChars[i]) > -1)
				return false;
		}
		return true;
	}

	/** fileSize：单位是字节 */
	public static float toMbSize(float fileSize) {
		if (fileSize >= 0) {
			float midValue = fileSize / 1024;
			return midValue / 1024;
		}
		return -1;
	}

	/**
	 * 兼容两种文件路径格式，以“/”分割的，以“\”分割的 ͨ�����ļ�path�õ�通过给定的文件path得到一个文件文件名称
	 * 例如:/usr/local/tomcat/webapps/demosite/index.jsp 返回:index.jsp
	 */
	public static String getFileName(String file_path) {
		if (file_path == null || file_path.length() == 0) {
			return null;
		}
		String separator = "/";
		int pos = file_path.lastIndexOf(separator);
		if (pos < 0) {
			separator = "\\";
		}
		pos = file_path.lastIndexOf(separator);
		return file_path.substring(pos + 1);
	}

	public static String getExtName(String file_path) {
		if (TypeConverter.sizeLagerZero(file_path)) {
			return file_path.substring(file_path.lastIndexOf(".") + 1,
					file_path.length()).toLowerCase();
		}
		return null;
	}

	/** 图片类型转换成byte[]型方法 */
	public static String getBytes(String filePath) throws IOException {
		String encodedImage = null;
		final File file = new File(filePath);
		if (file.exists()) {
			BufferedInputStream bis = null;
			try {
				bis = new BufferedInputStream(new FileInputStream(file));
				int bytes = (int) file.length();
				byte[] buffer = new byte[bytes];
				int readBytes = bis.read(buffer);
				if (readBytes != buffer.length) {
					throw new IOException("Entire file not read");
				}
				encodedImage = EncryptUtil.encodeBase64(buffer);
			} finally {
				if (bis != null) {
					bis.close();
				}
			}
		}
		return encodedImage;
	}

	public static void main(String[] args) {
		// FileUtil.downloadFile("http://tp2.sinaimg.cn/1712283073/50/1280734786");
	}

}