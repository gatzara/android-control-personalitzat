package es.cipfpbatoi.android.controlpersonalitzat;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static es.cipfpbatoi.android.controlpersonalitzat.R.string.transference;
import static es.cipfpbatoi.android.controlpersonalitzat.R.string.credit_card;
import static es.cipfpbatoi.android.controlpersonalitzat.R.string.paypal;

public class ControlPayment extends LinearLayout {
	private EditText txtUsuario;
	private EditText txtPassword;
	private EditText txtCreditCard;
	private Button btnLogin;
	private TextView lblMensaje;
	private RadioGroup rg;
	private RadioButton rb1;
	private RadioButton rb2;
	private RadioButton rb3;
	
	private OnPaymentListener listener;
	
	public ControlPayment(Context context) {
		super(context);
		inicializar();
	}
	
	public ControlPayment(Context context, AttributeSet attrs) {
		super(context, attrs);
		inicializar();
		
		// Procesamos los atributos XML personalizados
		TypedArray a = 
			getContext().obtainStyledAttributes(attrs,
				R.styleable.ControlPayment);

		String textoBoton = a.getString(
				R.styleable.ControlPayment_payment_text);
		
		btnLogin.setText(textoBoton);

		a.recycle();
	}

	private void inicializar() 
	{
		//Utilizamos el layout 'control_payment' como interfaz del control
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = 
			(LayoutInflater)getContext().getSystemService(infService);
		li.inflate(R.layout.control_payment, this, true);
		
		//Obtenemoslas referencias a los distintos control
		//txtUsuario = (EditText)findViewById(R.id.TxtUsuario);
		//txtPassword = (EditText)findViewById(R.id.TxtPassword);
		rg=(RadioGroup) findViewById(R.id.payment);
		rb1=(RadioButton) findViewById(R.id.transferencia);
		rb2=(RadioButton) findViewById(R.id.targeta);
		rb3=(RadioButton) findViewById(R.id.paypal);
		txtCreditCard = (EditText)findViewById(R.id.txtCreditCard);
		btnLogin = (Button)findViewById(R.id.BtnAceptar);
		lblMensaje = (TextView)findViewById(R.id.LblMensaje);
		
		//Asociamos los eventos necesarios
		GetSelectedRadioButton();
	}
	
	public void setOnLoginListener(OnPaymentListener l)
	{
		listener = l;
	}

	private void GetSelectedRadioButton() {

		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (group.getCheckedRadioButtonId()) {
					case R.id.transferencia:
						txtCreditCard.setEnabled(false);
						listener.onPayment(rb1.getText().toString(),txtCreditCard.getText().toString());
						setMensaje( getContext().getResources().getString(transference));
						break;
					case R.id.targeta:
						txtCreditCard.setEnabled(true);
						listener.onPayment(rb2.getText().toString(),txtCreditCard.getText().toString());
						setMensaje(( getContext().getResources().getString(credit_card)));
						break;
					case R.id.paypal:
						txtCreditCard.setEnabled(false);
						listener.onPayment(rb3.getText().toString(),txtCreditCard.getText().toString());
						setMensaje( getContext().getResources().getString(paypal));
						break;

				}
			}
		});
	}
	
/*	private void asignarEventos()
	{
		btnLogin.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) {
				listener.onPayment(txtUsuario.getText().toString(),
						         txtCreditCard.getText().toString());
			}
		});
	}*/
	
	public void setMensaje(String msg)
	{
		lblMensaje.setText(msg);
	}
}
