package com.testosterol.apps.roboto.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;

import android.net.Uri;
import android.os.Bundle;

import com.testosterol.apps.roboto.R;
import com.testosterol.apps.roboto.robot.CasualRobotFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener,
        CasualRobotFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupNavigation(getIntent().getExtras());
    }

    private void setupNavigation(Bundle bundle) {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavInflater navInflater = navController.getNavInflater();
            NavGraph graph = navInflater.inflate(R.navigation.nav_graph);
            navHostFragment.getNavController().setGraph(graph, bundle);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
