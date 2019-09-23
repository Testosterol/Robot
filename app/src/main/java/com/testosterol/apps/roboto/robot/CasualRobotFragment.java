package com.testosterol.apps.roboto.robot;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.testosterol.apps.roboto.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CasualRobotFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CasualRobotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CasualRobotFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText xAxis, yAxis, instructions, startX, startY, direction;
    private Button startRobot;

    private OnFragmentInteractionListener mListener;

    public CasualRobotFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CasualRobotFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CasualRobotFragment newInstance(String param1, String param2) {
        CasualRobotFragment fragment = new CasualRobotFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_casual_robot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        xAxis = view.findViewById(R.id.x_axis_edit_text);
        yAxis = view.findViewById(R.id.y_axis_edit_text);
        instructions = view.findViewById(R.id.instructions_edit_text);
        startX = view.findViewById(R.id.x_start);
        startY = view.findViewById(R.id.y_start);
        direction = view.findViewById(R.id.direction);
        startRobot = view.findViewById(R.id.start_button);


        startRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> graphDimens = new ArrayList<>();
                graphDimens.add(Integer.valueOf(xAxis.getText().toString()));
                graphDimens.add(Integer.valueOf(yAxis.getText().toString()));

                ArrayList<Integer> startDimens = new ArrayList<>();
                startDimens.add(Integer.valueOf(startX.getText().toString()));
                startDimens.add(Integer.valueOf(startY.getText().toString()));

                int[] xStart = new int[1];
                int[] yStart = new int[1];

                xStart[0] = Integer.valueOf(startX.getText().toString());
                yStart[0] = Integer.valueOf(startY.getText().toString());

                String facingDirection = direction.getText().toString();
                String[] instructionsArr = instructions.getText().toString().split("");

                for(int i=0; i<instructionsArr.length; i++){
                    switch (instructionsArr[i]){
                        case "R":
                            switch (facingDirection){
                                case "N":
                                    xStart[0]++;
                                    facingDirection = "E";
                                    break;
                                case "S":
                                    xStart[0]--;
                                    facingDirection = "W";
                                    break;
                                case "E":
                                    yStart[0]--;
                                    facingDirection = "S";
                                    break;
                                case "W":
                                    yStart[0]++;
                                    facingDirection = "N";
                                    break;
                            }
                            break;
                        case "L":
                            switch (facingDirection){
                                case "N":
                                    xStart[0]--;
                                    facingDirection = "W";
                                    break;
                                case "S":
                                    xStart[0]++;
                                    facingDirection = "E";
                                    break;
                                case "E":
                                    yStart[0]++;
                                    facingDirection = "N";
                                    break;
                                case "W":
                                    yStart[0]--;
                                    facingDirection = "S";
                                    break;
                            }
                            break;
                        case "F":
                            switch (facingDirection){
                                case "N":
                                    yStart[0]++;
                                    break;
                                case "S":
                                    yStart[0]--;
                                    break;
                                case "E":
                                    xStart[0]++;
                                    break;
                                case "W":
                                    xStart[0]--;
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                }


                Log.d("result","x: " + xStart[0] + " y: " + yStart[0] + " dir: " + facingDirection);

            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
