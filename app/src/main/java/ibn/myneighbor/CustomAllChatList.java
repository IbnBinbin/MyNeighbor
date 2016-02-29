package ibn.myneighbor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import android.util.Log;

/**
 * Created by ttnok on 22/2/2559.
 */
public class CustomAllChatList extends ArrayAdapter<String>{
    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<Integer> imgid;
    private final ArrayList<String> owner;
    private final ArrayList<Integer> offerOrReq;
    private final boolean isAll;

    public CustomAllChatList(Activity context, ArrayList<String> itemname, ArrayList<Integer> imgid, ArrayList<String> owner, ArrayList<Integer> offerOrReq, boolean isAll) {
        super(context, R.layout.item_list, itemname);

        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
        this.owner = owner;
        this.offerOrReq=offerOrReq;
        this.isAll=isAll;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.all_chat_list, null, true);

        TextView owner = (TextView) rowView.findViewById(R.id.owner);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.profilePic);

        if(isAll) {
            owner.setText("Conversation between you and "+this.owner.get(position) + " about");
        }else{
            owner.setText(this.owner.get(position) + " said");
        }
        txtTitle.setText(itemname.get(position));
//        Log.d("Ibn", imgid.get(position)+" :imgid");
        imageView.setImageResource(imgid.get(position));

        ArrayList<String> tag = new ArrayList<String>();
        tag.add(this.owner.get(position));
        tag.add(itemname.get(position));
        tag.add(imgid.get(position).toString());

        rowView.setTag(tag);

        return rowView;

    }

}
