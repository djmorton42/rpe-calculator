package ca.quadrilateral.rpecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupComponents();
    }

    private void setupComponents() {
        buildRPESpinner();
        buildRepsSpinner();
        buildTargetRPESpinner();
        buildTargetRepsSpinner();
        buildWeightText();
    }

    private void buildWeightText() {
        final EditText editText = findViewById(R.id.weightText);
        editText.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable editable) {
                updateWeightDisplays();
            }
        });
    }

    private void buildRPESpinner() {
        final Spinner spinner = findViewById(R.id.rpeSpinner);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(
                        this,
                        R.array.rpe_array,
                        android.R.layout.simple_spinner_item
                );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerChangeListener());
    }

    private void buildTargetRPESpinner() {
        final Spinner spinner = findViewById(R.id.targetRPESpinner);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(
                        this,
                        R.array.rpe_array,
                        android.R.layout.simple_spinner_item
                );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerChangeListener());
    }

    private void buildRepsSpinner() {
        final Spinner spinner = findViewById(R.id.repsSpinner);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(
                        this,
                        R.array.reps_array,
                        android.R.layout.simple_spinner_item
                );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerChangeListener());
    }

    private void buildTargetRepsSpinner() {
        final Spinner spinner = findViewById(R.id.targetRepsSpinner);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(
                        this,
                        R.array.reps_array,
                        android.R.layout.simple_spinner_item
                );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerChangeListener());
    }

    private RPECalculator buildRPECalculator() {
        final Spinner targetRpeSpinner = findViewById(R.id.targetRPESpinner);
        final Spinner targetRepsSpinner = findViewById(R.id.targetRepsSpinner);

        final Spinner rpeSpinner = findViewById(R.id.rpeSpinner);
        final Spinner repsSpinner = findViewById(R.id.repsSpinner);
        final EditText weightText = findViewById(R.id.weightText);

        final Integer reps = Integer.parseInt((String) repsSpinner.getSelectedItem());
        final String rpe = (String) rpeSpinner.getSelectedItem();
        final Double weight = Double.parseDouble(weightText.getText().toString());

        final Integer targetReps = Integer.parseInt((String) targetRepsSpinner.getSelectedItem());
        final String targetRpe = (String) targetRpeSpinner.getSelectedItem();

        return new RPECalculator(weight, reps, rpe, targetReps, targetRpe);
    }

    private void updateWeightDisplays() {
        try {
            final RPECalculator rpeCalculator = buildRPECalculator();
            setEstimated1RMText(rpeCalculator.estimated1RM());
            setTargetWeightText(rpeCalculator.targetWeight());
        } catch (final Exception e) {
            clearWeightDisplays();
        }
    }

    private void clearWeightDisplays() {
        setEstimated1RMText("---");
        setTargetWeightText("---");
    }

    private void setTargetWeightText(final String targetWeight) {
        final TextView textView = findViewById(R.id.targetWeightText);
        textView.setText(targetWeight);
    }

    private void setTargetWeightText(final double targetWeight) {
        setTargetWeightText(
                NumberFormat.getIntegerInstance().format(targetWeight)
        );
    }

    private void setEstimated1RMText(final String estimated1RM) {
        final TextView textView = findViewById(R.id.estimated1RMText);
        textView.setText(estimated1RM);
    }

    private void setEstimated1RMText(final double estimated1RM) {
        setEstimated1RMText(
                NumberFormat.getIntegerInstance().format(estimated1RM)
        );
    }

    private class SpinnerChangeListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(
                AdapterView<?> adapterView,
                View view,
                int position,
                long rowId) {

            updateWeightDisplays();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            updateWeightDisplays();
        }
    }
}
