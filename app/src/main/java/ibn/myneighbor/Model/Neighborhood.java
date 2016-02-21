package ibn.myneighbor.Model;

/**
 * Created by ttnok on 21/2/2559.
 */
public class Neighborhood {
    private  int id;
    private String initial_point;
    private String final_point;
    private int draw_type;
    private String owner;

    public Neighborhood(String init, String finalP, int type, String owner){
        this.initial_point=init;
        this.final_point=finalP;
        this.draw_type=type;
        this.owner=owner;
    }

    public int getID(){return id;}
    public String getInitialPoint(){return initial_point;}
    public String getFinalPoint(){return final_point;}
    public int getDrawType(){return draw_type;}
    public String getOwner(){return owner;}

    public void setID(int id){this.id= id;}
}
