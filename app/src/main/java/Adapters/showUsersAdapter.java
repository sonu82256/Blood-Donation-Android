package Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

//import com.bappy.austblooddonationapp.R;

import com.example.bloodapp.R;

import java.util.List;

import DataModels.userProfileData;

public class showUsersAdapter extends ArrayAdapter<userProfileData> {

    private Activity context;
    private List<userProfileData> usersList;

    public showUsersAdapter(Activity context, List<userProfileData> usersList) {
        super(context, R.layout.show_request_layout, usersList);
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.show_request_layout,null, true);

        userProfileData userProfileData = usersList.get(position);

        TextView bloodGroup = view.findViewById(R.id.bloodGroupText);
        TextView name = view.findViewById(R.id.nameText);
        TextView address = view.findViewById(R.id.addressText);
        TextView views = view.findViewById(R.id.viewsCount);
        ImageView eyeView = view.findViewById(R.id.eyeView);

        views.setVisibility(View.GONE);
        eyeView.setVisibility(View.GONE);


        name.setText("Name: "+ userProfileData.getName());
        bloodGroup.setText("Blood Group: "+ userProfileData.getBloodGroup());
        address.setText("Address: "+ userProfileData.getDivison()+" ("+ userProfileData.getDistrict()+")");


        return view;
    }

}
