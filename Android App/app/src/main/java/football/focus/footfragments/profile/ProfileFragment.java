package football.focus.footfragments.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import football.focus.footfragments.R;
import football.focus.footfragments.Util.SaveData;


public class ProfileFragment extends Fragment {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    TextView emailView;
    TextView createdView;
    TextView subTypeView;
    TextView subEndView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_profile, null);

        emailView = fragmentView.findViewById(R.id.email);
        createdView = fragmentView.findViewById(R.id.created);
        subTypeView = fragmentView.findViewById(R.id.subType);
        subEndView = fragmentView.findViewById(R.id.subEnd);

        loadData();

        return fragmentView;
    }

    private void loadData()
    {
        SaveData sd = new SaveData();
        sd.read("user", getActivity().getApplicationContext());
        try {
            JSONObject jsonobject = new JSONObject(sd.content);

                String email = jsonobject.getString("email");
                String created = jsonobject.getString("created");
                String subType = jsonobject.getString("subtype");
                String subEnd = jsonobject.getString("subend");

                if (subEnd.equals("0000-00-00 00:00:00"))
                {
                    subEnd = "-                ";
                }

                emailView.setText(email);
                createdView.setText(created.substring(0,10));
                subTypeView.setText(subType);
                subEndView.setText(subEnd.substring(0,10));

        } catch (JSONException ex) {
            Log.e("App", "Failure", ex);
        }
    }

}
