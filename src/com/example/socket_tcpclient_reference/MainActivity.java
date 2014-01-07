package com.example.socket_tcpclient_reference;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TCPClient mTcpClient;
	
	//The content of the Table will be in an array
	//each column gets an array
	private Object[] colId={"ID"};
	private Object[] colName={"Name"};
	private Object[] colValue={"Value"};
	private Object[] colDescription={"Description"};
	private Object[] colAmount={"Amount"};
	
	
	@Override
	//Called when the activity is first created.
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		final EditText et_id=(EditText)findViewById(R.id.editText_id);
		final EditText et_name=(EditText)findViewById(R.id.editText_name);
		
		Button send=(Button)findViewById(R.id.button_search);
		
		new connectTask().execute("");
		fillTable();
		
		send.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View arg0){
				String message_id=et_id.getText().toString();
				String message_name=et_name.getText().toString();
				
				//message that will be sent to Server:
				//the "*" is to mark the later split
				String message=message_id+"*"+message_name;
				
				//sends message to the server
				if(mTcpClient!=null){
					mTcpClient.sendMessage(message);
				}
				et_id.setText("");
				et_name.setText("");
				
			}
		});
	}
	
	public class connectTask extends AsyncTask< String, String, TCPClient>{

		@Override
		protected TCPClient doInBackground(String... message) {
			//create a TCPClient object
			mTcpClient=new TCPClient(new TCPClient.OnMessageReceived(){

				@Override
				//here the messageReceived method is implemented!!!!
				public void messageReceived(String message) {
					publishProgress(message);
				}
			});
			mTcpClient.run();
			return null;
		}
		
		protected void onProgressUpdate(String... values){
			super.onProgressUpdate(values);
			
			String[] resultDB=values[0].split("\\*",-1);
			int rowCount=resultDB.length/5;
			
			//initialize string objects for table columns
			colId=new Object[rowCount];
			colName=new Object[rowCount];
			colValue=new Object[rowCount];
			colDescription=new Object[rowCount];
			colAmount=new Object[rowCount];
			
			// this is the index we need to go through the received object
			int index=0;
			for (int row=0; row< rowCount; row++){
				//column<5 because we know we will receive 5 columns
				for (int column=0; column<5; column++){
					//to line break after the 5th column
					int mod=column%5;
					switch(mod){
						case 0:
							colId[row]=resultDB[index];
							break;
						case 1:
							colName[row]=resultDB[index];
							break;
						case 2:
							colValue[row]=resultDB[index];
							break;
						case 3:
							colDescription[row]=resultDB[index];
							break;
						case 4:
							colAmount[row]=resultDB[index];
							break;
						default:
							break;
					}
					index++;
				}
			}
			fillTable();
		}
	}

	public void fillTable(){
		int rowCount=colId.length;
		Log.d("Fill Table", "rowCount= "+rowCount);
		TableLayout table=(TableLayout)this.findViewById(R.id.tableLayout);
		//clears the table before new input comes in
		table.removeAllViews();
		
		for (int i=0; i< rowCount;i++){
			//fills the table with content
			fillRow(table, i);
		}
	}
	
	//fills the row with the values in the Object[]-Arrays
	public void fillRow(TableLayout table, int rowNumber){
		LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View fullRow=inflater.inflate(R.layout.row, null, false);
		
		TextView tvId=(TextView) fullRow.findViewById(R.id.rowId);
		tvId.setText(String.valueOf(colId[rowNumber]));
		
		TextView tvName=(TextView) fullRow.findViewById(R.id.rowName);
		tvName.setText(String.valueOf(colName[rowNumber]));
		
		TextView tvValue=(TextView) fullRow.findViewById(R.id.rowValue);
		tvValue.setText(String.valueOf(colValue[rowNumber]));
		
		TextView tvDescription=(TextView) fullRow.findViewById(R.id.rowDescription);
		tvDescription.setText(String.valueOf(colDescription[rowNumber]));
		
		TextView tvAmount=(TextView) fullRow.findViewById(R.id.rowAmount);
		tvAmount.setText(String.valueOf(colAmount[rowNumber]));
		
		table.addView(fullRow);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
