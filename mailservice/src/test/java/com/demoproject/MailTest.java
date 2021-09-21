package com.demoproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SpringBootTest(classes = MailserviceApplication.class)
public class MailTest extends AbstractTestNGSpringContextTests {

	@Test
	public void sample() throws MessagingException {
		System.out.println("Test");

		System.setProperty("webdriver.chrome.driver", "D:\\chrome\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.navigate().to("http://localhost:8080/index");

		driver.manage().window().maximize();

		Map<String, String> senderInfoMap = new HashMap<>();
		String mailBody = null;
		mailBody = this.readmailBodyFile();

		senderInfoMap = this.readSenderFile();
		List<String> receiverEmailList = this.readReceiverFile();
		int senderCount = 0;
		int receiverCount = 0;
		String subject = null;

		for (Map.Entry mapElement : senderInfoMap.entrySet()) {
			receiverCount = 0;
			senderCount = senderCount + 1;

			String userIdInput = (String) mapElement.getKey();
			String passwordInput = (String) mapElement.getValue();

			WebElement userName = driver.findElement(By.id("fname"));
			WebElement password = driver.findElement(By.id("lname"));

			userName.sendKeys(userIdInput);
			password.sendKeys(passwordInput);

			driver.findElement(By.id("login")).click();

			for (String receiverId : receiverEmailList) {
				receiverCount = receiverCount + 1;
				WebElement fromId = driver.findElement(By.id("fromId"));
				WebElement toId = driver.findElement(By.id("toId"));
				WebElement subjectElement = driver.findElement(By.id("subject"));
				WebElement mailBodyContent = driver.findElement(By.id("mailBody"));
				WebElement fileInput = driver.findElement(By.name("myfile"));
				fileInput.sendKeys("D:\\Mail Info\\attachment.txt");

				fromId.sendKeys(userIdInput);
				toId.sendKeys(receiverId);
				mailBodyContent.sendKeys(mailBody);

				System.out.println("From Id" + userIdInput);
				System.out.println("To Id" + receiverId);
				System.out.println("mail sent");

				subject = senderCount + " " + "John Doe" + " " + receiverCount;

				subjectElement.sendKeys(subject);
				this.writeFile(subject);
				driver.findElement(By.id("fromId")).clear();
				driver.findElement(By.id("toId")).clear();
				driver.findElement(By.id("subject")).clear();
				driver.findElement(By.id("mailBody")).clear();
				driver.findElement(By.id("myfile")).clear();

				try {
					this.sendEmail(receiverId, userIdInput, subject, mailBody);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Subject" + subject);
				System.out.println("mailBody" + mailBody);

			}
			driver.findElement(By.id("logout")).click();

		}

	}

	public List<String> readReceiverFile() {
		List<String> receiverInfoList = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("D:\\Mail Info\\receiver.txt"));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				receiverInfoList.add(values[0].trim());
			}

			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return receiverInfoList;

	}
	
	public Map<String, String> readSenderFile() {
		Map<String, String> senderInfoMap = new HashMap<>();
		try {

			String line = null;
			BufferedReader br = new BufferedReader(new FileReader("D:\\Mail Info\\sender.txt"));
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				senderInfoMap.put(values[0], values[1]);
			}

			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return senderInfoMap;
	}

	public List<String> readAttachementFile() {
		List<String> receiverInfoList = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("D:\\Mail Info\\attachment.txt"));
			String line = null;
			while ((line = br.readLine()) != null) {
				receiverInfoList.add(line.trim());
			}

			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return receiverInfoList;

	}



	public String readmailBodyFile() {
		List<String> mailBody = new ArrayList<>();
		String mailContent = "";
		try {

			String line = null;
			BufferedReader br = new BufferedReader(new FileReader("D:\\Mail Info\\mailBody.txt"));
			while ((line = br.readLine()) != null) {
				mailBody.add(line);
			}

			br.close();

			for (String body : mailBody) {
				mailContent = mailContent.concat(body);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mailContent;

	}

	public void writeFile(String content) {

		PrintWriter writer;
		try {
			writer = new PrintWriter("D:\\Mail Info\\attachment.txt");
			writer.print("");
			writer.close();

			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter("D:\\Mail Info\\attachment.txt");
				BufferedWriter bw = new BufferedWriter(fileWriter);

				bw.write(content);
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendEmail(String to, String from, String subject, String bodyText)
			throws MessagingException, IOException {
		File file = new File("D:\\Mail Info\\attachment.txt");

		sendMessage(to, from, subject, bodyText, file);

	}
	public void sendMessage(String to, String from, String subject, String bodyText, File file)
			throws MessagingException, IOException {

		//Add yoy gmail Email Id and Password Here
		final String username = "youremailid@gmail.com";
        final String password = "password";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        
        
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject(subject);           
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(bodyText, "text/plain");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            mimeBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);

            mimeBodyPart.setDataHandler(new DataHandler(source));
            mimeBodyPart.setFileName(file.getName());

            multipart.addBodyPart(mimeBodyPart);
            
            
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
		
	}

}
