package com.ava.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.ava.resource.GlobalConfig;
import com.sun.mail.smtp.SMTPSendFailedException;
import com.sun.mail.smtp.SMTPTransport;

/**
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public final class MailUtil implements Runnable {

	/** 邮件主机 */
	private String mailhost = null;

	/** 用户名 */
	private String fromuser = null;

	/** 密码 */
	private String password = null;

	/** 邮件中显示的邮箱名称 */
	private String fromuser_view = null;

	/** 目标邮箱 */
	private String touser = null;

	/** 目标邮箱集合，群发时使用 */
	private List<String> tousers = null;

	/** 邮件标题 */
	private String subject = null;

	/** 邮件内容 */
	private String body = null;

	/** 抄送人邮箱 */
	private String ccuser = null;

	/** 暗送人邮箱 */
	private String bccuser = null;

	/** 回复邮箱 */
	private String replyto = null;

	/** 附件列表 */
	private List<Object> attachList = new ArrayList<Object>();

	public MailUtil() {
		this(null, null, null);
	}

	public MailUtil(String mailhost, String fromuser, String password) {
		this.setMailhost(mailhost);
		this.setFromuser(fromuser);
		this.setPassword(password);
		if (this.mailhost == null || this.fromuser == null
				|| this.password == null) {
			init();
		}
	}

	public void init() {
		if (this.mailhost == null || this.fromuser == null
				|| this.password == null) {
			this.setMailhost(GlobalConfig.getMail_mailhost());
			this.setFromuser(GlobalConfig.getMail_fromuser());
			this.setPassword(GlobalConfig.getMail_password());
		}
	}

	/**
	 * 此方法实现了邮件群发方法，同时每发送一封邮件，间隔5秒 调用示例：
	 * MailUtil mail = new MailUtil(); 
	 * List<String> tousers = new ArrayList<String>(); 
	 * tousers.add("wj@kingsh.com");
	 * tousers.add("service@kingsh.com");
	 * tousers.add("market@kingsh.com");
	 * mail.setTousers(tousers); 
	 * mail.setSubject("邮件标题"); 
	 * mail.setBody("邮件内容");
	 * new Thread(mail).start();
	 */
	public void run() {
		bacthSend(this);
	}

	public void bacthSend(MailUtil mailUtil) {
		System.out.println("=================> " + DateTime.getNormalDateTime() + " Begin batch Send...");
		if (mailUtil.tousers != null && mailUtil.tousers.size() > 0) {
			for (String touser : mailUtil.tousers) {
				mailUtil.setTouser(touser);
				mailUtil.send();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("=================> " + DateTime.getNormalDateTime() + " End batch Send...");
		System.out.println("tousers=" + mailUtil.tousers);
	}

	/**
	 * 调用示例： mail.send("wj@kingsh.com,test@126.com", "中文标题", "邮件内容");
	 * 其中邮件内容默认支持html标签，以html格式发送
	 */
	public int send(String touser, String subject, String body) {
		this.setTouser(touser);
		this.setSubject(subject);
		this.setBody(body);
		return send();
	}

	public int send() {
		return send("html");
	}

	public int send(String isText) {
		try {
			Properties props = new Properties();
			if (this.mailhost != null) {
				props.put("mail.smtp.host", mailhost);
			}
			props.put("mail.smtp.auth", "true");
			Session sendMailSession = Session.getInstance(props);
			//sendMailSession.setDebug(true);

			// 发email
			MimeMessage newMessage = new MimeMessage(sendMailSession);
			// 设置发件人
			if (fromuser_view != null) {
				newMessage.setFrom(new InternetAddress(MimeUtility.encodeText(
						fromuser_view, "UTF-8", "B")
						+ "<" + fromuser + ">"));
			} else {
				newMessage.setFrom(new InternetAddress(fromuser));
			}
			// 设置目标邮箱
			newMessage.setRecipients(Message.RecipientType.TO,
					javax.mail.internet.InternetAddress.parse(touser));
			// 设置抄送人
			if (ccuser != null) {
				newMessage.setRecipients(Message.RecipientType.CC,
						javax.mail.internet.InternetAddress.parse(ccuser));
			}
			// 设置暗送人
			if (bccuser != null) {
				newMessage.setRecipients(Message.RecipientType.BCC,
						javax.mail.internet.InternetAddress.parse(bccuser));
			}
			// 设置回复邮箱
			if (replyto != null) {
				newMessage.setReplyTo((Address[]) InternetAddress
						.parse(replyto));
			}
			// 设置标题
			newMessage
					.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
			// 设置发送时间
			newMessage.setSentDate(new Date());

			if ("text".equals(isText)) {
				newMessage.setText(body);
			} else {
				// 给消息对象设置内容，用来发送HTML格式邮件用，纯文本格式可直接用.sexText方法
				BodyPart mbp = new MimeBodyPart(); // 新建一个存放信件内容的BodyPart对象
				mbp.setContent(body, "text/html;charset=utf-8"); // 给BodyPart对象设置内容和格式/编码方式
				Multipart mp = new MimeMultipart(); // 新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
				mp.addBodyPart(mbp); // 将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
				// 开始添加附件到邮件
				if (attachList != null && attachList.size() > 0) {
					Iterator attachIterator = attachList.iterator();
					while (attachIterator.hasNext()) {
						mbp = new MimeBodyPart();
						FileDataSource fds = new FileDataSource(
								(String) attachIterator.next());
						mbp.setDataHandler(new DataHandler(fds));
						mbp.setFileName(fds.getName());
						mp.addBodyPart(mbp);
					}
				}
				newMessage.setContent(mp); // 把mp作为消息对象的内容
				mp = null; // 回收Multipart对象
				mbp = null; // 回收BodyPart对象
			}

			SMTPTransport t = (SMTPTransport) sendMailSession
					.getTransport("smtp");

			try {
				t.connect(mailhost, fromuser, password);
				t.sendMessage(newMessage, newMessage.getAllRecipients());
			} catch (SMTPSendFailedException e) {
				// 邮件服务器不支持虚拟发件人
				System.out.println("Mail from different with auth identifier");
				try {
					newMessage.setFrom(new InternetAddress(fromuser));
					t.sendMessage(newMessage, newMessage.getAllRecipients());
				} finally {
					t.close();
				}
			} finally {
				t.close();
			}
			return 1;

		} catch (MessagingException m) {
			System.out
					.println("========> MailUtil MessagingException..............");
			m.printStackTrace();
			return -10;
		} catch (Exception e) {
			System.out.println("========> MailUtil Exception..............");
			e.printStackTrace();
			return -20;
		}
	}

	public void setAttachList(List<Object> attachList) {
		this.attachList = attachList;
	}

	/** 给邮件添加附件，可以重复调用 */
	public void addAttach(String filePath) {
		if (attachList == null) {
			attachList = new ArrayList<Object>();
		}
		this.attachList.add(filePath);
	}

	/** 设置暗送人邮箱 */
	public void setBccuser(String bccuser) {
		this.bccuser = bccuser;
	}

	/** 设置邮件内容 */
	public void setBody(String body) {
		this.body = body;
	}

	/** 设置抄送人邮箱 */
	public void setCcuser(String ccuser) {
		this.ccuser = ccuser;
	}

	public void setFromuser(String fromuser) {
		this.fromuser = fromuser;
	}

	/** 设置邮件中显示的发件人邮箱名称 */
	public void setFromuser_view(String fromuser_view) {
		this.fromuser_view = fromuser_view;
	}

	public void setMailhost(String mailhost) {
		this.mailhost = mailhost;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/** 设置回复邮箱 */
	public void setReplyto(String replyto) {
		this.replyto = replyto;
	}

	/** 设置邮件标题 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/** 设置目标邮箱，多个邮箱用英文逗号分隔 */
	public void setTouser(String touser) {
		this.touser = touser;
	}

	/** 给群发邮件目标邮箱，可以重复调用 */
	public void addTouser(String touser) {
		if (tousers == null) {
			tousers = new ArrayList<String>();
		}
		this.tousers.add(touser);
	}

	/** 设置目标邮箱集合，每次循环发送时有时间间隔，适合群发时使用 */
	public void setTousers(List<String> tousers) {
		this.tousers = tousers;
	}

	public static void main(String[] args) {
		try {
			MailUtil mail = new MailUtil();
			String content = "<font color='red'>中文了，还是urf8的呢。。 -_-</font>";
			// mail.addAttach("mylog4j.log");
			mail.setFromuser_view("客服中心");
			// mail.setFromuser_view("www@ddd.com");
			// mail.setBccuser("squall.w@263.net");
			mail.setSubject("中文标题");
			mail.setBody(content);
			mail.addTouser("243062529@qq.com");
			mail.bacthSend(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
