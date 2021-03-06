package info.jproject.opensource.foodordering;

import info.jproject.opensource.foodordering.library.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@Deprecated
public class UnivCenterInfoActivity extends Activity{
	
	private Button next;
	String PHPSESSID = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.univcenterinfo);
        
        next = (Button) findViewById(R.id.btnUCNext);
        TextView txtProduct = (TextView) findViewById(R.id.univcenter_label);
        
        Intent i = getIntent();
        // getting attached intent data
        String product = i.getStringExtra("prod");
        PHPSESSID = i.getStringExtra("Set-Cookie");
        // displaying selected product name
        txtProduct.setText(product);
        
        next.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent ucMain = new Intent(getApplicationContext(), UnivCenterMainActivity.class);
				//ucMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				ucMain.putExtra("Set-Cookie", PHPSESSID);
				startActivity(ucMain);
				//finish();
			}
		});
        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		UserFunctions userFunctions = new UserFunctions();
		userFunctions.logoutUser(getApplicationContext());
		
		Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);
        // Closing dashboard screen
        finish();
        
		return true;
	}
}