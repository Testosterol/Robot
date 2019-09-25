package com.testosterol.apps.roboto.robot;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.service.autofill.FieldClassification;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.testosterol.apps.roboto.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    private static final String REGEX = "[lfr]";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText xAxis, yAxis, instructions, startX, startY, direction;
    private TextView result;
    private Button startRobot;
    private LineChart mChart;

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
        result = view.findViewById(R.id.result);
        mChart = view.findViewById(R.id.linechart_casual);


        startRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!xAxis.getText().toString().isEmpty() && !yAxis.getText().toString().isEmpty()
                        && !instructions.getText().toString().isEmpty() && !startX.getText().toString().isEmpty()
                        && !startY.getText().toString().isEmpty() && !direction.getText().toString().isEmpty()) {

                    String facingDirection = direction.getText().toString().toUpperCase();
                    if (facingDirection.matches("[NSWE]")) {

                        int[] xStart = new int[1];
                        int[] yStart = new int[1];

                        xStart[0] = Integer.valueOf(startX.getText().toString());
                        yStart[0] = Integer.valueOf(startY.getText().toString());

                        int xAxisValue = Integer.parseInt(xAxis.getText().toString());

                        ArrayList<String> xVals = new ArrayList<>();
                        ArrayList<Entry> yVals = new ArrayList<Entry>();

                        for(int j = 0; j<=xAxisValue; j++) {
                            xVals.add(String.valueOf(j));
                        }
                        yVals.add(new Entry(xStart[0], yStart[0]));

                        String[] instructionsArr = instructions.getText().toString().toUpperCase().split("");

                        for (int i = 0; i < instructionsArr.length; i++) {
                            switch (instructionsArr[i]) {
                                case "R":
                                    facingDirection = switchCaseDirection("R", facingDirection);
                                    break;
                                case "L":
                                    facingDirection = switchCaseDirection("L", facingDirection);
                                    break;
                                case "F":
                                    switch (facingDirection) {
                                        case "N":
                                            yStart[0]++;
                                            yVals.add(new Entry(xStart[0], yStart[0]));
                                            break;
                                        case "S":
                                            yStart[0]--;
                                            yVals.add(new Entry(xStart[0], yStart[0]));
                                            break;
                                        case "E":
                                            xStart[0]++;
                                            yVals.add(new Entry(xStart[0], yStart[0]));
                                            break;
                                        case "W":
                                            xStart[0]--;
                                            yVals.add(new Entry(xStart[0], yStart[0]));
                                            break;
                                    }
                                    break;
                                default:
                                    if(instructionsArr[i].matches("[^lfr]")) {
                                        Toast.makeText(getActivity(), "Instructions unclear, use R-right, L-left, F-forward only", Toast.LENGTH_LONG).show();
                                    }
                                    break;
                            }
                        }

                        LineDataSet set1;

                        // create a dataset and give it a type
                        set1 = new LineDataSet(yVals, "path");

                        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                        dataSets.add(set1); // add the datasets

                        // create a data object with the datasets
                        LineData data = new LineData(xVals, dataSets);

                        // set data
                        mChart.setData(data);

                        String resultStr = "Result: " + "" + xStart[0] + " " + yStart[0] + " " + facingDirection;
                        result.setText(resultStr);
                    } else {
                        Toast.makeText(getActivity(), "The facing direction must be either N-north, W-west, S-south, E-east", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please fill all the fields !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String switchCaseDirection(String instruction, String direction){
        switch (direction) {
            case "N":
                if(instruction.equals("R")){
                    direction = "E";
                }else{
                    direction = "W";
                }
                break;
            case "S":
                if(instruction.equals("R")) {
                    direction = "W";
                }else{
                    direction = "E";
                }
                break;
            case "E":
                if(instruction.equals("R")) {
                    direction = "S";
                }else{
                    direction = "N";
                }
                break;
            case "W":
                if(instruction.equals("R")) {
                    direction = "N";
                }else{
                    direction = "S";
                }
                break;
        }
        return direction;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
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
