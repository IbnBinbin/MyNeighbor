package ibn.myneighbor.Model;

import java.util.Date;

/**
 * Created by ttnok on 21/2/2559.
 */
public class User {
    private  int id;
    private String username;
    private String bio;
    private int profile_pic_id;
    private String password;
    private Date created_date;
    private Date updated_date;

    public User (String username, String bio, int profilePic, String pass){
        this.username=username;
        this.bio=bio;
        this.profile_pic_id=profilePic;
        this.password=pass;
        this.created_date=new Date();
    }

    public int getID(){return id;}
    public String getUsername(){return username;}
    public String getBio(){return bio;}
    public int getProfilePic(){return profile_pic_id;}
    public String getPassword(){return password;}
    public Date getCreatedDate(){return created_date;}
    public Date getUpdatedDate(){return updated_date;}

    public void setID(int id){this.id= id;}
    public void setUpdated(Date date){this.updated_date= date;}

}
