package info.lonerunner.pacecalculator;

import info.lonerunner.timepickerwithseconds.view.MyTimePickerDialog;
import info.lonerunner.timepickerwithseconds.view.TimePicker;

import android.R.array;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


public class PaceCalculator extends Activity
{
	
	private int mHour;
	private int mMinute;
	private int mSecond;
	
	private final int PACE_CALC_ID = 0;
	
	private EditText movingTime;
	private EditText customDistance;
	private Button calculate;
	private Spinner mileageUnitSelector;
	private Spinner distance;
	private TextView resultsTitle;
	
	private ScrollView scrollView;
	private LinearLayout mileageCustomBlock;
	private Switch customSwitch;
	private boolean customIsChecked;
	
	


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pace_calculator);
		
		initialiseItems();
		distanceEntryType(customIsChecked);
		
//		movingTime.setOnTouchListener(new View.OnTouchListener()
//		{
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event)
//			{
//				// TODO Auto-generated method stub
//				showPicker(v);
//				return false;
//			}
//		});
//		

		calculate.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				clearFocus();
				displayResults();
			}
		});
	
	}
	
	private void clearFocus()
	{
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(movingTime.getWindowToken(), 0);
		scrollToResult(resultsTitle);
		
			
	}
		
	private void distanceEntryType(boolean customIsChecked)
	{
		
		customSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				// TODO Auto-generated method stub
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) movingTime.getLayoutParams();
				int visible = 0;
				int invisible = 8;
				if(isChecked)
				{
					mileageCustomBlock.setVisibility(visible);
					distance.setVisibility(invisible);
					params.addRule(RelativeLayout.BELOW, R.id.mileage_custom_block);
					
				}else
				{
					mileageCustomBlock.setVisibility(invisible);
					distance.setVisibility(visible);
					
					params.addRule(RelativeLayout.BELOW, R.id.distance);
				}
				
			}
		});
		
	}
	

	private void scrollToResult(final View v)
	{
		final Handler handler = new Handler();
		
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							Thread.sleep(100);
						}catch(InterruptedException e)
						{
						}
						handler.post(new Runnable()
						{
							@Override
							public void run()
							{
								scrollView.smoothScrollTo(0, v.getTop());
							}
						});	
					}
                 }).start();
     }
	
	
	public void showPicker(View v)
	{
		
		String timeValue = movingTime.getText().toString();
		if(timeValue.equals(""))
		{
			timeValue = "00:30:00";
		}
		
		MyTimePickerDialog mTimePicker = new MyTimePickerDialog(this,
				new MyTimePickerDialog.OnTimeSetListener()
				{

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute, int seconds)
					{
						// TODO Auto-generated method stub
						movingTime.setText(String.format("%02d", hourOfDay) + ":"
								+ String.format("%02d", minute) + ":"
								+ String.format("%02d", seconds));
					}
				}, Integer.parseInt(timeValue.substring(0, 2)), Integer.parseInt(timeValue.substring(3, 5)),
				Integer.parseInt(timeValue.substring(6, 8)), true);
		mTimePicker.show();
	}
	
	
	private void displayResults()
	{
		int visibility = 0;
		
		LinearLayout resultsHeaderBlock2 = (LinearLayout)findViewById(R.id.result_header_block2);
		LinearLayout resultsHeaderBlock = (LinearLayout)findViewById(R.id.result_header_block);
		TextView resultTitle = (TextView)findViewById(R.id.results_title);
		View horizontalRule = (View)findViewById(R.id.horizontal_rule_result_header);
		resultsHeaderBlock.setVisibility(visibility);
		resultsHeaderBlock2.setVisibility(visibility);
		resultTitle.setVisibility(visibility);
		horizontalRule.setVisibility(visibility);
		ArrayAdapter<String> resultAdapter = new ArrayAdapter<String>(PaceCalculator.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.mileage_array));
		ListView listView = (ListView)findViewById(R.id.results_list_view);
		listView.setAdapter(resultAdapter);
		
		int totalHeight=0;
		for(int i=0 ;i< resultAdapter.getCount(); i++)
		{
			View arrayItem = resultAdapter.getView(i,null , listView);
			arrayItem.measure(0, 0);
			totalHeight += arrayItem.getMeasuredHeight();
		}
		
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (resultAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
        listView.setVisibility(visibility);
	}
	

	
	private void initialiseItems()
	{
		
		resultsTitle = (TextView)findViewById(R.id.results_title);
		this.mileageCustomBlock = (LinearLayout)findViewById(R.id.mileage_custom_block);
		customSwitch = (Switch)findViewById(R.id.custom_switch);
		customIsChecked = customSwitch.isChecked();
		distance = (Spinner)findViewById(R.id.distance);
		this.customDistance = (EditText)findViewById(R.id.distance_entry);
		this.mileageUnitSelector = (Spinner)findViewById(R.id.mileage_units_selector);
		this.movingTime = (EditText)findViewById(R.id.moving_time_entry);
		this.calculate =(Button)findViewById(R.id.calculate_button);
		scrollView = (ScrollView)findViewById(R.id.scroll_view);
		
		ArrayAdapter<CharSequence> distanceAdapter = ArrayAdapter.createFromResource(this, R.array.mileage_array, android.R.layout.simple_spinner_item); 
		distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		distance.setAdapter(distanceAdapter);
		
		ArrayAdapter<CharSequence> unitSelectorAdapter = ArrayAdapter.createFromResource(this, R.array.mileage_unit_array, android.R.layout.simple_spinner_item); 
		unitSelectorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mileageUnitSelector.setAdapter(unitSelectorAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pace_calculator, menu);
		menu.add("Pace in min/mile");
		return true;
	}

}
