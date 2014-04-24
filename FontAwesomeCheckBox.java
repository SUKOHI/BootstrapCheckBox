package com.sukohi.lib;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.beardedhen.androidbootstrap.R;

public class FontAwesomeCheckBox extends LinearLayout {

	private Context context;
	private View layout;
	private FontAwesomeText fontAwesomeTextChecked, fontAwesomeTextUnchecked;
	private TextView labelTextView;
	private static Map<String, Integer> typeColorMap;
	private boolean currentChecked = false;
	
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
	
	public FontAwesomeCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initialize(attrs);
	}

	public FontAwesomeCheckBox(Context context) {
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
		
		String bootstrapType = "default";
		String label = "";
		float textSize = 16.0f;
		boolean checked = false;
		
		//attribute values
		
		if (a.getString(R.styleable.FontAwesomeCheckBox_fc_type) != null) {
			bootstrapType = a.getString(R.styleable.FontAwesomeCheckBox_fc_type);
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
		
		int typeColor = getResources().getColor(typeColorMap.get(bootstrapType));
		a.recycle();
		
		setText(label);
		setTextSize(textSize);
		setChecked(checked);

		labelTextView.setTextColor(typeColor);
		fontAwesomeTextChecked.setTextColor(typeColor);
		fontAwesomeTextUnchecked.setTextColor(typeColor);
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				setChecked(!currentChecked);
				
			}
			
		});
		
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
