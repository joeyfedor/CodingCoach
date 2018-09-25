package mandj.appbuildin.codingcoach.innovo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */

public class ReferenceFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int sectionNumber;
    String callingActivity;
    FragmentActivity mActivity;
    RecyclerView mRecyclerView;
    MyAdapter adapter;
    Fragment currentFrag = this;
    private static Activity anActivity;

    private OnFragmentInteractionListener mListener;

    public ReferenceFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (FragmentActivity) activity;
        setRetainInstance(true);
        anActivity = activity;
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        if (getArguments() != null)
            sectionNumber = getArguments().getInt("section_number");
        if (getArguments().getString("callingActivity") != null)
            callingActivity = getArguments().getString("callingActivity");

        if (callingActivity != null) {
            ((MainActivity) activity).onSectionAttached(
                    sectionNumber - 1);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sectionNumber = getArguments().getInt("section_number");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reference, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        if (getArguments().getString("callingActivity") != null) {
            String[] datasetOne = getResources().getStringArray(R.array.packagesOne);
            String[] datasetTwo = getResources().getStringArray(R.array.packagesOneDescripts);
            adapter = new MyAdapter(datasetOne, datasetTwo);
            ((MainActivity)anActivity).updateTitle("Reference");

        } else {
            ((MainActivity)anActivity).updateTitle(getResources().getStringArray(R.array.packagesOne), sectionNumber - 1);
            ReferenceListObject referenceListObject = new ReferenceListObject(sectionNumber, getResources());
            adapter = new MyAdapter(referenceListObject.getMethod(), referenceListObject.getDescription(), referenceListObject.getReturnType());
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.SetOnItemClickListener(new MyAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                if (getArguments().getString("callingActivity") != null) {
                    openFragment(position);
                    //Intent intent = new Intent(getActivity(), Reference.class);
                    //intent.putExtra("number", position);
                    //startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //getFragmentManager().putFragment(outState, "mContent", currentFrag);
        Log.d("Save ReferenceFragment", "saved");
    }

    private void openFragment(int pos) {
        // update the main content by replacing fragments
        //position = pos;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        currentFrag = PlaceholderFragment.newInstance(pos + 1, getActivity());

        fragmentTransaction
                .addToBackStack("ReferenceFragment")
                .setCustomAnimations(R.anim.slideinfromleft, R.anim.slideouttoleft, R.anim.slideinfromright, R.anim.slideouttoright)
                .replace(R.id.container, currentFrag)
                .commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public static Fragment newInstance(int sectionNumber, Context context) {
            //if (sectionNumber == 3) {
            Class<?> clss = ReferenceFragment.class;
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            Fragment fragment = Fragment.instantiate(context, clss.getName(), args);
            fragment.setArguments(args);
            return fragment;
            //}
            /*Class<?> clss = Reference.class;
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            Fragment fragment = Fragment.instantiate(context, clss.getName(), args);
            fragment.setArguments(args);
            return fragment;*/
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_new_main, container, false);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class ReferenceListObject {
        private static int pos;
        private static String[][] methods;
        private static String[][] descriptions;
        private static String[][] returnTypes;

        public ReferenceListObject(int position, Resources resources) {
            pos = position - 1;
            methods = new String[][]{resources.getStringArray(R.array.booleanMethods),
                    resources.getStringArray(R.array.byteMethods), resources.getStringArray(R.array.charMethods),
                    resources.getStringArray(R.array.doubleMethods), resources.getStringArray(R.array.floatMethods),
                    resources.getStringArray(R.array.integerMethods), resources.getStringArray(R.array.mathMethods),
                    resources.getStringArray(R.array.objectMethods), resources.getStringArray(R.array.stringMethods)};
            descriptions = new String[][]{resources.getStringArray(R.array.booleanDescriptions),
                    resources.getStringArray(R.array.byteDescriptions), resources.getStringArray(R.array.charDescriptions),
                    resources.getStringArray(R.array.doubleDescriptions), resources.getStringArray(R.array.floatDescriptions),
                    resources.getStringArray(R.array.integerDescriptions),resources.getStringArray(R.array.mathDescriptions),
                    resources.getStringArray(R.array.objectDescriptions), resources.getStringArray(R.array.stringDescriptions)};
            returnTypes = new String[][]{resources.getStringArray(R.array.booleanReturnTypes),
                    resources.getStringArray(R.array.byteReturnTypes), resources.getStringArray(R.array.charReturnTypes),
                    resources.getStringArray(R.array.doubleReturnTypes), resources.getStringArray(R.array.floatReturnTypes),
                    resources.getStringArray(R.array.integerReturnTypes), resources.getStringArray(R.array.mathReturnTypes),
                    resources.getStringArray(R.array.objectReturnTypes), resources.getStringArray(R.array.stringReturnTypes)};
        }

        public String[] getMethod() {
            return methods[pos];
        }

        public String[] getDescription() {
            return descriptions[pos];
        }

        public String[] getReturnType() {
            return returnTypes[pos];
        }
    }

}
