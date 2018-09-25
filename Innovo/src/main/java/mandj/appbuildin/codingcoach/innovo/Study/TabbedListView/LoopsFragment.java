package mandj.appbuildin.codingcoach.innovo.Study.TabbedListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import mandj.appbuildin.codingcoach.innovo.R;
import mandj.appbuildin.codingcoach.innovo.Study.Runner.PuzzleRunner;
import mandj.appbuildin.codingcoach.innovo.Vibrate;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link //Callbacks}
 * interface.
 */
public class LoopsFragment extends ListFragment {

    static final ListContent stuff = new ListContent();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<RowItem> rowItems;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    Context myContext;
    int puzzleType = 3;
    int[] puzzle;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public LoopsFragment() {
    }

    // TODO: Rename and change types of parameters
    public static LoopsFragment newInstance(String param1, String param2) {
        LoopsFragment fragment = new LoopsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        stuff.setupLoops(getActivity().getApplicationContext());
        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < stuff.getLoopsDifficulty().length; i++) {
            RowItem item = new RowItem(stuff.getLoopsDifficulty()[i], stuff.getLoopsTitles()[i], stuff.getLoopsDescripts()[i], stuff.getLoopsDone()[i]);
            rowItems.add(item);
        }

        myContext = getActivity();

        setListAdapter(new ComplexArrayAdapter(getActivity(), R.layout.complex_list_item, rowItems));
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String[] names = myContext.getResources().getStringArray(R.array.method_header_loops);
        if (position < names.length) { //if position less than number of puzzles
            Vibrate vibrate = new Vibrate();
            vibrate.VibrateOne(20, myContext);
            Intent intent = new Intent(v.getContext(), PuzzleRunner.class);
            intent.putExtra("array", puzzle = new int[]{position, puzzleType});
            intent.putExtra("difficulty", stuff.getLoopsDifficulty()[position]);
            intent.putExtra("newPuzzle?", true);
            startActivity(intent);
        } else {
            Toast.makeText(myContext, "This puzzle is locked, try another one", Toast.LENGTH_SHORT)
                    .show();

        }

        /*if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }*/
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
