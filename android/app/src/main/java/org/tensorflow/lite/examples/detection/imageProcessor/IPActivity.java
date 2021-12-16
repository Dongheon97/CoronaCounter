package org.tensorflow.lite.examples.detection.imageProcessor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.examples.detection.R;

public class IPActivity extends AppCompatActivity {
    private IPGlobal ipGlobal;
    private int getMax;
    private int getLimit;
    private int current = 0 ;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                current = data.getIntExtra("current",0);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("current", current);
        if (current!= 0){
            setResult(RESULT_OK, intent);
        }
        else{
            setResult(RESULT_CANCELED, intent);
        }
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        getMax = intent.getIntExtra("max", 0);
        getLimit = intent.getIntExtra("limit", 0);

        // 전역변수 가져옴.
        ipGlobal = (IPGlobal) getApplication();
        ipGlobal.setMaximum(getMax);
        ipGlobal.setLimited(getLimit);

        setContentView(R.layout.tfe_od_choose_camera);

        Switch cameraSwitch = findViewById(R.id.cameraSwitch);
        cameraSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    ipGlobal.setIsBack(true);
                    System.out.println("This is Back Camera");
                }
                else{
                    ipGlobal.setIsBack(false);
                    System.out.println("This is Front Camera");
                }
            }
        });

        Switch entranceSwitch = findViewById(R.id.entranceSwitch);
        entranceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    ipGlobal.setIsRight(true);
                    System.out.println("This is Right Entrance");
                }
                else{
                    ipGlobal.setIsRight(false);
                    System.out.println("This is Left Entrance");
                }
            }
        });

        Button countButton = findViewById(R.id.StartCounting);
        countButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                System.out.println(ipGlobal.getMaximum());
                System.out.println(ipGlobal.getLimited());
                Intent intent = new Intent(getApplicationContext(), DetectorActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }
}
