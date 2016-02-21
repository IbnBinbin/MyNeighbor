package ibn.myneighbor.Model;

import java.util.Date;

/**
 * Created by ttnok on 21/2/2559.
 */
public class Activity {
    private int id;
    private String title;
    private String description;
    private int request_or_offer;
    private String group_name;
    private Date expire_date;
    private Date created_date;
    private Date updated_date;
    private String owner;

    public Activity (String title, String desc, int req_offer, String groupName, Date expireDate, String owner){
        this.title=title;
        this.description=desc;
        this.request_or_offer=req_offer;
        this.group_name=groupName;
        this.expire_date=expireDate;
        this.created_date=new Date();
        this.owner=owner;
    }

    public int getID(){return id;}
    public String getTitle(){return title;}
    public String getDescription(){return description;}
    public int getRequestOrOffer(){return request_or_offer;}
    public String getGroupName(){return group_name;}
    public Date getexpireDate(){return expire_date;}
    public String getOwner(){return owner;}

    public void setID(int id){this.id= id;}
    public void setUpdated(Date date){this.updated_date= date;}
}
