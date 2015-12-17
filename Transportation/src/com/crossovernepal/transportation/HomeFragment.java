package com.crossovernepal.transportation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

@SuppressLint("InflateParams")
public class HomeFragment extends Fragment implements View.OnClickListener {

	public static AutoCompleteTextView fromPath, toPath, throughPath1,
			throughPath2, throughPath3, toTextView, fromTextView;
	public static EditText fromPrice, toPrice;
	ImageButton bus, micro, tempo, all;

	Button btnReverse, btnAdd, btnRemove;
	ImageButton reloadBtn_black;
	LinearLayout llAdd, llRemove, llRemove1, llremoveOuter, llremoveOuter1;
	ScrollView mainLayout;
	LinearLayout top;

	public int count = 0;
	public static boolean btnaddfirst;
	public static boolean btnaddsecond;
	MySQLiteHelper dbHelper;
	String day1;

	@SuppressWarnings("rawtypes")
	ArrayAdapter adapter;

	LayoutInflater layoutInflater;

	InputMethodManager imm;

	// @SuppressWarnings({ "retypes", "unchecked" })

	@SuppressWarnings({})
	@Override
	public View onCreateView(final LayoutInflater inflater,
			ViewGroup container, final Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.home, container, false);

		dbHelper = new MySQLiteHelper(getActivity());
		imm = (InputMethodManager) getActivity().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		HomeActivity.backBtn.setVisibility(View.INVISIBLE);

		reloadBtn_black = (ImageButton) view.findViewById(R.id.reloadBtn_black);

		fromPath = (AutoCompleteTextView) view.findViewById(R.id.fromTextView1);

		toPath = (AutoCompleteTextView) view.findViewById(R.id.toTextView5);

		throughPath1 = (AutoCompleteTextView) view
				.findViewById(R.id.through1TextView2);

		llRemove = (LinearLayout) view.findViewById(R.id.llRemove);
		llRemove1 = (LinearLayout) view.findViewById(R.id.llRemove1);
		llremoveOuter = (LinearLayout) view.findViewById(R.id.llremoveOuter);
		llremoveOuter1 = (LinearLayout) view.findViewById(R.id.llremoveOuter1);
		btnReverse = (Button) view.findViewById(R.id.btnReverse);
		fromPrice = (EditText) view.findViewById(R.id.fromprice);
		toPrice = (EditText) view.findViewById(R.id.toprice);

		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		bus = (ImageButton) view.findViewById(R.id.button_bus);
		micro = (ImageButton) view.findViewById(R.id.button_micro);
		tempo = (ImageButton) view.findViewById(R.id.button_tempo);

		all = (ImageButton) view.findViewById(R.id.button_all);

		layoutInflater = (LayoutInflater) getActivity().getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		bus.setOnClickListener(this);
		micro.setOnClickListener(this);

		tempo.setOnClickListener(this);
		all.setOnClickListener(this);

		btnAdd = (Button) view.findViewById(R.id.btnAddThrough);
		btnRemove = (Button) view.findViewById(R.id.btnRemoveThrough);
		mainLayout = (ScrollView) view.findViewById(R.id.main_layout);

