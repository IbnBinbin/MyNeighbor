package ibn.myneighbor;

/**
 * Created by ttnok on 19/2/2559.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;


public class ChatListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;

    public ChatListAdapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.chat_list, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_list, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.profilePic);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);

        ImageButton chatButton = (ImageButton) rowView.findViewById(R.id.chatButton);
        chatButton.setTag(position);

        return rowView;

    }

}
