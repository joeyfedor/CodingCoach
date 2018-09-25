package mandj.appbuildin.codingcoach.innovo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Monica on 8/2/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    OnItemClickListener mItemClickListener;
    private String[] mDataset;
    private String[] mDatasetTwo;
    private String[] mDatasetThree;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset, String[] myDatasetTwo) {
        mDataset = myDataset;
        mDatasetTwo = myDatasetTwo;
    }

    public MyAdapter(String[] myDataset, String[] myDatasetTwo, String[] myDatasetThree) {
        mDataset = myDataset;
        mDatasetTwo = myDatasetTwo;
        mDatasetThree = myDatasetThree;
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v;
        if (mDatasetThree == null) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ref_list_item, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ref_list_item_two, parent, false);
        }
        // set the view's size, margins, paddings and layout parameters
        //...
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);
        holder.txtViewTitle.setText(mDataset[position]);
        holder.txtViewDescription.setText(mDatasetTwo[position]);
        if (mDatasetThree != null)
            holder.txtViewReturnType.setText(mDatasetThree[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case

        public TextView txtViewTitle;
        public TextView txtViewDescription;
        public TextView txtViewReturnType;

        public ViewHolder(View v) {
            super(v);
            txtViewTitle = (TextView) v.findViewById(R.id.text1);
            txtViewDescription = (TextView) v.findViewById(R.id.text2);
            txtViewReturnType = (TextView) v.findViewById(R.id.text3);
            v.setClickable(true);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }
}