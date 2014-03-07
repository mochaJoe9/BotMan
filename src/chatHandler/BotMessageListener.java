package chatHandler;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class BotMessageListener implements MessageListener
{
	@Override
	public void processMessage(Chat chat, Message message)
	{
		
		System.out.println("message recieved " + message.getBody());
	}
}
