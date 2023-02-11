package gov.iti.jets.client.callBack;


import gov.iti.jets.client.Dina.ContactList;
import gov.iti.jets.client.Dina.MessagesQueue;
import gov.iti.jets.client.Util.ConnectionFlag;
import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.MessageDto;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.network.client.IClient;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Martinily extends UnicastRemoteObject implements IClient {
    public Martinily() throws RemoteException {
    }

    @Override
    public void receive() throws RemoteException {
        ConnectionFlag.getInstance().connectedFlag = true;
    }

    @Override
    public void receiveMessage(long chatId, String senderId, String message) throws RemoteException {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(message);
        messageDto.setSenderId(senderId);

        MessagesQueue.getList().put(chatId,messageDto);
    }

    @Override
    public void addToGroup(String userId, long chatId) throws RemoteException {

    }

    @Override
    public void removefromGroup(String userId, long chatId) throws RemoteException {

    }

    @Override
    public void addFriend(ContactDto contactDto) throws RemoteException {
        //Add contact element to contact list
        ContactList.getList().add(contactDto);
    }

    @Override
    public void removeFriend(ContactDto contactDto) throws RemoteException {
        //Get contactDto element and remove it from the list
        ContactDto oldContactDto = ContactList.getList().stream().filter(x -> x.getPhoneNumber() == contactDto.getPhoneNumber()).toList().get(0);
        ContactList.getList().remove(oldContactDto);
    }

    @Override
    public void editUser(ContactDto contactDto) throws RemoteException {
        //Get old contactDto element and remove it from the list
        ContactDto oldContactDto = ContactList.getList().stream().filter(x -> x.getPhoneNumber() == contactDto.getPhoneNumber()).toList().get(0);
        ContactList.getList().remove(oldContactDto);
        //Add new contact element with new user data
        ContactList.getList().add(contactDto);
    }

    @Override
    public void readFile(long chatId, String senderId, byte[] bytes, String fileName) throws RemoteException
    {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            FileChannel channel = FileChannel.open(Paths.get(fileName),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            channel.write(byteBuffer);
        }
         catch (IOException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to send File!!");
        }
    }

}
