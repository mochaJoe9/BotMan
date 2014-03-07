package main;

import java.util.concurrent.TimeUnit;

import org.jivesoftware.smack.Chat;

import chatHandler.ChatConnector;

public class ChatBotMain 
{
	private static final String USERNAME = "bobthebot";
	private static final String PASSWORD = "pink87cougar";
	
	public static void main(String[] args) throws InterruptedException
	{
		
		ChatConnector cc = new ChatConnector(USERNAME, PASSWORD);
		
		Chat chat1 = cc.createNewChat("alicethebot@ryan-merewethers-macbook.local");
		cc.sendMessage(chat1, "sup");
		
		while(true)
		{
			TimeUnit.SECONDS.sleep(10);
		}
		
	}
}
