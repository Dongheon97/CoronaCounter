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
    private IPGlobal ipGlobal = (IPGlobal) getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // 전역변수 가져옴.
        IPGlobal ipGlobal = (IPGlobal) getApplication() ;

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
                ipGlobal.setMaximum(10);
                ipGlobal.setLimited(4);
                ipGlobal.setCurrent(0);
                System.out.println(ipGlobal.getMaximum());
                System.out.println(ipGlobal.getLimited());
                System.out.println(ipGlobal.getCurrent());
                Intent intent = new Intent(getApplicationContext(), DetectorActivity.class);
                startActivity(intent);
            }
        });
    }
}
