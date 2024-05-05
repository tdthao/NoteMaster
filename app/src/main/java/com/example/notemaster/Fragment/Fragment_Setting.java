package com.example.notemaster.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.notemaster.R;

public class Fragment_Setting extends Fragment {
    public Fragment_Setting() {
        // Required empty public constructor
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    // khi cần load lại dlieu của activity này nó sẽ vào đây
    @Override
    public void onResume() {
        super.onResume();
    }

    // hàm load lại dlieu khi cần và đc gọi đến
    public void reloadData() {
        Toast.makeText(getActivity(), "Reload fragment ST", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
}