		mainLayout.setOnTouchListener(new View.OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				InputMethodManager keyboard = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				keyboard.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
				return false;
			}
		});
		top = (LinearLayout) view.findViewById(R.id.top);
		top.setOnTouchListener(new View.OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				InputMethodManager keyboard = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				keyboard.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

				return false;
			}
		});

		fromPath.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				fromPath.setError(null);
				toPath.setError(null);
				throughPath1.setError(null);

			}
		});
		toPath.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				toPath.setError(null);
				throughPath1.setError(null);
				fromPath.setError(null);

			}
		});
		fromPrice.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				toPath.setError(null);
				throughPath1.setError(null);
				fromPath.setError(null);

			}
		});
		toPrice.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				toPath.setError(null);
				throughPath1.setError(null);
				fromPath.setError(null);

			}
		});
		btnAdd.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("NewApi")
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (count < 2) {
					count++;
					if ((count == 1)) {

						final LayoutInflater inflater = getLayoutInflater(savedInstanceState);

						llRemove.setVisibility(View.VISIBLE);
						llremoveOuter.setVisibility(View.VISIBLE);
						throughPath2 = (AutoCompleteTextView) inflater.inflate(
								R.layout.dynamic_autocomplete, null);

						throughPath2.setTag("av" + count);
						throughPath2.setThreshold(1);
						throughPath2.setAdapter(adapter);

						throughPath2
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										// TODO Auto-generated method stub
										imm.toggleSoftInput(
												InputMethodManager.SHOW_FORCED,
												0);

									}
								});
						llRemove.addView(throughPath2);

						btnRemove.setVisibility(View.VISIBLE);
						btnaddfirst = true;
						throughPath1
								.setOnFocusChangeListener(new OnFocusChangeListener() {
									@Override
									public void onFocusChange(View rv,
											boolean hasFocus) {

										throughPath2.setError(null);
										toPath.setError(null);
										throughPath1.setError(null);
										fromPath.setError(null);

									}
								});

					} else if (count == 2) {
						llRemove1.setVisibility(View.VISIBLE);
						llremoveOuter1.setVisibility(View.VISIBLE);
						throughPath3 = (AutoCompleteTextView) inflater.inflate(
								R.layout.dynamic_autocomplete, null);

						throughPath3.setTag("av" + count);
						throughPath3.setThreshold(1);
						throughPath3.setAdapter(adapter);

						throughPath3
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										// TODO Auto-generated method stub
										imm.toggleSoftInput(
												InputMethodManager.SHOW_FORCED,
												0);

									}
								});

						llRemove1.addView(throughPath3);

						btnaddsecond = true;

						throughPath1
								.setOnFocusChangeListener(new OnFocusChangeListener() {
									@Override
									public void onFocusChange(View rv,
											boolean hasFocus) {
										if (hasFocus) {
											throughPath2.setError(null);
											throughPath3.setError(null);

										}

									}
								});
						throughPath2
								.setOnFocusChangeListener(new OnFocusChangeListener() {
									@Override
									public void onFocusChange(View rv,
											boolean hasFocus) {

										if (hasFocus) {

											throughPath3.setError(null);

										}
									}

								});

					}
				}
			}

		});
		btnRemove.setOnClickListener(new OnClickListener() {

			// @SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {

				if (count == 1) {
					System.out.println("count:" + count);

					View av = getView().findViewWithTag("av" + count);

					llRemove.removeView(av);
					llRemove.setVisibility(View.GONE);
					llremoveOuter.setVisibility(View.GONE);
					if (shared.through2String != null)
						shared.through2String = "";
					count--;
					btnaddfirst = false;
					if (count == 0)
						btnRemove.setVisibility(View.GONE);
				} else if (count == 2) {
					System.out.println("count:" + count);

					View av = getView().findViewWithTag("av" + count);

					llRemove1.removeView(av);
					llRemove1.setVisibility(View.GONE);
					llremoveOuter1.setVisibility(View.GONE);
					if (shared.through3String != null)
						shared.through3String = "";
					count--;
					btnaddsecond = false;

				}

			}
		});

		updateAutocompleteTextfields();

		btnReverse = (Button) view.findViewById(R.id.btnReverse);
		btnReverse.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				ArrayList<String> myArrayList = new ArrayList<String>();
				myArrayList.add(fromPath.getText().toString());
				myArrayList.add(toPath.getText().toString());

				System.out.println("Current values of from and to: "
						+ myArrayList);

				Collections.reverse(myArrayList);
				String getFrom;
				getFrom = myArrayList.get(0);
				fromPath.setText(getFrom);

				String getTo;
				getTo = myArrayList.get(1);
				toPath.setText(getTo);
				System.out.println("Reveresed data: " + myArrayList);
				toPath.requestFocus();

			}
		});
		reloadBtn_black.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "Page Refreshed",
						Toast.LENGTH_SHORT).show();
				fromPath.setText("");
				fromPath.setEnabled(true);
				fromPath.setError(null);
				fromPath.setFocusableInTouchMode(true);

				throughPath1.setText("");
				throughPath1.setEnabled(true);
				throughPath1.setFocusableInTouchMode(true);
				throughPath1.setError(null);

				toPath.setText("");
				toPath.setEnabled(true);
				toPath.setFocusableInTouchMode(true);
				toPath.setError(null);

				fromPrice.setText("");
				fromPrice.setEnabled(true);
				fromPrice.setError(null);
				fromPrice.setFocusableInTouchMode(true);

				toPrice.setText("");
				toPrice.setEnabled(true);
				toPrice.setError(null);
				toPrice.setFocusableInTouchMode(true);

				if (throughPath2 != null) {
					throughPath2.setText("");
					throughPath2.setEnabled(true);
					throughPath2.setFocusableInTouchMode(true);
					throughPath2.setError(null);

				}
				if (throughPath3 != null) {
					throughPath3.setText("");
					throughPath3.setEnabled(true);
					throughPath3.setFocusableInTouchMode(true);
					throughPath3.setError(null);
				}

			}
		});

		return view;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateAutocompleteTextfields() {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

		String[] route_name;
		int i = 0;
		try {
			list = dbHelper.getAllColumnsData("Select * from node");

			route_name = new String[list.size()];
			for (ArrayList temp : list) {
				route_name[i] = temp.get(1).toString();
				i++;
				System.out.println("crossover:" + temp.get(1));

			}
			adapter = new ArrayAdapter(getActivity().getApplicationContext(),
					R.layout.text_view, route_name);
			fromPath.setThreshold(1);
			fromPath.setAdapter(adapter);

			fromPath.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

				}
			});

			toPath.setThreshold(1);
			toPath.setAdapter(adapter);

			toPath.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

				}
			});

			throughPath1.setThreshold(1);
			throughPath1.setAdapter(adapter);

			throughPath1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

				}
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; MainActivity.this adds items to the action bar if
		// it is present.
		inflater.inflate(R.menu.reload, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {

		case R.id.reload:

			refresh();
			break;

		default:
			break;
		}

		return true;
	}

	public void makeMail() {
		try {
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			i.putExtra(Intent.EXTRA_EMAIL,
					new String[] { "info@crossovernepal.com" });
			i.putExtra(Intent.EXTRA_SUBJECT, "Your Subject");
			i.putExtra(Intent.EXTRA_TEXT, "");
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(getActivity().getApplicationContext(),
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void refresh() {
		// make all editext fields empty

		fromPath.setText("");
		throughPath1.setText("");
		toPath.setText("");

		fromPrice.setText("");
		toPrice.setText("");

		if (HomeFragment.throughPath2 != null) {
			HomeFragment.throughPath2.setText("");
			HomeFragment.throughPath2.setEnabled(true);
			HomeFragment.throughPath2.setFocusableInTouchMode(true);
			HomeFragment.throughPath2.setError(null);

		}
		if (HomeFragment.throughPath3 != null) {
			HomeFragment.throughPath3.setText("");
			HomeFragment.throughPath3.setEnabled(true);
			HomeFragment.throughPath3.setFocusableInTouchMode(true);
			HomeFragment.throughPath3.setError(null);
		}

		fromPath.setError(null);
		throughPath1.setError(null);
		toPath.setError(null);

		fromPrice.setError(null);
		toPrice.setError(null);
		throughPath1.setFocusable(true);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.button_bus:
			shared.vehicle = "Bus";
			search();
			break;

		case R.id.button_micro:
			shared.vehicle = "Micro";
			search();
			break;

		case R.id.button_tempo:
			shared.vehicle = "Tempo";
			search();
			break;

		case R.id.button_all:
			shared.vehicle = "all";
			search();
			break;

		default:
			break;
		}

	}

	public void search() {

		if (!isEmpty(fromPath)) {
			fromPath.getText().clearSpans();
			shared.fromString = fromPath.getText().toString();
		}
		if (!isEmpty(toPath))
			shared.toString = toPath.getText().toString();

		if (!isEmpty(throughPath1))
			shared.through1String = throughPath1.getText().toString();
		if (btnaddfirst == true) {
			if (!isEmpty(throughPath2))
				shared.through2String = throughPath2.getText().toString();
		}
		if (btnaddsecond == true) {
			if (!isEmpty(throughPath3))
				shared.through3String = throughPath3.getText().toString();
		}
		if (!isEmpty(fromPrice))
			shared.fromPrice = fromPrice.getText().toString();

		if (!isEmpty(toPrice))
			shared.toPrice = toPrice.getText().toString();

		if (fromPath.getText().length() > 0 && toPath.getText().length() == 0) {
			toPath.setError("Enter destination address");

		} else if (toPath.getText().length() > 0
				&& fromPath.getText().length() == 0) {
			fromPath.setError("Enter source address");

		} else if ((fromPrice.getText().length() > 0)
				&& (toPrice.getText().length() == 0)) {
			toPrice.setError("Enter to price");

		} else if ((toPrice.getText().length() > 0)
				&& (fromPrice.getText().length() == 0)) {
			fromPrice.setError("Enter from price");

		} else if (fromPath.getText().length() > 0
				&& toPath.getText().length() > 0) {

			try {

				startActivity(new Intent(getActivity().getApplicationContext(),
						SearchListActivity.class));

			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HomeActivity.mFrameLayout.setVisibility(View.VISIBLE);

		} else {
			Toast.makeText(getActivity().getApplicationContext(),
					"Please enter source and destination address...",
					Toast.LENGTH_SHORT).show();
			fromPath.setError("Enter Source route..");
			toPath.setError("Enter Destination route...");
		}

	}

	private boolean isEmpty(EditText etText) {

		return etText.getText().toString().trim().length() == 0;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

}