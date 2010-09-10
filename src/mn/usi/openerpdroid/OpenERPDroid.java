package mn.usi.openerpdroid;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;
import mn.usi.openerpdroid.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OpenERPDroid extends Activity {

	public Button login_button;
	public TextView host_tv,user_tv, pass_tv;
	public EditText host_txt,user_txt, pass_txt;

	public XMLRPCClient rpcClient, regClient;
	public static final String HOST = "http://10.0.0.113:8069/xmlrpc";
	public static final String URL_COMMON = "/common";
	public static final String URL_OBJECT = "/object";
	public static final String URL_DB = "/db";
	
	public String DB_NAME = "monos_erp";
	public String USERNAME = "";
	public String PASSWORD = "";
	public String user = "";
	public int uid = 0;

	public String partner = "";
	public int partner_id = 0;
	public Spinner db_spinner;
	public ArrayAdapter spinnerArrayAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// TEXTVIEW		
		user_tv = (TextView) findViewById(R.id.user_tv);
		pass_tv = (TextView) findViewById(R.id.pass_tv);
		// EDITTEXT
		user_txt = (EditText) findViewById(R.id.user_txt);
		pass_txt = (EditText) findViewById(R.id.pass_txt);

		// BUTTOn
		login_button = (Button) findViewById(R.id.login_button);
		login_button.setOnClickListener(LoginButtonClick);
		
		
		db_spinner = (Spinner) findViewById(R.id.db_spinner);
		spinnerArrayAdapter = new ArrayAdapter(this ,android.R.layout.simple_spinner_item);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		//getDatabaseList();
		
		db_spinner.setAdapter(spinnerArrayAdapter);
	}

	public void showToastNotification(String  message) {
		Toast tmptoast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		tmptoast.show();

	}

	private OnClickListener LoginButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				uid = Connect();
				if (uid > 0) {
					String succes ="successful login from "+ USERNAME+" using database "+DB_NAME;
					showToastNotification(succes);
					//RegisterTestData();
				} else {
					String fail = "bad login or password from "+ USERNAME+" using database " +DB_NAME;
					showToastNotification(fail);
				}
			} catch (Exception e) {
				Log.i("------------------ LOGIN FAILED 1", e.toString());
			}
		}
	};
	public int Connect()// String host, int port, String tinydb, String login,
						// String password)
	{
		USERNAME = user_txt.getText().toString();
		PASSWORD = pass_txt.getText().toString();
		//DB_NAME = db_spinner.getSelectedItem().toString();
		rpcClient = new XMLRPCClient(HOST+URL_COMMON );
		try {
				// Connect
				user = (String) rpcClient.call("login", DB_NAME, USERNAME, PASSWORD).toString();
				Log.i("------------------CURRENT USER ID  ", user);
				Object uid1 = Integer.parseInt(user);
				if (uid1 instanceof Integer)
					return (Integer) uid1;
			return -1;
		} catch (XMLRPCException e) {
			Log.i("------------------ LOGIN FAILED-XMLRPCException", e.toString());
			return -2;
		} catch (Exception e) {
			Log.i("------------------ LOGIN FAILED-EXCEPTION", e.toString());
			return -3;
		}
	}
	public void  getDatabaseList() {
		 rpcClient = new XMLRPCClient(HOST+URL_DB);
		  //uussen baaz bga esehiig shalgana
			Vector<Object> params = new Vector<Object>();
			Object result = null;
			try {
				 result= rpcClient.call("list", params);
			} catch (XMLRPCException e) {
				e.printStackTrace();
			}
			Object[] a = (Object[]) result;
			for (int i = 0; i < a.length; i++) {
			    if (a[i] instanceof String){
			    	spinnerArrayAdapter.add(a[i].toString());
			    	}
				}
		Log.i("------------------ getDatabaseList","4");
		if (a.length == 0) {
			showToastNotification("No database exist");
		}
	 }
}