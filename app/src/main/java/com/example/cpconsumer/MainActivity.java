package com.example.cpconsumer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new ShowAllASync(getApplicationContext()).execute();

            }
        });
    }

    public class ShowAllASync extends AsyncTask<Void, Void, String> {
        private final WeakReference<Context> weakReference;
        public ShowAllASync(Context applicationContext) {
            this.weakReference = new WeakReference<>(applicationContext);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }

        @SuppressLint("Range")
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder builder;

                Cursor cursor = weakReference.get().getContentResolver()
                        .query(Uri.parse("content://com.example.dreamprovider/dream_tbl"), null,
                                null, null, null);
                cursor.moveToFirst();
                builder = new StringBuilder();
                while (!cursor.isAfterLast()) {
                    builder.append("\n")
                            .append(cursor.getString(cursor.getColumnIndex("id")))
                            .append(". ")
                            .append(cursor.getString(cursor.getColumnIndex("name")));
                    cursor.moveToNext();
                }
                return builder.toString();
        }
    }
}