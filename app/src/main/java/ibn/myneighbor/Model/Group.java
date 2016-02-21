package ibn.myneighbor.Model;

/**
 * Created by ttnok on 21/2/2559.
 */
public class Group {
    private  int id;
    private String username;
    private String group_name;
    private String member;

    public Group (String username, String group_name, String member){
        this.username=username;
        this.group_name=group_name;
        this.member=member;
    }

    public String getUsername(){return username;}
    public String getGroupName(){return group_name;}
    public String getMember(){return member;}

    public void setID(int id){this.id= id;}
}
