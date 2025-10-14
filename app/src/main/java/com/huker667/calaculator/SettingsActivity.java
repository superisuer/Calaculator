package com.huker667.calaculator;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.CompoundButton;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.huker667.calaculator.databinding.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class SettingsActivity extends AppCompatActivity {
	
	private SettingsBinding binding;
	
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = SettingsBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		sp = getSharedPreferences("settings", Activity.MODE_PRIVATE);
		
		binding.switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					sp.edit().putString("disable_div_by_zero", "1").commit();
				} else {
					sp.edit().putString("disable_div_by_zero", "0").commit();
				}
			}
		});
		
		binding.switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					sp.edit().putString("show_realtime_calc", "1").commit();
				} else {
					sp.edit().putString("show_realtime_calc", "0").commit();
				}
			}
		});
		
		binding.switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					sp.edit().putString("infinity_calc_protection", "1").commit();
				} else {
					sp.edit().putString("infinity_calc_protection", "0").commit();
				}
			}
		});
		
		binding.switch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					sp.edit().putString("negative_mode", "1").commit();
				} else {
					sp.edit().putString("negative_mode", "0").commit();
				}
			}
		});
		
		binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					sp.edit().putString("disable_end_check", "1").commit();
				} else {
					sp.edit().putString("disable_end_check", "0").commit();
				}
			}
		});
	}
	
	private void initializeLogic() {
		binding.switch1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/googlesans.ttf"), 0);
		binding.switch2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/googlesans.ttf"), 0);
		binding.switch3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/googlesans.ttf"), 0);
		binding.switch4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/googlesans.ttf"), 0);
		binding.switch5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/googlesans.ttf"), 0);
		binding.textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/jetbrainsmono.ttf"), 0);
	}
	
}