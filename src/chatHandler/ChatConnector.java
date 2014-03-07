package chatHandler;

import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

public class ChatConnector 
{
	private static final String SERVER = "ryan-merewethers-macbook.local";
	private String USERNAME;
	private String PASSWORD;
	
	private XMPPConnection connection;
	private PacketListener packetListener;
	
	/**
	 * Constructor
	 * Connects to the openfire server to enable chat
	 */
	public ChatConnector(String username, String password)
	{
		//set the input parameters to class variables.
		this.USERNAME = username;
		this.PASSWORD = password;
		
		// create a new configuration for the server that is being connected to
		ConnectionConfiguration config = new ConnectionConfiguration(SERVER, 5222);
		
		// create a new connection to the server
		connection = new XMPPConnection(config);
		
		// try to connect to the server
		try
		{
			//connect to the server
			connection.connect();
			
			System.out.println("Connection successful to " + connection.getHost());
		}
		catch (XMPPException e)
		{
			System.out.println("Failed to connect to " + connection.getHost());
			System.exit(1);
		}
		
		// try to login to the server
		try
		{
			// login to the server
			connection.login(USERNAME, PASSWORD);
			System.out.println("successful login for " + connection.getUser());
			
			// create a new available presence for the user
			Presence presence = new Presence(Presence.Type.available);
			
			//send the user's presence to the server
			connection.sendPacket(presence);
			
		}
		catch (XMPPException e)
		{
			System.out.println("unable to login user " + connection.getUser());
			System.exit(1);
		}
		
		//filter what type of messages will be accepted
		PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
		//add a packet listener to recieve incoming messages
		connection.addPacketListener(new BotPacketListener(), filter);
	}
	
	//$&$&$&&&&&&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$

	/**
	 * This function gets the users that this user is buddies with
	 * @return the list of buddies
	 */
	public Collection<RosterEntry> getBuddyList()
	{
		//get the user's roster
		Roster roster = connection.getRoster();
		
		//get the names of each user on the roster
		Collection<RosterEntry> buddyList = roster.getEntries();
		
		return buddyList;
	}
	
	//$&$&$&&&&&&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$

	/**
	 * This function creates a new chat connection with a user
	 * @param user The user you will chat with
	 * @return the chat between you and a user
	 */
	public Chat createNewChat(String user)
	{
		// add a new chat manager to the connection
		ChatManager chatManager = connection.getChatManager();
				
		//create a new chat with the inteded user
		Chat chat = chatManager.createChat(user, new BotMessageListener());
		
		return chat;
	}
	
	//$&$&$&&&&&&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$
	/**
	 * This function sends a message to another xmpp user
	 * @param chat The chat between the two users
	 * @param message The message that will be sent to the user.
	 */
	public void sendMessage(Chat chat, String message)
	{
		//attempt to send the message
		try 
		{
			//send the message to the intended user
			chat.sendMessage(message);
		} 
		catch (XMPPException e) 
		{
			System.out.println("message not sent, try again");
		}
	}
	
	//$&$&$&&&&&&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$&$

	class BotPacketListener implements PacketListener 
	{

		@Override
		public void processPacket(Packet packet) 
		{
			Message message = (Message) packet;
		    String body = message.getBody();
		    String from = message.getFrom();
		    System.out.println(body);
		}

	}
}
