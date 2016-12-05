package ua.kiev.prog;

import java.util.ArrayList;
import java.util.List;

public class JsonMessages {
    private final List<Message> list;

    public JsonMessages(List<Message> sourceList, int fromIndex, String to, String room) {
        this.list = new ArrayList<>();
        for (int i = fromIndex; i < sourceList.size(); i++){
            Message message = sourceList.get(i);
            if (message != null) {
                if(to.equals(message.getTo())||
                        ("all".equals(message.getTo())&&("main".equals(message.getRoom())||room.equals(message.getRoom()))))
                    list.add(message);
            }
        }
    }
}