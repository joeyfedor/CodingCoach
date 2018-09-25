package mandj.appbuildin.codingcoach.innovo;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int sectionNumber;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
            sectionNumber = getArguments().getInt("section_number");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            sectionNumber = getArguments().getInt("section_number");
        }

        View view;
        int layout;
        switch (sectionNumber) {
            case (2):
                layout = R.layout.activity_main;
                view = inflater.inflate(layout, container, false);
                break;
            case (3):
                layout = R.layout.fragment_reference;
                view = inflater.inflate(layout, container, false);
                break;
            case (4):
                Save save = new Save();
                view = save.onCreateView(inflater, container, savedInstanceState);
                TextView textView = (TextView) view.findViewById(R.id.saveTextView);
                textView.setText(save.formatSaves(save.getInternalSave(getActivity())));
                break;
            case (6):
                layout = R.layout.activity_about;
                view = inflater.inflate(layout, container, false);
                PackageInfo packageInfo = null;
                try {
                    packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                TextView txtView = (TextView) view.findViewById(R.id.andVerTextView);
                txtView.setText("Package Name: " + packageInfo.packageName + "\n\n" +
                        "Application Version: " + packageInfo.versionName + "\n\n" +
                        "Version Code: " + packageInfo.versionCode);
                break;
            case (7):
                layout = R.layout.nav_drawer_feedback;
                view = inflater.inflate(layout, container, false);
                Feedback feedback = new Feedback();
                feedback.populateSpinner(view, getActivity());
                break;
            case (8):
                layout = R.layout.pref;
                view = inflater.inflate(layout, container, false);
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
                break;
            default:
                layout = R.layout.coming_soon;
                view = inflater.inflate(layout, container, false);
        }

        // Inflate the layout for this fragment

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

        if (getArguments() != null)
            sectionNumber = getArguments().getInt("section_number");

        ((MainActivity) activity).onSectionAttached(
                sectionNumber - 1);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        public void onFragmentInteraction(Uri uri);
    }

}
