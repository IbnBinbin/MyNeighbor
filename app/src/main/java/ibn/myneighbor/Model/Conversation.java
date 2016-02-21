package ibn.myneighbor.Model;

import java.util.Date;

/**
 * Created by ttnok on 21/2/2559.
 */
public class Conversation {
    private int id;
    private String topic;
    private String owner;
    private String client;
    private Date created_date;
    private String chat_message;

    public Conversation (String topic, String owner, String client, String chat){
        this.topic=topic;
        this.owner=owner;
        this.client=client;
        this.created_date =new Date();
        this.chat_message=chat;
    }

    public int getID(){return id;}
    public String getTopic(){return topic;}
    public String getOwner(){return owner;}
    public String getClient(){return client;}
    public Date getcreatedDate(){return created_date;}
    public String getChatMessage(){return chat_message;}

    public void setID(int id){this.id= id;}

}
