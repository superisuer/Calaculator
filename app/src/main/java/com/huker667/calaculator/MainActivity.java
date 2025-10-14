package com.huker667.calaculator;

import android.animation.*;
import android.animation.ObjectAnimator;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Vibrator;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.huker667.calaculator.databinding.*;
import java.io.*;
import java.text.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private MainBinding binding;
	private double nmode = 0;
	private String d_mode = "";
	private String n1s = "";
	private String n2s = "";
	private String nowstr = "";
	private double accuracy = 0;
	private double result = 0;
	private double result1 = 0;
	private double running = 0;
	private double trialmode = 0;
	
	private Vibrator v;
	private ObjectAnimator oa = new ObjectAnimator();
	private TimerTask t;
	private Intent i = new Intent();
	private SharedPreferences sp;
	private AlertDialog.Builder dialog;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = MainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		nmode = 1;
		d_mode = "N";
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		sp = getSharedPreferences("settings", Activity.MODE_PRIVATE);
		dialog = new AlertDialog.Builder(this);
		
		binding.textBigCalc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				v.vibrate((long)(300));
				oa.setTarget(binding.textBigCalc);
				oa.setFloatValues((float)(0), (float)(720));
				oa.setDuration((int)(1500));
				oa.setPropertyName("rotation");
				oa.setInterpolator(new DecelerateInterpolator());
				oa.start();
			}
		});
		
		binding.imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), SettingsActivity.class);
				startActivity(i);
			}
		});
		
		binding.seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				if (_progressValue == 0) {
					binding.textview2.setText("1");
					accuracy = 1;
				}
				if (_progressValue == 1) {
					binding.textview2.setText("0.1");
					accuracy = 0.1d;
				}
				if (_progressValue == 2) {
					binding.textview2.setText("0.01");
					accuracy = 0.01d;
				}
				if (_progressValue == 3) {
					binding.textview2.setText("0.001");
					accuracy = 0.001d;
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				
			}
		});
		
		binding.button17.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				nmode = 1;
				n1s = "";
				n2s = "";
				v.vibrate((long)(20));
				_UpdateViews();
			}
		});
		
		binding.button18.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (nmode == 1) {
					nmode = 2;
					binding.textMode.setText("2");
					oa.setTarget(binding.input2);
					oa.setPropertyName("scaleY");
					oa.setFloatValues((float)(1), (float)(1.2d));
					oa.setDuration((int)(60));
					oa.setRepeatMode(ValueAnimator.REVERSE);
					oa.setInterpolator(new DecelerateInterpolator());
					oa.setRepeatCount((int)(1));
					oa.start();
					v.vibrate((long)(100));
				} else {
					nmode = 1;
					binding.textMode.setText("1");
					oa.setTarget(binding.input);
					oa.setPropertyName("scaleY");
					oa.setFloatValues((float)(1), (float)(1.2d));
					oa.setDuration((int)(60));
					oa.setRepeatMode(ValueAnimator.REVERSE);
					oa.setInterpolator(new DecelerateInterpolator());
					oa.setRepeatCount((int)(1));
					oa.start();
					v.vibrate((long)(100));
				}
				_UpdateViews();
			}
		});
		
		binding.button20.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d_mode = "÷";
				binding.dmode.setText("÷");
			}
		});
		
		binding.button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_HandleInput("7");
				_UpdateViews();
			}
		});
		
		binding.button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_HandleInput("8");
				_UpdateViews();
			}
		});
		
		binding.button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_HandleInput("9");
				_UpdateViews();
			}
		});
		
		binding.button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d_mode = "×";
				binding.dmode.setText("×");
			}
		});
		
		binding.button5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_HandleInput("4");
				_UpdateViews();
			}
		});
		
		binding.button6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_HandleInput("5");
				_UpdateViews();
			}
		});
		
		binding.button7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_HandleInput("6");
				_UpdateViews();
			}
		});
		
		binding.button8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d_mode = "-";
				binding.dmode.setText("-");
			}
		});
		
		binding.button9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_HandleInput("1");
				_UpdateViews();
			}
		});
		
		binding.button10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_HandleInput("2");
				_UpdateViews();
			}
		});
		
		binding.button11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_HandleInput("3");
				_UpdateViews();
			}
		});
		
		binding.button12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d_mode = "+";
				binding.dmode.setText("+");
			}
		});
		
		binding.button14.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_HandleInput("0");
				_UpdateViews();
			}
		});
		
		binding.button15.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_HandleInput(".");
				_UpdateViews();
			}
		});
		
		binding.button16.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_HandleInput("=");
				v.vibrate((long)(50));
				_UpdateViews();
			}
		});
	}
	
	private void initializeLogic() {
		binding.textBigCalc.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/jetbrainsmono.ttf"), 0);
		binding.input2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/jetbrainsmono.ttf"), 0);
		binding.input.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/jetbrainsmono.ttf"), 0);
		binding.textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/jetbrainsmono.ttf"), 0);
		oa.setTarget(binding.imageview1);
		oa.setPropertyName("rotation");
		oa.setFloatValues((float)(0), (float)(720));
		oa.setDuration((int)(800));
		oa.setInterpolator(new DecelerateInterpolator());
		oa.start();
		if (sp.getString("majorchanges", "").equals("1.2")) {
			
		} else {
			v.vibrate((long)(300));
			sp.edit().putString("majorchanges", "1.2").commit();
			i.setClass(getApplicationContext(), ChangesActivity.class);
			startActivity(i);
		}
	}
	
	public void _UpdateViews() {
		runOnUiThread(() -> {
			if (nmode == 1) {
				binding.textMode.setText("1");
				binding.input.setTextColor(SketchwareUtil.getMaterialColor(MainActivity.this, R.attr.colorOnSurface));
				binding.input2.setTextColor(SketchwareUtil.getMaterialColor(MainActivity.this, R.attr.colorOnSurfaceVariant));
			} else {
				binding.textMode.setText("2");
				binding.input.setTextColor(SketchwareUtil.getMaterialColor(MainActivity.this, R.attr.colorOnSurfaceVariant));
				binding.input2.setTextColor(SketchwareUtil.getMaterialColor(MainActivity.this, R.attr.colorOnSurface));
			}
			binding.dmode.setText(d_mode);
			binding.input.setText(n1s);
			binding.input2.setText(n2s);
			binding.textview1.setText(String.valueOf(result));
		});
	}
	
	
	public void _HandleInput(final String _add) {
		v.vibrate((long)(20));
		if (nmode == 1) {
			nowstr = n1s;
		} else {
			nowstr = n2s;
		}
		if (_add.equals(".")) {
			if (nowstr.contains(".")) {
				v.vibrate((long)(500));
			} else {
				if (nowstr.equals("")) {
					v.vibrate((long)(500));
				} else {
					nowstr = nowstr.concat(".");
				}
			}
		}
		if (_add.equals("0")) {
			nowstr = nowstr.concat("0");
		}
		if (_add.equals("1")) {
			nowstr = nowstr.concat("1");
		}
		if (_add.equals("2")) {
			nowstr = nowstr.concat("2");
		}
		if (_add.equals("3")) {
			nowstr = nowstr.concat("3");
		}
		if (_add.equals("4")) {
			nowstr = nowstr.concat("4");
		}
		if (_add.equals("5")) {
			nowstr = nowstr.concat("5");
		}
		if (_add.equals("6")) {
			nowstr = nowstr.concat("6");
		}
		if (_add.equals("7")) {
			nowstr = nowstr.concat("7");
		}
		if (_add.equals("8")) {
			nowstr = nowstr.concat("8");
		}
		if (_add.equals("9")) {
			nowstr = nowstr.concat("9");
		}
		if (_add.equals("=")) {
			if (d_mode.equals("N")) {
				v.vibrate((long)(500));
			} else {
				result1 = 0;
				if (n1s.equals("")) {
					n1s = "0";
				}
				if (n2s.equals("")) {
					n2s = "0";
				}
				if (d_mode.equals("+")) {
					result = Double.parseDouble(n1s);
					for(int _repeat143 = 0; _repeat143 < (int)((Double.parseDouble(n2s) / Double.parseDouble(binding.textview2.getText().toString()))); _repeat143++) {
						result = result + Double.parseDouble(binding.textview2.getText().toString());
						if (sp.getString("show_realtime_calc", "").equals("1")) {
							_UpdateViews();
						}
					}
					if (sp.getString("negative_mode", "").equals("1")) {
						result = result * -1;
					}
				}
				if (d_mode.equals("-")) {
					result = Double.parseDouble(n1s);
					for(int _repeat184 = 0; _repeat184 < (int)((Double.parseDouble(n2s) / Double.parseDouble(binding.textview2.getText().toString()))); _repeat184++) {
						result = result - Double.parseDouble(binding.textview2.getText().toString());
						if (sp.getString("show_realtime_calc", "").equals("1")) {
							_UpdateViews();
						}
					}
					if (sp.getString("negative_mode", "").equals("1")) {
						result = result * -1;
					}
				}
				if (d_mode.equals("×")) {
					result = 0;
					for(int _repeat296 = 0; _repeat296 < (int)(Double.parseDouble(n2s)); _repeat296++) {
						result = result + Double.parseDouble(n1s);
						if (sp.getString("show_realtime_calc", "").equals("1")) {
							_UpdateViews();
						}
					}
					if (sp.getString("negative_mode", "").equals("1")) {
						result = result * -1;
					}
				}
				if (d_mode.equals("÷")) {
					result = 0;
					result1 = Double.parseDouble(n1s);
					running = 1;
					if (sp.getString("disable_div_by_zero", "").equals("1") && n2s.equals("0")) {
						
					} else {
						new Thread(() -> {
							while (running==1) {
								if (0.00001d < result1) {
									result1 = result1 - (Double.parseDouble(n2s) * Double.parseDouble(binding.textview2.getText().toString()));
									result++;
									if (sp.getString("show_realtime_calc", "").equals("1")) {
										_UpdateViews();
									}
								} else {
									result = result * Double.parseDouble(binding.textview2.getText().toString());
									if (sp.getString("negative_mode", "").equals("1")) {
										result = result * -1;
									}
									running = 0;
								}
							}
							
						}).start();
						
					}
				}
			}
		}
		if (nmode == 1) {
			n1s = nowstr;
		} else {
			n2s = nowstr;
		}
	}
	
}