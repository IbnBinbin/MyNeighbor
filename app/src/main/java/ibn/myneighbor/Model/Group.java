package ibn.myneighbor.Model;

/**
 * Created by ttnok on 21/2/2559.
 */
public class Group {
    private  int id;
    private String owner;
    private String group_name;
    private String member;

    public Group (int id, String owner, String group_name, String member){
        this.id=id;
        this.owner=owner;
        this.group_name=group_name;
        this.member=member;
    }

    public int getID(){return id;}
    public String getOwner(){return owner;}
    public String getGroupName(){return group_name;}
    public String getMember(){return member;}

}
