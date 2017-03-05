package com.example.ram.to_doapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    static TextView textView;
    EditText et1;
    AppCompatButton b1;
    ListView listView;
    ArrayList<Notepad> temp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.inputOne);
        b1 = (AppCompatButton) findViewById(R.id.clickMe);
        textView = (TextView) findViewById(R.id.totalTask);
        Typeface tf = Typeface.createFromAsset(getAssets(), "watch_regular.otf");
        et1.setTypeface(tf);
        textView.setTypeface(tf);
        final NoteDataHelper noteDataHelper = new NoteDataHelper(MainActivity.this);
        temp = (ArrayList<Notepad>) noteDataHelper.getAllNotes();
        final NotepadAdapter adapter = new NotepadAdapter(this, temp);
        textView.setText(Integer.toString(adapter.getCount()) + " Task");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String one = et1.getText().toString();
                if(!one.isEmpty()) {
                    noteDataHelper.addNote(new Notepad(one));
                    temp.add(new Notepad(one));
                    et1.setText("");
                    textView.setText(Integer.toString(adapter.getCount()) + " Task");
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView = (ListView) findViewById(R.id.notesList);
        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Notepad value = (Notepad) adapterView.getItemAtPosition(i);
////                Snackbar snackbar = Snackbar
////                        .make(coordinatorLayout, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);
////
////                snackbar.show();
//            }
//        });
    }
}

class NotepadAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Notepad> mDataSource;
    private NoteDataHelper noteDataHelper;
    NotepadAdapter(Context context, ArrayList<Notepad> notes) {
        this.mContext  = context;
        this.mDataSource = new ArrayList<Notepad>();
        this.mDataSource = notes;
        this.noteDataHelper = new NoteDataHelper(context);
    }
    @Override
    public int getCount(){
        return mDataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = null;
        if(row == null) {
            LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = mLayoutInflater.inflate(R.layout.activity_listview, viewGroup, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        final Notepad note = mDataSource.get(i);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "watch_regular.otf");
        holder.tv1.setTypeface(tf);
        holder.tv1.setText("#" + Integer.toString(i+1) + "    " +note.subject);
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataSource.remove(note);
                noteDataHelper.deleteNote(note);
                NotepadAdapter.this.notifyDataSetChanged();
                MainActivity.textView.setText(NotepadAdapter.this.getCount() + " Task");
            }
        });
        return row;
    }
}
class ViewHolder{
    TextView tv1;
    AppCompatImageButton b1;

    ViewHolder(View view){
        tv1 = (TextView) view.findViewById(R.id.subject);
        b1 = (AppCompatImageButton) view.findViewById(R.id.button);
    }
}