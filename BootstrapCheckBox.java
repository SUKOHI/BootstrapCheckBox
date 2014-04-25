package com.sukohi.lib;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.beardedhen.androidbootstrap.R;

public class BootstrapCheckBox extends LinearLayout {

	private Context context;
	private View layout;
	private FontAwesomeText fontAwesomeTextChecked, fontAwesomeTextUnchecked;
	private TextView labelTextView;
	private static Map<String, Integer> typeColorMap;
	private boolean currentChecked = false;
	private TYPE_COLOR currentTypeColor;
	
	static {

		typeColorMap = new HashMap<String, Integer>();
		typeColorMap.put("default", R.color.bbutton_default);
		typeColorMap.put("primary", R.color.bbutton_primary);
		typeColorMap.put("success", R.color.bbutton_success);
		typeColorMap.put("info", R.color.bbutton_info);
		typeColorMap.put("warning", R.color.bbutton_warning);
		typeColorMap.put("danger", R.color.bbutton_danger);
		typeColorMap.put("inverse", R.color.bbutton_inverse);
		
	}
	
	private enum TYPE_COLOR {
		
		DEFAULT("default", R.color.bbutton_default, R.color.bbutton_default_pressed), 
		PRIMARY("primary", R.color.bbutton_primary, R.color.bbutton_primary_pressed), 
		SUCCESS("success", R.color.bbutton_success, R.color.bbutton_success_pressed), 
		INFO("info", R.color.bbutton_info, R.color.bbutton_info_pressed), 
		WARNING("warning", R.color.bbutton_warning, R.color.bbutton_warning_pressed), 
		DANGER("danger", R.color.bbutton_danger, R.color.bbutton_danger_pressed), 
		INVERSE("inverse", R.color.bbutton_inverse, R.color.bbutton_inverse_pressed);

		private String symbol;
		private int defaultColorResourceId;
		private int pressedColorResourceId;
		
		private TYPE_COLOR(String symbol, int defaultColorResourceId, int pressedColorResourceId) {
			
			this.symbol = symbol;
			this.defaultColorResourceId = defaultColorResourceId;
			this.pressedColorResourceId = pressedColorResourceId;
			
		}
		
	}
	
	public BootstrapCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initialize(attrs);
	}

	public BootstrapCheckBox(Context context) {
		super(context);
		this.context = context;
		initialize(null);
	}
	
	private void initialize(AttributeSet attrs) {
		
        layout = LayoutInflater.from(context).inflate(R.layout.bootstrap_checkbox, this);
        fontAwesomeTextChecked = (FontAwesomeText) layout.findViewById(R.id.checkbox_fontawesome_checked);
        fontAwesomeTextUnchecked = (FontAwesomeText) layout.findViewById(R.id.checkbox_fontawesome_unchecked);
        labelTextView = (TextView) layout.findViewById(R.id.checkbox_label);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontAwesomeCheckBox);
		
		//defaults
		
		currentTypeColor = TYPE_COLOR.INVERSE;
		String label = "";
		float textSize = 16.0f;
		boolean checked = false;
		
		//attribute values
		
		if (a.getString(R.styleable.FontAwesomeCheckBox_fc_type) != null) {
			String bootstrapType = a.getString(R.styleable.FontAwesomeCheckBox_fc_type);
			setType(bootstrapType);
		}

		if(a.getString(R.styleable.FontAwesomeCheckBox_fc_text) != null) {
			label = a.getString(R.styleable.FontAwesomeCheckBox_fc_text);
		}

		if(a.getString(R.styleable.FontAwesomeCheckBox_fc_text_size) != null) {
			textSize = a.getFloat(R.styleable.FontAwesomeCheckBox_fc_text_size, textSize);
		}

		if(a.getString(R.styleable.FontAwesomeCheckBox_fc_checked) != null) {
			checked = a.getBoolean(R.styleable.FontAwesomeCheckBox_fc_checked, false);
		}
		
		a.recycle();
		
		setText(label);
		setTextSize(textSize);
		setChecked(checked);
		setTypeColor(currentTypeColor.defaultColorResourceId);
		
		layout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
				
				case MotionEvent.ACTION_DOWN:
					setChecked(!currentChecked);
					setTypeColor(currentTypeColor.pressedColorResourceId);
					break;
					
				default:
					setTypeColor(currentTypeColor.defaultColorResourceId);
					break;
					
				}
				
				return true;
				
			}
			
		});
		
	}
	
	public void setType(String bootstrapType) {
		
		for (TYPE_COLOR typeColor : TYPE_COLOR.values()) {
			
			if(bootstrapType.equals(typeColor.symbol)) {

				currentTypeColor = typeColor;
				
			}
			
		}
		
	}
	
	public void setText(String text) {
		
		labelTextView.setText(text);
		
	}
	
	public void setTextSize(float textSize) {

		labelTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		fontAwesomeTextChecked.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		fontAwesomeTextUnchecked.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		
	}
	
	public void setChecked(boolean checked) {
		
		currentChecked = checked;
		int checkedVisibility, uncheckedVisibility;
		
		if(checked) {
			
			checkedVisibility = View.VISIBLE;
			uncheckedVisibility = View.INVISIBLE;
			
		} else {
			
			checkedVisibility = View.INVISIBLE;
			uncheckedVisibility = View.VISIBLE;
			
		}
		
		fontAwesomeTextChecked.setVisibility(checkedVisibility);
		fontAwesomeTextUnchecked.setVisibility(uncheckedVisibility);

	}
	
	public boolean isChecked() {
		
		return (currentChecked);
		
	}
	
	public void setTypeColor(int colorResourceId) {

		int color = getResources().getColor(colorResourceId);
		labelTextView.setTextColor(color);
		fontAwesomeTextChecked.setTextColor(color);
		fontAwesomeTextUnchecked.setTextColor(color);
		
	}
	
}
/*** Example

	// XML

	<com.sukohi.lib.FontAwesomeCheckBox 
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content"
	    bootstrap:fc_type="primary"
	    bootstrap:fc_text="text"
	    bootstrap:fc_text_size="16"
	    bootstrap:fc_checked="true" />

	// Code

	fontAwesomeCheckBox.setText("text");
	fontAwesomeCheckBox.setTextSize(16);
	fontAwesomeCheckBox.setChecked(true);

***/
