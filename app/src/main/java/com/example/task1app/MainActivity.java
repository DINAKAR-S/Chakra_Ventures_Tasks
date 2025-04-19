package com.example.task1app;

import android.Manifest;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int MIC_PERMISSION_CODE = 101;

    private static final String BOOKS_API = "http://192.168.72.8:5000/books";      // Replace with your IP
    private static final String IPL_API = "http://192.168.72.8:5000/ipl-teams";    // Replace with your IP

    TextView textApiResult, textInstalledApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buttons
        Button buttonCamera = findViewById(R.id.button_camera);
        Button buttonMic = findViewById(R.id.button_mic);
        Button buttonBooks = findViewById(R.id.button_fetch_books);
        Button buttonIpl = findViewById(R.id.button_fetch_ipl);
        Button buttonApps = findViewById(R.id.button_installed_apps);

        // Output views
        textApiResult = findViewById(R.id.text_api_result);
        textInstalledApps = findViewById(R.id.text_installed_apps);

        // Button actions
        buttonCamera.setOnClickListener(view ->
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE));

        buttonMic.setOnClickListener(view ->
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, MIC_PERMISSION_CODE));

        buttonBooks.setOnClickListener(view -> {
            textApiResult.setText("Fetching books...");
            fetchApiData(BOOKS_API, "Books");
        });

        buttonIpl.setOnClickListener(view -> {
            textApiResult.setText("Fetching IPL teams...");
            fetchApiData(IPL_API, "IPL Teams");
        });

        buttonApps.setOnClickListener(this::listInstalledApps);
    }

    private void fetchApiData(String urlString, String label) {
        new Thread(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder rawJson = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    rawJson.append(inputLine);
                }
                in.close();

                // Parse JSON (assumes format: [{"title": "..."}] or [{"name": "..."}])
                JSONArray jsonArray = new JSONArray(rawJson.toString());
                StringBuilder result = new StringBuilder(label + ":\n");

                for (int i = 0; i < jsonArray.length(); i++) {
                    String item = "";
                    if (jsonArray.getJSONObject(i).has("title")) {
                        item = jsonArray.getJSONObject(i).getString("title");
                    } else if (jsonArray.getJSONObject(i).has("name")) {
                        item = jsonArray.getJSONObject(i).getString("name");
                    }
                    result.append("• ").append(item).append("\n");
                }

                String finalOutput = result.toString();
                runOnUiThread(() -> textApiResult.setText(finalOutput));

            } catch (Exception e) {
                runOnUiThread(() -> textApiResult.setText("Error fetching " + label + ":\n" + e.getMessage()));
            }
        }).start();
    }


    private void listInstalledApps(android.view.View view) {
        StringBuilder appsList = new StringBuilder();
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo appInfo : packages) {
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String label = pm.getApplicationLabel(appInfo).toString();
                appsList.append(label).append("\n");
            }
        }

        textInstalledApps.setText("Installed Apps:\n" + appsList.toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if ((requestCode == CAMERA_PERMISSION_CODE || requestCode == MIC_PERMISSION_CODE)
                && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted (optional toast or log)
        } else {
            // Permission denied (optional toast or log)
        }
    }
}
