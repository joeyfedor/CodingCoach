package mandj.appbuildin.codingcoach.innovo.Learn;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import mandj.appbuildin.codingcoach.innovo.Feedback;
import mandj.appbuildin.codingcoach.innovo.R;
import mandj.appbuildin.codingcoach.innovo.Study.TabbedListView.ListContent;
import mandj.appbuildin.codingcoach.innovo.Vibrate;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link //Callbacks}
 * interface.
 */
public class LearnFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListContent listContent = new ListContent();
    Context myContext;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LearnFragment() {
    }

    // TODO: Rename and change types of parameters
    public static LearnFragment newInstance(String param1, String param2) {
        LearnFragment fragment = new LearnFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Map<String, String>> listStuff;
        int sectionNumber = 0;


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            sectionNumber = getArguments().getInt("section_number");
        }

        switch (sectionNumber) {
            case 1:
                listStuff = listContent.getSectionOneList(getActivity());
                break;
            case 2:
                listStuff = listContent.getSectionTwoList();
                break;
            case 3:
                listStuff = listContent.getSectionThreeList();
                break;
            default:
                listStuff = listContent.getSectionOneList(getActivity());
                break;
        }

        myContext = getActivity();

        setListAdapter(new SimpleAdapter(getActivity(), listStuff,
                android.R.layout.simple_list_item_2, new String[]{"First Line", "Second Line"},
                new int[]{android.R.id.text1, android.R.id.text2}));
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_feedback) {
            Intent intent = new Intent(getActivity(), Feedback.class);
            intent.putExtra("callingActivity", "Learn");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Vibrate vibrate = new Vibrate();
        vibrate.VibrateOne(20, myContext);
        Resources res = getResources();

        if (position < res.getStringArray(R.array.topics).length) {

            Intent intent = new Intent(v.getContext(), LearnRunner.class);
            intent.putExtra("position", position);
            startActivity(intent);
        } else {
            Toast.makeText(myContext, "This topic is locked, try another one", Toast.LENGTH_SHORT)
                    .show();
        }


        //if (null != mListener) {
        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        //    mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        //}
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
