package org.mirine.imagesender;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        Intent intent = getIntent();
        String room = intent.getExtras().getString(Intent.EXTRA_TEXT);
        sendImage(room.split("::")[0], room.split("::")[1]);
    }

    public void sendImage(final String num, final String uri) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/imagesender/setting.txt"));
            final String[] h = br.readLine().split(":");
                try {
                    File file = new File(uri);
                    Uri ur = Uri.fromFile(file);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_STREAM, ur);
                    intent.setPackage("com.kakao.talk");
                    startActivity(intent);
                    Thread.sleep(3900);
                    AccessibilityServiceManager manager = AccessibilityServiceManager.getInstance();
                    manager.dispatch(Integer.parseInt(h[0].split(",")[0]), Integer.parseInt(h[0].split(",")[1]));
                    Thread.sleep(1200);
                    manager.dispatch(Integer.parseInt(h[Integer.parseInt(num) + 1].split(",")[0]), Integer.parseInt(h[Integer.parseInt(num) + 1].split(",")[1]));
                    Thread.sleep(1200);
                    manager.dispatch(Integer.parseInt(h[1].split(",")[0]), Integer.parseInt(h[1].split(",")[1]));
                    Thread.sleep(2500);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(Intent.ACTION_MAIN); //태스크의 첫 액티비티로 시작
        intent.addCategory(Intent.CATEGORY_HOME);   //홈화면 표시
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //새로운 태스크를 생성하여 그 태스크안에서 액티비티 추가
        startActivity(intent);
        finish();

    }

}
