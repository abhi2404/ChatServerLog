package com.chatlogserver.chatlogserver;

import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
public class ChatLogServerController {

    private static Map<String, List<Message>> chatLogs = new HashMap<>();
    Comparator<Message> comparator = ((o1, o2) -> Long.compare(o2.getTimestamp(), o1.getTimestamp()));



    @PostMapping("/chatlogs/{user}")
    public String createChatLogEntry(@PathVariable String user, @RequestBody Message message) {
        List<Message> chatLog = chatLogs.getOrDefault(user, new ArrayList<>());
        chatLog.add(message);
        Collections.sort(chatLog, new Comparator<Message>() {
            @Override
            public int compare(Message m1, Message m2) {
                return Long.compare(m2.getTimestamp(), m1.getTimestamp());
            }
        });
        chatLogs.put(user, chatLog);
        return message.getMessageId();
    }

    @GetMapping("/chatlogs/{user}")
    public List<Message> getChatLogs(@PathVariable String user, @RequestParam(defaultValue = "10") int limit, @RequestParam(required = false) String start) {
        List<Message> chatLog = chatLogs.getOrDefault(user, new ArrayList<>());
        if (start != null) {
            int index = chatLog.indexOf(new Message(start));
            if (index >= 0) {
                chatLog = chatLog.subList(index, chatLog.size());
            } else {
                chatLog = Collections.emptyList();
            }
        }
        return chatLog.size() > limit ? chatLog.subList(0, limit) : chatLog;
    }

    @DeleteMapping("/chatlogs/{user}")
    public void deleteChatLogs(@PathVariable String user) {
        chatLogs.remove(user);
    }

    @DeleteMapping("/chatlogs/{user}/{messageId}")
    public String deleteChatLog(@PathVariable String user, @PathVariable String messageId) {
        List<Message> chatLog = chatLogs.get(user);
        if (chatLog != null) {
            if(chatLog.indexOf(new Message(messageId)) !=-1){
            chatLog.remove(new Message(messageId));
                return "Message Deleted Successfully";
            }
        }
        return ("No message with id " + messageId);
    }
  
}
