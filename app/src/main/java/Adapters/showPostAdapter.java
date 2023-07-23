package Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

//import com.bappy.austblooddonationapp.R;

import com.example.bloodapp.R;

import java.util.List;

import DataModels.bloodPostData;

public class showPostAdapter extends ArrayAdapter<bloodPostData> {

    private Activity context;
    private List<bloodPostData> postList;

    public showPostAdapter(Activity context, List<bloodPostData> postList) {
        super(context, R.layout.show_request_layout, postList);
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.show_request_layout,null, true);

        bloodPostData bloodPostData = postList.get(position);

        TextView bloodGroup = view.findViewById(R.id.bloodGroupText);
        TextView name = view.findViewById(R.id.nameText);
        TextView address = view.findViewById(R.id.addressText);
        TextView views = view.findViewById(R.id.viewsCount);


        name.setText("Name: "+bloodPostData.getName());
        bloodGroup.setText("Blood Group: "+bloodPostData.getBloodGroup());
        address.setText("Address: "+bloodPostData.getAddress());
        views.setText(String.valueOf(bloodPostData.getViews()));

        return view;
    }
}

