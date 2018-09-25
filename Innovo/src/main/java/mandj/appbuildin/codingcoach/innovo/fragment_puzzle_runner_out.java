package mandj.appbuildin.codingcoach.innovo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class fragment_puzzle_runner_out extends android.support.v4.app.Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.puzzle_runner_out, container, false);
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(R.id.outMethod)).setText(
                Integer.toString(args.getInt(ARG_OBJECT)));
        return rootView;
    }
}