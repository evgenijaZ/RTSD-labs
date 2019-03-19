package edu.kpi.comsys.esp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.kpi.comsys.esp.algorythms.FermaFactorizationMethod;
import edu.kpi.comsys.esp.algorythms.FermaFactorizationMethod.Multipliers;
import edu.kpi.comsys.esp.algorythms.NeuralNetwork;
import edu.kpi.comsys.esp.algorythms.NeuralNetwork.Dot;
import edu.kpi.comsys.esp.algorythms.NeuralNetwork.Result;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.text.MessageFormat.format;

public class MainActivity extends AppCompatActivity {
    private static final String TIME_TEXT_FORMAT = "{0}:{1}:{2}";
    private static final String MESSAGE_FORMAT = "n = {0} * {1}";
    private static final String MESSAGE2_FORMAT = "w1 = {0}, w2 = {1}";
    private final String TOAST_MESSAGE = "Number has been successfully factorized!";
    private final String TOAST2_MESSAGE = "Neural network is finished calculations!";
    private FermaFactorizationMethod factorizationMethod = new FermaFactorizationMethod();
    private NeuralNetwork neuralNetwork = new NeuralNetwork();
    private String TOAST2_MESSAGE_FAILURE = "Unnable to finish calculation with actual arguments!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButtonFactorize();
        addListenerOnButtonRunNetwork();
    }

    private void addListenerOnButtonFactorize() {
        Button button = findViewById(R.id.factorize_button);
        button.setOnClickListener(v -> {
            int number = extractIntValue(R.id.number);
            Multipliers multipliers = factorizationMethod.findMultipliers(number);
            TextView answer = findViewById(R.id.answer);
            answer.setText(format(MESSAGE_FORMAT, multipliers.getA(), multipliers.getB()));
            TextView time = findViewById(R.id.time);
            time.setText(millisToString(multipliers.getTimeElapsed()));
            Toast.makeText(getApplicationContext(), TOAST_MESSAGE, Toast.LENGTH_LONG).show();
        });
    }

    private void addListenerOnButtonRunNetwork() {
        Button button = findViewById(R.id.neural_network_button);
        button.setOnClickListener(v -> {
            double x1 = extractDoubleValue(R.id.x1);
            double x2 = extractDoubleValue(R.id.x2);
            double y1 = extractDoubleValue(R.id.y1);
            double y2 = extractDoubleValue(R.id.y2);
            double p = extractDoubleValue(R.id.p);
            double sigma = extractDoubleValue(R.id.sigma);
            Optional<Result> optionalResult = neuralNetwork.run(new Dot(x1, y1), new Dot(x2, y2), p, sigma);
            if (optionalResult.isPresent()) {
                Result result = optionalResult.get();
                TextView answer = findViewById(R.id.answer2);
                answer.setText(format(MESSAGE2_FORMAT, result.getW1(), result.getW2()));
                Toast.makeText(getApplicationContext(), TOAST2_MESSAGE, Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getApplicationContext(), TOAST2_MESSAGE_FAILURE, Toast.LENGTH_LONG).show();

        });
    }

    private int extractIntValue(int viewId) {
        EditText editText = findViewById(viewId);
        String text = editText.getText().toString();
        return parseInt(text);
    }

    private double extractDoubleValue(int viewId) {
        EditText editText = findViewById(viewId);
        String text = editText.getText().toString();
        return parseDouble(text);
    }

    private String millisToString(long millis){
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes);
        long milliseconds = TimeUnit.MILLISECONDS.toMillis(millis) - TimeUnit.SECONDS.toMillis(seconds);
        return format(TIME_TEXT_FORMAT,minutes,seconds,milliseconds);
    }
}
