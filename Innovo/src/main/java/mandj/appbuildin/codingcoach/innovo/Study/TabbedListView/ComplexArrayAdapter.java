package mandj.appbuildin.codingcoach.innovo.Study.TabbedListView;

/**
 * Created by Monica on 8/17/2014.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mandj.appbuildin.codingcoach.innovo.MainActivity;
import mandj.appbuildin.codingcoach.innovo.R;

public class ComplexArrayAdapter extends ArrayAdapter<RowItem> {

    Context context;
    MainActivity mainActivity = new MainActivity();
    ArrayList<String> correctPuzzles;


    public ComplexArrayAdapter(Context context, int resourceId,
                               List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;

        correctPuzzles = mainActivity.getNamesCorrect(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.complex_list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.textView2);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.textView);
            holder.checkBox = (ImageView) convertView.findViewById(R.id.checkBox);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView3);
            holder.txtLevel = (TextView) convertView.findViewById(R.id.levelText);
            holder.outline = (ImageView) convertView.findViewById(R.id.outLine);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtDesc.setText(rowItem.getDesc());
        holder.txtTitle.setText(rowItem.getTitle());

        if (rowItem.getDone() == 0) {
            holder.checkBox.setVisibility(View.INVISIBLE);
            holder.outline.setVisibility(View.VISIBLE);
        } else if (rowItem.getDone() == 1) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.outline.setVisibility(View.INVISIBLE);
        } else {
            holder.checkBox.setVisibility(View.INVISIBLE);
            holder.outline.setVisibility(View.INVISIBLE);
        }

    holder.imageView.setImageResource(rowItem.getImageId());
    if(rowItem.getImageId()==R.drawable.levelone)
            holder.txtLevel.setText("Level 1");
    else
            holder.txtLevel.setText("Level 2");

    return convertView;
}

/*private view holder class*/
private class ViewHolder {
    ImageView imageView;
    TextView txtTitle;
    TextView txtDesc;
    ImageView checkBox;
    TextView txtLevel;
    ImageView outline;
}
}
