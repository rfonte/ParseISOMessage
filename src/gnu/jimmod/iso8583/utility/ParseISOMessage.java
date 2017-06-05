package gnu.jimmod.iso8583.utility;
 
import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
 
public class ParseISOMessage {
 
	public static void main(String[] args) throws IOException, ISOException {
		
		// Create Packager based on XML that contain DE type
		GenericPackager packager = new GenericPackager("basic.xml");
 
		// Print Input Data
		Scanner ler = new Scanner(System.in);
		//0200B2200000001000000000000000800000201234000000010000011072218012345606A5DFGR021ABCDEFGHIJ 1234567890
		String data = ler.nextLine();
		System.out.println("Data : " + data);
		MapaBits(data);
 
		// Create ISO Message
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		isoMsg.unpack(data.getBytes());
 
		// print the DE list
		logISOMsg(isoMsg);
		
	}
 
	private static void logISOMsg(ISOMsg msg) {
		System.out.println("----ISO MESSAGE-----");
		try {
			System.out.println("  MTI : " + msg.getMTI());
						
			for (int i=1;i <= msg.getMaxField();i++) {
				if (msg.hasField(i)) {
					System.out.println("    Field-"+i+" : "+msg.getString(i));
				}
			}
		} catch (ISOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("--------------------");
		}
	}
	
	private static void MapaBits(String msg) {
		String mb = msg.substring(4, 36);
		String[] binario = new BigInteger(mb,16).toString(2).split("");//.substring(0, 64)
		String binstring = Arrays.toString(binario).replace(",", "").replace(" ", "").replace("[", "").replace("]", "");
		String binformatado = binstring.substring(0, 64) + " " + binstring.substring(64, 128);
		//String pos = "";
		
		/*for (int i = 0; i < binario.length; i++) {
			//System.out.println(binario[i]);
			if(binario[i].equals("1")){
				pos += String.valueOf(i+1) + ",";
			}
		}*/
		
		System.out.println("Mapa Bits: " + binformatado + "\n");
		//System.out.println("  Pos: " + pos);
	}
	
}