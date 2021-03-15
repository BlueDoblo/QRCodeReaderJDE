package JDE;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.Properties;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;
public class JDEQRReader {

	public static void main(String[] args) {
		String FileConfig = "SetUp/QRCodeReaderJDE.properties";
		Properties Config = new Properties();

		try {
			Config.load(new FileInputStream(FileConfig.trim()));
		} catch (FileNotFoundException e) {
			System.out.println("Archivo de configuracion no encontrado " + FileConfig.trim());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Getting encoder
		Base64.Encoder encoder = Base64.getEncoder();

		// Creating byte array
		byte byteArr[] = { 1, 2 };
		// encoding byte array
		byte byteArr2[] = encoder.encode(byteArr);
		byte byteArr3[] = new byte[5]; // Make sure it has enough size to store copied bytes
		int x = encoder.encode(byteArr, byteArr3); // Returns number of bytes written
		Integer PosDesde = Integer.parseInt(Config.getProperty("PosicionDesde"));
		Base64.Decoder decoder = Base64.getDecoder();

		// Decodificacion de String en Base64
		String str;
		String Nuestr;
		String dStr;
		Scanner sc = new Scanner(System.in);
		
		do {
			System.out.println("\nLea por Favor el Codigo QR\n");
			str = sc.nextLine();
			Nuestr = str.toString().substring(PosDesde, str.toString().length());

			// Decoding string
			dStr = new String(decoder.decode(Nuestr));
			System.out.println("Decoded string: " + dStr);
			// if it's not already, convert to a JSON object
			try {
			     JSONObject jsonObject = new JSONObject(dStr);
			     
			        String Salida = "";
		            Salida = "Centro Emisor: " + jsonObject.getInt("ptoVta")+"\n";
		            Salida = Salida + "Tipo: " + jsonObject.getInt("tipoCmp")+"\n";
		            Salida = Salida + "Nro Comprobante: " + jsonObject.getInt("nroCmp")+"\n";
		            Salida = Salida + "Cuit: " + jsonObject.getLong("nroDocRec")+"\n";
		            Salida = Salida + "Fecha: " + jsonObject.getString("fecha").toString()+"\n";
		            Salida = Salida + "Importe " +jsonObject.getLong("importe")+"\n";
		            Salida = Salida + "Moneda: " + jsonObject.getString("moneda").toString() +"\n";
		            Salida = Salida + "Tipo Cambio: " + jsonObject.getInt("ctz") +"\n";
		            Salida = Salida + "\n "+ "Desea Cargar el Comprobante ?";
		            System.out.println(Salida.toString());

			}catch (JSONException err){
			     System.out.println("Error" + err.toString());
			}
			
		} while (true);

	}
}
