package ru.mirea.makarovra.mireaproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FileHandlingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FileHandlingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FileHandlingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FileHandlingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FileHandlingFragment newInstance(String param1, String param2) {
        FileHandlingFragment fragment = new FileHandlingFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_handling, container, false);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        EditText fileName = view.findViewById(R.id.editTextText1);
        EditText text = view.findViewById(R.id.editTextText2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileExtensionDialog(String.valueOf(fileName.getText()),String.valueOf(text.getText()));
            }
        });

        return view;
    }

    private void showFileExtensionDialog(String fileName, String text) {
        final String[] extensions = new String[]{"txt", "pdf", "docx"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите расширение файла")
                .setItems(extensions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int extension) {
                        try {
                            FileOutputStream outputStream;
                            outputStream = getActivity().openFileOutput(fileName + "." + extensions[extension], Context.MODE_PRIVATE);
                            outputStream.write(text.getBytes());
                            outputStream.close();
                        } catch (IOException e) {
                            System.out.println("error");
                            e.printStackTrace();
                        }
                    }
                });
        builder.create().show();
    }
}